package com.example.service;

import com.example.config.DatabaseConfig;
import com.example.model.NoteFinale;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteFinaleService {

    private static final String SELECT_ALL = "SELECT etudiant_id, matiere_id, valeur FROM NoteFinale ORDER BY etudiant_id, matiere_id";
    private static final String SELECT_BY_ETUDIANT = "SELECT etudiant_id, matiere_id, valeur FROM NoteFinale WHERE etudiant_id = ? ORDER BY matiere_id";
    private static final String SELECT_BY_ETUDIANT_MATIERE = "SELECT etudiant_id, matiere_id, valeur FROM NoteFinale WHERE etudiant_id = ? AND matiere_id = ?";
    private static final String INSERT = "INSERT INTO NoteFinale (etudiant_id, matiere_id, valeur) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE NoteFinale SET valeur = ? WHERE etudiant_id = ? AND matiere_id = ?";
    private static final String DELETE = "DELETE FROM NoteFinale WHERE etudiant_id = ? AND matiere_id = ?";

    public List<NoteFinale> listAll() throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            List<NoteFinale> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new NoteFinale(
                        rs.getInt("etudiant_id"),
                        rs.getInt("matiere_id"),
                        rs.getBigDecimal("valeur")
                ));
            }
            return result;
        }
    }

    public List<NoteFinale> findByEtudiant(int etudiantId) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ETUDIANT)) {
            stmt.setInt(1, etudiantId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<NoteFinale> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(new NoteFinale(
                            rs.getInt("etudiant_id"),
                            rs.getInt("matiere_id"),
                            rs.getBigDecimal("valeur")
                    ));
                }
                return result;
            }
        }
    }

    public NoteFinale findByEtudiantMatiere(int etudiantId, int matiereId) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ETUDIANT_MATIERE)) {
            stmt.setInt(1, etudiantId);
            stmt.setInt(2, matiereId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new NoteFinale(
                            rs.getInt("etudiant_id"),
                            rs.getInt("matiere_id"),
                            rs.getBigDecimal("valeur")
                    );
                }
                return null;
            }
        }
    }

    public void create(NoteFinale noteFinale) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT)) {
            stmt.setInt(1, noteFinale.getEtudiantId());
            stmt.setInt(2, noteFinale.getMatiereId());
            stmt.setBigDecimal(3, noteFinale.getValeur());
            stmt.executeUpdate();
        }
    }

    public void update(NoteFinale noteFinale) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setBigDecimal(1, noteFinale.getValeur());
            stmt.setInt(2, noteFinale.getEtudiantId());
            stmt.setInt(3, noteFinale.getMatiereId());
            stmt.executeUpdate();
        }
    }

    public void delete(int etudiantId, int matiereId) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {
            stmt.setInt(1, etudiantId);
            stmt.setInt(2, matiereId);
            stmt.executeUpdate();
        }
    }
}
