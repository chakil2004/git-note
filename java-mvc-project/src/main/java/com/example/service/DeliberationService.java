package com.example.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Service helper for running the deliberation logic.
 * <p>
 * The deliberation selects a solution (Min / Max / Moyenne) based on configured
 * parameters for a given subject (matiere). The selected value is stored in the
 * NoteFinale table.
 */
public class DeliberationService {

    private static final String READ_NOTE_STATS_SQL =
            "SELECT MIN(valeur) AS min_val, MAX(valeur) AS max_val, AVG(valeur) AS avg_val " +
            "FROM Note WHERE etudiant_id = ? AND matiere_id = ?";

    private static final String READ_NOTE_VALUES_SQL =
            "SELECT valeur FROM Note WHERE etudiant_id = ? AND matiere_id = ?";

    private static final String READ_PARAMETRES_SQL =
            "SELECT p.id, p.seuil, m.stringValeur AS methode, s.stringValeur AS solution, " +
            "m.ref AS methode_ref, s.ref AS solution_ref " +
            "FROM Parametre p " +
            "JOIN Methode m ON p.methode_id = m.id " +
            "JOIN Solution s ON p.solution_id = s.id " +
            "WHERE p.matiere_id = ?";

    private static final String COUNT_CORRECTEURS_SQL =
            "SELECT COUNT(DISTINCT prof_id) AS correcteur_count FROM Note " +
            "WHERE etudiant_id = ? AND matiere_id = ?";

    private static final String UPSERT_NOTE_FINALE_SQL =
            "INSERT INTO NoteFinale (etudiant_id, matiere_id, valeur) VALUES (?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE valeur = VALUES(valeur)";

