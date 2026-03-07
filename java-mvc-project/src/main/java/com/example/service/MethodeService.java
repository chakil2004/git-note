package com.example.service;

import com.example.config.DatabaseConfig;
import com.example.model.Methode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MethodeService {

    private static final String SELECT_ALL = "SELECT id, stringValeur, ref FROM Methode ORDER BY id";
    private static final String SELECT_BY_ID = "SELECT id, stringValeur, ref FROM Methode WHERE id = ?";
    private static final String INSERT = "INSERT INTO Methode (stringValeur, ref) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE Methode SET stringValeur = ?, ref = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Methode WHERE id = ?";

    public List<Methode> listAll() throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            List<Methode> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new Methode(rs.getInt("id"), rs.getString("stringValeur"), rs.getString("ref")));
            }
            return result;
        }
    }

    public Methode findById(int id) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Methode(rs.getInt("id"), rs.getString("stringValeur"), rs.getString("ref"));
                }
                return null;
            }
        }
    }

    public void create(Methode methode) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT)) {
            stmt.setString(1, methode.getStringValeur());
            stmt.setString(2, methode.getRef());
            stmt.executeUpdate();
        }
    }

    public void update(Methode methode) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setString(1, methode.getStringValeur());
            stmt.setString(2, methode.getRef());
            stmt.setInt(3, methode.getId());
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
