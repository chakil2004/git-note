package com.example.service;

import com.example.model.StatutTravaux;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service pour la gestion des statuts de travaux
 */
public class StatutTravauxService {
    
    private static final String SELECT_ALL_STATUTS = 
        "SELECT id, libelle FROM statut_travaux ORDER BY libelle ASC";
    
    private static final String SELECT_STATUT_BY_ID = 
        "SELECT id, libelle FROM statut_travaux WHERE id = ?";
    
    private static final String INSERT_STATUT = 
        "INSERT INTO statut_travaux (libelle) VALUES (?)";
    
    private static final String UPDATE_STATUT = 
        "UPDATE statut_travaux SET libelle = ? WHERE id = ?";
    
    private static final String DELETE_STATUT = 
        "DELETE FROM statut_travaux WHERE id = ?";
    
    /**
     * Récupère tous les statuts de travaux
     */
    public List<StatutTravaux> getAllStatuts(Connection conn) throws SQLException {
        List<StatutTravaux> statuts = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_STATUTS);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                StatutTravaux statut = new StatutTravaux();
                statut.setId(rs.getInt("id"));
                statut.setLibelle(rs.getString("libelle"));
                statuts.add(statut);
            }
        }
        
        return statuts;
    }
    
    /**
     * Récupère un statut par son ID
     */
    public StatutTravaux getStatutById(Connection conn, int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_STATUT_BY_ID)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    StatutTravaux statut = new StatutTravaux();
                    statut.setId(rs.getInt("id"));
                    statut.setLibelle(rs.getString("libelle"));
                    return statut;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Crée un nouveau statut de travaux
     */
    public void createStatut(Connection conn, StatutTravaux statut) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_STATUT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, statut.getLibelle());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("La création du statut a échoué, aucune ligne affectée.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    statut.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La création du statut a échoué, aucun ID obtenu.");
                }
            }
        }
    }
    
    /**
     * Met à jour un statut de travaux
     */
    public boolean updateStatut(Connection conn, StatutTravaux statut) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_STATUT)) {
            stmt.setString(1, statut.getLibelle());
            stmt.setInt(2, statut.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Supprime un statut de travaux
     */
    public boolean deleteStatut(Connection conn, int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_STATUT)) {
            stmt.setInt(1, id);
            
            return stmt.executeUpdate() > 0;
        }
    }
}
