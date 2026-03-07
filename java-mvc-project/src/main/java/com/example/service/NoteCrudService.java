package com.example.service;

import com.example.config.DatabaseConfig;
import com.example.model.Note;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteCrudService {

    private static final String SELECT_ALL = "SELECT etudiant_id, prof_id, matiere_id, valeur FROM Note ORDER BY etudiant_id, matiere_id, prof_id";
    private static final String SELECT_BY_PK = "SELECT etudiant_id, prof_id, matiere_id, valeur FROM Note WHERE etudiant_id = ? AND prof_id = ? AND matiere_id = ?";
    private static final String INSERT = "INSERT INTO Note (etudiant_id, prof_id, matiere_id, valeur) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE Note SET valeur = ? WHERE etudiant_id = ? AND prof_id = ? AND matiere_id = ?";
    private static final String DELETE = "DELETE FROM Note WHERE etudiant_id = ? AND prof_id = ? AND matiere_id = ?";

    public List<Note> listAll() throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            List<Note> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new Note(
                        rs.getInt("etudiant_id"),
                        rs.getInt("prof_id"),
                        rs.getInt("matiere_id"),
                        rs.getBigDecimal("valeur")
                ));
            }
            return result;
        }
    }

    public Note findById(int etudiantId, int profId, int matiereId) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_PK)) {
            stmt.setInt(1, etudiantId);
            stmt.setInt(2, profId);
            stmt.setInt(3, matiereId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Note(
                            rs.getInt("etudiant_id"),
                            rs.getInt("prof_id"),
                            rs.getInt("matiere_id"),
                            rs.getBigDecimal("valeur")
                    );
                }
                return null;
            }
        }
    }

    public void create(Note note) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT)) {
            stmt.setInt(1, note.getEtudiantId());
            stmt.setInt(2, note.getProfId());
            stmt.setInt(3, note.getMatiereId());
            stmt.setBigDecimal(4, note.getValeur());
            stmt.executeUpdate();
        }
    }

    public void update(Note note) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setBigDecimal(1, note.getValeur());
            stmt.setInt(2, note.getEtudiantId());
            stmt.setInt(3, note.getProfId());
            stmt.setInt(4, note.getMatiereId());
            stmt.executeUpdate();
        }
    }

    public void delete(int etudiantId, int profId, int matiereId) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {
            stmt.setInt(1, etudiantId);
            stmt.setInt(2, profId);
            stmt.setInt(3, matiereId);
            stmt.executeUpdate();
        }
    }
}
