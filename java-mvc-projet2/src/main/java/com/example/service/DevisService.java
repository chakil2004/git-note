package com.example.service;

import com.example.model.Devis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service pour la gestion des devis
 */
public class DevisService {
    
    private static final String SELECT_ALL_DEVIS = 
        "SELECT id, demande_id, type_devis_id, date_devis, statut_id FROM devis ORDER BY date_devis DESC";
    
    private static final String SELECT_DEVIS_BY_ID = 
        "SELECT id, demande_id, type_devis_id, date_devis, statut_id FROM devis WHERE id = ?";
    
    private static final String SELECT_DEVIS_BY_DEMANDE = 
        "SELECT id, demande_id, type_devis_id, date_devis, statut_id FROM devis WHERE demande_id = ? ORDER BY date_devis DESC";
    
    private static final String INSERT_DEVIS = 
        "INSERT INTO devis (demande_id, type_devis_id, date_devis, statut_id) VALUES (?, ?, ?, ?)";
    
    private static final String UPDATE_DEVIS = 
        "UPDATE devis SET demande_id = ?, type_devis_id = ?, date_devis = ?, statut_id = ? WHERE id = ?";
    
    private static final String DELETE_DEVIS = 
        "DELETE FROM devis WHERE id = ?";
    
    /**
     * Récupère tous les devis
     */
    public List<Devis> getAllDevis(Connection conn) throws SQLException {
        List<Devis> devisList = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_DEVIS);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Devis devis = new Devis();
                devis.setId(rs.getInt("id"));
                devis.setDemandeId(rs.getInt("demande_id"));
                devis.setTypeDevisId(rs.getInt("type_devis_id"));
                devis.setDateDevis(rs.getTimestamp("date_devis").toLocalDateTime());
                devis.setStatutId(rs.getInt("statut_id"));
                devisList.add(devis);
            }
        }
        
        return devisList;
    }
    
    /**
     * Récupère un devis par son ID
     */
    public Devis getDevisById(Connection conn, int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_DEVIS_BY_ID)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Devis devis = new Devis();
                    devis.setId(rs.getInt("id"));
                    devis.setDemandeId(rs.getInt("demande_id"));
                    devis.setTypeDevisId(rs.getInt("type_devis_id"));
                    devis.setDateDevis(rs.getTimestamp("date_devis").toLocalDateTime());
                    devis.setStatutId(rs.getInt("statut_id"));
                    return devis;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Récupère les devis pour une demande
     */
    public List<Devis> getDevisByDemande(Connection conn, int demandeId) throws SQLException {
        List<Devis> devisList = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_DEVIS_BY_DEMANDE)) {
            stmt.setInt(1, demandeId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Devis devis = new Devis();
                    devis.setId(rs.getInt("id"));
                    devis.setDemandeId(rs.getInt("demande_id"));
                    devis.setTypeDevisId(rs.getInt("type_devis_id"));
                    devis.setDateDevis(rs.getTimestamp("date_devis").toLocalDateTime());
                    devis.setStatutId(rs.getInt("statut_id"));
                    devisList.add(devis);
                }
            }
        }
        
        return devisList;
    }
    
    /**
     * Crée un nouveau devis
     */
    public void createDevis(Connection conn, Devis devis) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_DEVIS, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, devis.getDemandeId());
            stmt.setInt(2, devis.getTypeDevisId());
            stmt.setTimestamp(3, Timestamp.valueOf(devis.getDateDevis()));
            stmt.setInt(4, devis.getStatutId());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("La création du devis a échoué, aucune ligne affectée.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    devis.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La création du devis a échoué, aucun ID obtenu.");
                }
            }
        }
    }
    
    /**
     * Met à jour un devis
     */
    public boolean updateDevis(Connection conn, Devis devis) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_DEVIS)) {
            stmt.setInt(1, devis.getDemandeId());
            stmt.setInt(2, devis.getTypeDevisId());
            stmt.setTimestamp(3, Timestamp.valueOf(devis.getDateDevis()));
            stmt.setInt(4, devis.getStatutId());
            stmt.setInt(5, devis.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Supprime un devis
     */
    public boolean deleteDevis(Connection conn, int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_DEVIS)) {
            stmt.setInt(1, id);
            
            return stmt.executeUpdate() > 0;
        }
    }
}