    /**
     * Runs deliberation for a student & subject, then stores the result in NoteFinale.
     *
     * @param conn       JDBC connection (must be open)
     * @param etudiantId student id
     * @param matiereId  subject id
     * @return the chosen final value (may be null if no notes exist)
     * @throws SQLException on SQL error
     */
    public BigDecimal delibererPourEtudiantMatiere(Connection conn, int etudiantId, int matiereId) throws SQLException {
        NoteStats stats = readNoteStats(conn, etudiantId, matiereId);
        if (stats == null) {
            return null;
        }

        // Use detailed difference calculation from NoteService
        NoteService noteService = new NoteService();
        BigDecimal difference = noteService.calculerDifferenceNote(conn, etudiantId, matiereId);

        // Find the first parameter that matches the rule
        String selectedSolution = null;
        BigDecimal selectedSeuil = null;
        String selectedMethode = null;
        boolean foundExactMatch = false;
        int matchCount = 0;
        List<ParametreDetail> matchingParams = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(READ_PARAMETRES_SQL)) {
            stmt.setInt(1, matiereId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BigDecimal seuil = rs.getBigDecimal("seuil");
                    String methode = rs.getString("methode");
                    String solution = rs.getString("solution");

                    boolean matches = false;
                    if (methode != null && seuil != null) {
                        switch (methode.trim()) {
                            case "<":
                                matches = difference.compareTo(seuil) < 0;
                                break;
                            case ">":
                                matches = difference.compareTo(seuil) > 0;
                                break;
                            case "<=":
                                matches = difference.compareTo(seuil) <= 0;
                                break;
                            case ">=":
                                matches = difference.compareTo(seuil) >= 0;
                                break;
                            default:
                                // unknown methode: ignore
                        }
                    }

                    if (matches) {
                        foundExactMatch = true;
                        matchCount++;
                        matchingParams.add(new ParametreDetail(
                            rs.getInt("id"),
                            seuil,
                            methode,
                            solution,
                            rs.getString("methode_ref"),
                            rs.getString("solution_ref")
                        ));
                        System.out.println("Correspondance trouvée: " + methode + " " + seuil + " -> " + solution);
                    }
                }
            }
        }

        // Décision : utiliser ParametreSelector si aucune correspondance OU plusieurs correspondances
        if (!foundExactMatch || matchCount > 1) {
            if (!foundExactMatch) {
                System.out.println("Aucune correspondance exacte trouvée, recherche du meilleur paramètre...");
            } else {
                System.out.println("Plusieurs correspondances exactes trouvées (" + matchCount + "), recherche du meilleur paramètre...");
            }
            
            ParametreSelector.ParametreDetail meilleurParametre = 
                ParametreSelector.trouverMeilleurParametre(conn, matiereId, difference);
            
            if (meilleurParametre != null) {
                selectedSolution = meilleurParametre.getSolution();
                selectedMethode = meilleurParametre.getMethode();
                selectedSeuil = meilleurParametre.getSeuil();
                System.out.println("Meilleur paramètre sélectionné par différence minimale");
            }
        } else if (foundExactMatch && matchCount == 1) {
            // Une seule correspondance exacte : l'utiliser directement
            ParametreDetail param = matchingParams.get(0);
            selectedSolution = param.getSolution();
            selectedMethode = param.getMethode();
            selectedSeuil = param.getSeuil();
            System.out.println("Correspondance unique trouvée, utilisation directe");
        }

        BigDecimal finalValue = chooseFinalValue(stats, selectedSolution);
        if (finalValue != null) {
            upsertNoteFinale(conn, etudiantId, matiereId, finalValue);
        }
        return finalValue;
    }

    private int countCorrecteurs(Connection conn, int etudiantId, int matiereId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(COUNT_CORRECTEURS_SQL)) {
            stmt.setInt(1, etudiantId);
            stmt.setInt(2, matiereId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("correcteur_count");
                }
                return 0;
            }
        }
    }

    private void printNoteValues(Connection conn, int etudiantId, int matiereId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(READ_NOTE_VALUES_SQL)) {
            stmt.setInt(1, etudiantId);
            stmt.setInt(2, matiereId);
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.print("Notes utilisées pour la moyenne (etudiant=" + etudiantId + ", matiere=" + matiereId + "): [");
                boolean first = true;
                while (rs.next()) {
                    if (!first) {
                        System.out.print(", ");
                    }
                    first = false;
                    System.out.print(rs.getBigDecimal("valeur"));
                }
                System.out.println("]");
            }
        }
    }

    private NoteStats readNoteStats(Connection conn, int etudiantId, int matiereId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(READ_NOTE_STATS_SQL)) {
            stmt.setInt(1, etudiantId);
            stmt.setInt(2, matiereId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                BigDecimal min = rs.getBigDecimal("min_val");
                BigDecimal max = rs.getBigDecimal("max_val");
                BigDecimal avg = rs.getBigDecimal("avg_val");
                if (min == null || max == null) {
                    return null;
                }
                return new NoteStats(min, max, avg);
            }
        }
    }

    private BigDecimal chooseFinalValue(NoteStats stats, String solution) {
        if (stats == null) {
            return null;
        }
        if (solution == null) {
            // default: average when no param matches
            return stats.avg;
        }
        switch (solution.trim()) {
            case "Min":
                return stats.min;
            case "Max":
                return stats.max;
            case "Moyenne":
                return stats.avg;
            default:
                return stats.avg;
        }
    }

    private void upsertNoteFinale(Connection conn, int etudiantId, int matiereId, BigDecimal valeur) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(UPSERT_NOTE_FINALE_SQL)) {
            stmt.setInt(1, etudiantId);
            stmt.setInt(2, matiereId);
            stmt.setBigDecimal(3, valeur);
            stmt.executeUpdate();
        }
    }

    /**
     * Classe interne pour stocker les détails d'un paramètre
     */
    public static class ParametreDetail {
        private final int id;
        private final BigDecimal seuil;
        private final String methode;
        private final String solution;
        private final String methodeRef;
        private final String solutionRef;

        public ParametreDetail(int id, BigDecimal seuil, String methode, String solution, 
                             String methodeRef, String solutionRef) {
            this.id = id;
            this.seuil = seuil;
            this.methode = methode;
            this.solution = solution;
            this.methodeRef = methodeRef;
            this.solutionRef = solutionRef;
        }

        // Getters
        public int getId() { return id; }
        public BigDecimal getSeuil() { return seuil; }
        public String getMethode() { return methode; }
        public String getSolution() { return solution; }
        public String getMethodeRef() { return methodeRef; }
        public String getSolutionRef() { return solutionRef; }
    }

    /**
     * Classe interne pour stocker les statistiques des notes
     */
    public static class NoteStats {
        public final BigDecimal min;
        public final BigDecimal max;
        public final BigDecimal avg;

        public NoteStats(BigDecimal min, BigDecimal max, BigDecimal avg) {
            this.min = min;
            this.max = max;
            this.avg = avg;
        }
    }
}
