package com.example.service;

import com.example.config.DatabaseConfig;
import com.example.model.Solution;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SolutionService {

    private static final String SELECT_ALL = "SELECT id, stringValeur, ref FROM Solution ORDER BY id";
    private static final String SELECT_BY_ID = "SELECT id, stringValeur, ref FROM Solution WHERE id = ?";
    private static final String INSERT = "INSERT INTO Solution (stringValeur, ref) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE Solution SET stringValeur = ?, ref = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Solution WHERE id = ?";

    public List<Solution> listAll() throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            List<Solution> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new Solution(rs.getInt("id"), rs.getString("stringValeur"), rs.getString("ref")));
            }
            return result;
        }
    }

    public Solution findById(int id) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Solution(rs.getInt("id"), rs.getString("stringValeur"), rs.getString("ref"));
                }
                return null;
            }
        }
    }

    public void create(Solution solution) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT)) {
            stmt.setString(1, solution.getStringValeur());
            stmt.setString(2, solution.getRef());
            stmt.executeUpdate();
        }
    }

    public void update(Solution solution) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setString(1, solution.getStringValeur());
            stmt.setString(2, solution.getRef());
            stmt.setInt(3, solution.getId());
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
