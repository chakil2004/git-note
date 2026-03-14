package com.example.controller;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            "SELECT p.seuil, m.stringValeur AS methode, s.stringValeur AS solution " +
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

        // Print all note values used to calculate the average (for debugging / visibility)
        printNoteValues(conn, etudiantId, matiereId);

        // If the student has exactly 2 correcteurs, use "Moyenne" regardless of params.
        int correcteursCount = countCorrecteurs(conn, etudiantId, matiereId);
        if (correcteursCount == 2) {
            BigDecimal valeur = stats.avg;
            upsertNoteFinale(conn, etudiantId, matiereId, valeur);
            return valeur;
        }

        BigDecimal difference = stats.max.subtract(stats.min);

        // Find the first parameter that matches the rule
        String selectedSolution = null;
        BigDecimal selectedSeuil = null;
        String selectedMethode = null;
        boolean foundExactMatch = false;

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
                            default:
                                // unknown methode: ignore
                        }
                    }

                    if (matches) {
                        selectedSolution = solution;
                        selectedMethode = methode;
                        selectedSeuil = seuil;
                        foundExactMatch = true;
                        System.out.println("Correspondance exacte trouvée: " + methode + " " + seuil + " -> " + solution);
                        break;
                    }
                }
            }
        }

        // Si aucune correspondance exacte, utiliser la fonction de plus petite différence
        if (!foundExactMatch) {
            System.out.println("Aucune correspondance exacte trouvée, recherche du meilleur paramètre...");
            ParametreSelector.ParametreDetail meilleurParametre = 
                ParametreSelector.trouverMeilleurParametre(conn, matiereId, difference);
            
            if (meilleurParametre != null) {
                selectedSolution = meilleurParametre.getSolution();
                selectedMethode = meilleurParametre.getMethode();
                selectedSeuil = meilleurParametre.getSeuil();
                System.out.println("Meilleur paramètre sélectionné par différence minimale");
            }
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

    private static class NoteStats {
        final BigDecimal min;
        final BigDecimal max;
        final BigDecimal avg;

        NoteStats(BigDecimal min, BigDecimal max, BigDecimal avg) {
            this.min = min;
            this.max = max;
            this.avg = avg;
        }
    }
}
