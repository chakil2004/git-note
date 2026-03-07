package com.example.service;

import com.example.config.DatabaseConfig;
import com.example.model.Prof;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfService {

    private static final String SELECT_ALL = "SELECT id, nom FROM Prof ORDER BY id";
    private static final String SELECT_BY_ID = "SELECT id, nom FROM Prof WHERE id = ?";
    private static final String INSERT = "INSERT INTO Prof (nom) VALUES (?)";
    private static final String UPDATE = "UPDATE Prof SET nom = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Prof WHERE id = ?";

    public List<Prof> listAll() throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            List<Prof> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new Prof(rs.getInt("id"), rs.getString("nom")));
            }
            return result;
        }
    }

    public Prof findById(int id) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Prof(rs.getInt("id"), rs.getString("nom"));
                }
                return null;
            }
        }
    }

    public void create(Prof prof) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT)) {
            stmt.setString(1, prof.getNom());
            stmt.executeUpdate();
        }
    }

    public void update(Prof prof) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setString(1, prof.getNom());
            stmt.setInt(2, prof.getId());
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
