package com.example.service;

import com.example.config.DatabaseConfig;
import com.example.model.Matiere;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MatiereService {

    private static final String SELECT_ALL = "SELECT id, nom FROM Matiere ORDER BY id";
    private static final String SELECT_BY_ID = "SELECT id, nom FROM Matiere WHERE id = ?";
    private static final String INSERT = "INSERT INTO Matiere (nom) VALUES (?)";
    private static final String UPDATE = "UPDATE Matiere SET nom = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Matiere WHERE id = ?";

    public List<Matiere> listAll() throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            List<Matiere> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new Matiere(rs.getInt("id"), rs.getString("nom")));
            }
            return result;
        }
    }

    public Matiere findById(int id) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Matiere(rs.getInt("id"), rs.getString("nom"));
                }
                return null;
            }
        }
    }

    public void create(Matiere matiere) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT)) {
            stmt.setString(1, matiere.getNom());
            stmt.executeUpdate();
        }
    }

    public void update(Matiere matiere) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setString(1, matiere.getNom());
            stmt.setInt(2, matiere.getId());
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
