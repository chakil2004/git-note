package com.example.service;

import com.example.model.Demande;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service pour la gestion des demandes de forage
 */
public class DemandeService {
    
    private static final String SELECT_ALL_DEMANDES = 
        "SELECT id, client_id, date_demande, description, lieu FROM demande ORDER BY date_demande DESC";
    
    private static final String SELECT_DEMANDE_BY_ID = 
        "SELECT id, client_id, date_demande, description, lieu FROM demande WHERE id = ?";
    
    private static final String INSERT_DEMANDE = 
        "INSERT INTO demande (client_id, date_demande, description, lieu) VALUES (?, ?, ?, ?)";
    
    private static final String UPDATE_DEMANDE = 
        "UPDATE demande SET client_id = ?, date_demande = ?, description = ?, lieu = ? WHERE id = ?";
    
    private static final String DELETE_DEMANDE = 
        "DELETE FROM demande WHERE id = ?";
    
    /**
     * Récupère toutes les demandes
     */
    public List<Demande> getAllDemandes(Connection conn) throws SQLException {
        List<Demande> demandes = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_DEMANDES);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Demande demande = new Demande();
                demande.setId(rs.getInt("id"));
                demande.setClientId(rs.getInt("client_id"));
                demande.setDateDemande(rs.getTimestamp("date_demande").toLocalDateTime());
                demande.setDescription(rs.getString("description"));
                demande.setLieu(rs.getString("lieu"));
                demandes.add(demande);
            }
        }
        
        return demandes;
    }
    
    /**
     * Récupère une demande par son ID
     */
    public Demande getDemandeById(Connection conn, int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_DEMANDE_BY_ID)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Demande demande = new Demande();
                    demande.setId(rs.getInt("id"));
                    demande.setClientId(rs.getInt("client_id"));
                    demande.setDateDemande(rs.getTimestamp("date_demande").toLocalDateTime());
                    demande.setDescription(rs.getString("description"));
                    demande.setLieu(rs.getString("lieu"));
                    return demande;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Crée une nouvelle demande
     */
    public void createDemande(Connection conn, Demande demande) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_DEMANDE, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, demande.getClientId());
            stmt.setTimestamp(2, Timestamp.valueOf(demande.getDateDemande()));
            stmt.setString(3, demande.getDescription());
            stmt.setString(4, demande.getLieu());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("La création de la demande a échoué, aucune ligne affectée.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    demande.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La création de la demande a échoué, aucun ID obtenu.");
                }
            }
        }
    }
    
    /**
     * Met à jour une demande
     */
    public boolean updateDemande(Connection conn, Demande demande) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_DEMANDE)) {
            stmt.setInt(1, demande.getClientId());
            stmt.setTimestamp(2, Timestamp.valueOf(demande.getDateDemande()));
            stmt.setString(3, demande.getDescription());
            stmt.setString(4, demande.getLieu());
            stmt.setInt(5, demande.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Supprime une demande
     */
    public boolean deleteDemande(Connection conn, int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_DEMANDE)) {
            stmt.setInt(1, id);
            
            return stmt.executeUpdate() > 0;
        }
    }
}
