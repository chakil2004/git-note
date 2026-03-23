package com.example.service;

import com.example.model.Travaux;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service pour la gestion des travaux
 */
public class TravauxService {
    
    private static final String SELECT_ALL_TRAVAUX = 
        "SELECT id, demande_id, statut_travaux_id FROM travaux ORDER BY id DESC";
    
    private static final String SELECT_TRAVAUX_BY_ID = 
        "SELECT id, demande_id, statut_travaux_id FROM travaux WHERE id = ?";
    
    private static final String SELECT_TRAVAUX_BY_DEMANDE = 
        "SELECT id, demande_id, statut_travaux_id FROM travaux WHERE demande_id = ? ORDER BY id DESC";
    
    private static final String INSERT_TRAVAUX = 
        "INSERT INTO travaux (demande_id, statut_travaux_id) VALUES (?, ?)";
    
    private static final String UPDATE_TRAVAUX = 
        "UPDATE travaux SET demande_id = ?, statut_travaux_id = ? WHERE id = ?";
    
    private static final String DELETE_TRAVAUX = 
        "DELETE FROM travaux WHERE id = ?";
    
    /**
     * Récupère tous les travaux
     */
    public List<Travaux> getAllTravaux(Connection conn) throws SQLException {
        List<Travaux> travauxList = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_TRAVAUX);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Travaux travaux = new Travaux();
                travaux.setId(rs.getInt("id"));
                travaux.setDemandeId(rs.getInt("demande_id"));
                travaux.setStatutTravauxId(rs.getInt("statut_travaux_id"));
                travauxList.add(travaux);
            }
        }
        
        return travauxList;
    }
    
    /**
     * Récupère un travaux par son ID
     */
    public Travaux getTravauxById(Connection conn, int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_TRAVAUX_BY_ID)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Travaux travaux = new Travaux();
                    travaux.setId(rs.getInt("id"));
                    travaux.setDemandeId(rs.getInt("demande_id"));
                    travaux.setStatutTravauxId(rs.getInt("statut_travaux_id"));
                    return travaux;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Récupère les travaux pour une demande
     */
    public List<Travaux> getTravauxByDemande(Connection conn, int demandeId) throws SQLException {
        List<Travaux> travauxList = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_TRAVAUX_BY_DEMANDE)) {
            stmt.setInt(1, demandeId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Travaux travaux = new Travaux();
                    travaux.setId(rs.getInt("id"));
                    travaux.setDemandeId(rs.getInt("demande_id"));
                    travaux.setStatutTravauxId(rs.getInt("statut_travaux_id"));
                    travauxList.add(travaux);
                }
            }
        }
        
        return travauxList;
    }
    
    /**
     * Crée un nouveau travaux
     */
    public void createTravaux(Connection conn, Travaux travaux) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_TRAVAUX, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, travaux.getDemandeId());
            stmt.setInt(2, travaux.getStatutTravauxId());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("La création du travaux a échoué, aucune ligne affectée.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    travaux.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La création du travaux a échoué, aucun ID obtenu.");
                }
            }
        }
    }
    
    /**
     * Met à jour un travaux
     */
    public boolean updateTravaux(Connection conn, Travaux travaux) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_TRAVAUX)) {
            stmt.setInt(1, travaux.getDemandeId());
            stmt.setInt(2, travaux.getStatutTravauxId());
            stmt.setInt(3, travaux.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Supprime un travaux
     */
    public boolean deleteTravaux(Connection conn, int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_TRAVAUX)) {
            stmt.setInt(1, id);
            
            return stmt.executeUpdate() > 0;
        }
    }
}
