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
            "SELECT MAX(valeur) - MIN(valeur) AS note_difference FROM Note " +
            "WHERE etudiant_id = ? AND matiere_id = ?";

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
     * Calculate the difference between the maximum and minimum note for a student
     * in a specific subject.
     *
     * @param conn       JDBC connection (must be open)
     * @param etudiantId student id
     * @param matiereId  subject id
     * @return difference between max and min note (0 if no notes)
     * @throws SQLException on SQL error
     */
    public BigDecimal calculerDifferenceNote(Connection conn, int etudiantId, int matiereId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(NOTE_DIFFERENCE_SQL)) {
            stmt.setInt(1, etudiantId);
            stmt.setInt(2, matiereId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    BigDecimal diff = rs.getBigDecimal("note_difference");
                    return diff == null ? BigDecimal.ZERO : diff;
                }
                return BigDecimal.ZERO;
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
