package com.example.service;

import com.example.config.DatabaseConfig;
import com.example.model.Etudiant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EtudiantService {

    private static final String SELECT_ALL = "SELECT id, nom FROM Etudiant ORDER BY id";
    private static final String SELECT_BY_ID = "SELECT id, nom FROM Etudiant WHERE id = ?";
    private static final String INSERT = "INSERT INTO Etudiant (nom) VALUES (?)";
    private static final String UPDATE = "UPDATE Etudiant SET nom = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Etudiant WHERE id = ?";

    public List<Etudiant> listAll() throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            List<Etudiant> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new Etudiant(rs.getInt("id"), rs.getString("nom")));
            }
            return result;
        }
    }

    public Etudiant findById(int id) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Etudiant(rs.getInt("id"), rs.getString("nom"));
                }
                return null;
            }
        }
    }

    public void create(Etudiant etudiant) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT)) {
            stmt.setString(1, etudiant.getNom());
            stmt.executeUpdate();
        }
    }

    public void update(Etudiant etudiant) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setString(1, etudiant.getNom());
            stmt.setInt(2, etudiant.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
