package com.example.controller;

import com.example.controller.DeliberationService;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Service helper for operations on Note records.
 */
public class NoteService {

    private static final String COUNT_CORRECTEURS_SQL =
            "SELECT COUNT(DISTINCT prof_id) AS correcteur_count FROM Note " +
            "WHERE etudiant_id = ? AND matiere_id = ?";

    private static final String NOTE_DIFFERENCE_SQL =
            "SELECT SUM(ABS(n1.valeur - n2.valeur)) AS note_difference " +
            "FROM Note n1 " +
            "JOIN Note n2 ON n1.etudiant_id = n2.etudiant_id " +
            "AND n1.matiere_id = n2.matiere_id " +
            "AND n1.prof_id < n2.prof_id " +
            "WHERE n1.etudiant_id = ? AND n1.matiere_id = ?";

    private static final String NOTE_FINALE_SQL =
            "SELECT valeur FROM NoteFinale WHERE etudiant_id = ? AND matiere_id = ?";

    /**
     * Count how many distinct professors graded a given student for a given subject.
     *
     * @param conn       JDBC connection (must be open)
     * @param etudiantId student id
     * @param matiereId  subject id
     * @return number of distinct professeurs who left a note for that student/subject
     * @throws SQLException on SQL error
     */
    public int countCorrecteurs(Connection conn, int etudiantId, int matiereId) throws SQLException {
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

    /**
     * Calculate the sum of differences between all pairs of notes for a student
     * in a specific subject.
     *
     * Formula: |note1 - note2| + |note1 - note3| + |note2 - note3| + ...
     *
     * @param conn       JDBC connection (must be open)
     * @param etudiantId student id
     * @param matiereId  subject id
     * @return sum of all differences between note pairs (0 if no notes)
     * @throws SQLException on SQL error
     */
    public BigDecimal calculerDifferenceNote(Connection conn, int etudiantId, int matiereId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(NOTE_DIFFERENCE_SQL)) {
            stmt.setInt(1, etudiantId);
            stmt.setInt(2, matiereId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    BigDecimal diff = rs.getBigDecimal("note_difference");
                    BigDecimal result = diff == null ? BigDecimal.ZERO : diff;
                    
                    // Afficher les détails dans le terminal
                    System.out.println("=== CALCUL ÉCART NOTES ===");
                    System.out.println("Étudiant ID: " + etudiantId);
                    System.out.println("Matière ID: " + matiereId);
                    System.out.println("Écart total calculé: " + result);
                    
                    // Afficher les notes individuelles pour vérification
                    afficherNotesIndividuelles(conn, etudiantId, matiereId);
                    System.out.println("========================");
                    
                    return result;
                }
                return BigDecimal.ZERO;
            }
        }
    }

    /**
     * Affiche les notes individuelles et les paires avec leurs écarts
     */
    private void afficherNotesIndividuelles(Connection conn, int etudiantId, int matiereId) throws SQLException {
        String sql = "SELECT prof_id, valeur FROM Note WHERE etudiant_id = ? AND matiere_id = ? ORDER BY prof_id";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, etudiantId);
            stmt.setInt(2, matiereId);
            
            // Récupérer toutes les notes
            java.util.List<java.math.BigDecimal> notes = new java.util.ArrayList<>();
            java.util.List<Integer> profIds = new java.util.ArrayList<>();
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    profIds.add(rs.getInt("prof_id"));
                    notes.add(rs.getBigDecimal("valeur"));
                }
            }
            
            // Afficher les notes individuelles
            System.out.println("Notes individuelles:");
            for (int i = 0; i < notes.size(); i++) {
                System.out.println("  Prof " + profIds.get(i) + ": " + notes.get(i));
            }
            
            // Calculer et afficher les écarts par paire
            System.out.println("Écarts par paire:");
            double totalEcart = 0;
            for (int i = 0; i < notes.size(); i++) {
                for (int j = i + 1; j < notes.size(); j++) {
                    double ecart = Math.abs(notes.get(i).doubleValue() - notes.get(j).doubleValue());
                    totalEcart += ecart;
                    System.out.println("  |" + notes.get(i) + " - " + notes.get(j) + "| = " + ecart);
                }
            }
            
            // Récupérer le résultat SQL pour comparaison
            BigDecimal sqlResult = BigDecimal.ZERO;
            String sqlCheck = "SELECT SUM(ABS(n1.valeur - n2.valeur)) AS sql_total " +
                             "FROM Note n1 " +
                             "JOIN Note n2 ON n1.etudiant_id = n2.etudiant_id " +
                             "AND n1.matiere_id = n2.matiere_id " +
                             "AND n1.prof_id < n2.prof_id " +
                             "WHERE n1.etudiant_id = ? AND n1.matiere_id = ?";
            
            try (PreparedStatement checkStmt = conn.prepareStatement(sqlCheck)) {
                checkStmt.setInt(1, etudiantId);
                checkStmt.setInt(2, matiereId);
                try (ResultSet checkRs = checkStmt.executeQuery()) {
                    if (checkRs.next()) {
                        sqlResult = checkRs.getBigDecimal("sql_total");
                    }
                }
            }
            
            // Comparaison
            System.out.println("Vérification manuelle du total (Java): " + totalEcart);
            System.out.println("Résultat de la requête SQL: " + sqlResult);
            
            // Convertir pour comparaison précise
            BigDecimal javaResult = BigDecimal.valueOf(totalEcart);
            if (javaResult.compareTo(sqlResult) == 0) {
                System.out.println("✅ COHÉRENCE : Java et SQL donnent le même résultat");
            } else {
                System.out.println("⚠️ INHÉRENCE DÉTECTÉE !");
                System.out.println("   Différence : " + javaResult.subtract(sqlResult));
            }
        }
    }

    /**
     * Return the final note for a student in a specific subject.
     *
     * @param conn       JDBC connection (must be open)
     * @param etudiantId student id
     * @param matiereId  subject id
     * @return the final note value, or null if no final note exists
     * @throws SQLException on SQL error
     */
    public BigDecimal getNoteFinale(Connection conn, int etudiantId, int matiereId) throws SQLException {
        // First try to read an existing finalized note
        BigDecimal existing = readNoteFinale(conn, etudiantId, matiereId);
        if (existing != null) {
            return existing;
        }

        // No existing final note -> run deliberation and persist the result
        DeliberationService deliberationService = new DeliberationService();
        return deliberationService.delibererPourEtudiantMatiere(conn, etudiantId, matiereId);
    }

    private BigDecimal readNoteFinale(Connection conn, int etudiantId, int matiereId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(NOTE_FINALE_SQL)) {
            stmt.setInt(1, etudiantId);
            stmt.setInt(2, matiereId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("valeur");
                }
                return null;
            }
        }
    }

    /**
     * List all final notes stored in the NoteFinale table.
     *
     * @param conn JDBC connection (must be open)
     * @return list of NoteFinale records
     * @throws SQLException on SQL error
     */
    public java.util.List<com.example.model.NoteFinale> listerNotesFinales(Connection conn) throws SQLException {
        String sql = "SELECT etudiant_id, matiere_id, valeur FROM NoteFinale";
        java.util.List<com.example.model.NoteFinale> result = new java.util.ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int etudiantId = rs.getInt("etudiant_id");
                    int matiereId = rs.getInt("matiere_id");
                    java.math.BigDecimal valeur = rs.getBigDecimal("valeur");
                    result.add(new com.example.model.NoteFinale(etudiantId, matiereId, valeur));
                }
            }
        }
        return result;
    }
}
