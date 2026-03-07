package com.example.service;

import com.example.config.DatabaseConfig;
import com.example.model.Parametre;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParametreService {

    private static final String SELECT_ALL = "SELECT id, matiere_id, methode_id, solution_id, seuil FROM Parametre ORDER BY id";
    private static final String SELECT_BY_ID = "SELECT id, matiere_id, methode_id, solution_id, seuil FROM Parametre WHERE id = ?";
    private static final String INSERT = "INSERT INTO Parametre (matiere_id, methode_id, solution_id, seuil) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE Parametre SET matiere_id = ?, methode_id = ?, solution_id = ?, seuil = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Parametre WHERE id = ?";

    public List<Parametre> listAll() throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            List<Parametre> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new Parametre(
                        rs.getInt("id"),
                        rs.getInt("matiere_id"),
                        rs.getInt("methode_id"),
                        rs.getInt("solution_id"),
                        rs.getBigDecimal("seuil")
                ));
            }
            return result;
        }
    }

    public Parametre findById(int id) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Parametre(
                            rs.getInt("id"),
                            rs.getInt("matiere_id"),
                            rs.getInt("methode_id"),
                            rs.getInt("solution_id"),
                            rs.getBigDecimal("seuil")
                    );
                }
                return null;
            }
        }
    }

    public void create(Parametre parametre) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT)) {
            stmt.setInt(1, parametre.getMatiereId());
            stmt.setInt(2, parametre.getMethodeId());
            stmt.setInt(3, parametre.getSolutionId());
            stmt.setBigDecimal(4, parametre.getSeuil());
            stmt.executeUpdate();
        }
    }

    public void update(Parametre parametre) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setInt(1, parametre.getMatiereId());
            stmt.setInt(2, parametre.getMethodeId());
            stmt.setInt(3, parametre.getSolutionId());
            stmt.setBigDecimal(4, parametre.getSeuil());
            stmt.setInt(5, parametre.getId());
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
