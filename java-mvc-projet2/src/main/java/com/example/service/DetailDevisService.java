package com.example.service;

import com.example.model.DetailDevis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service pour la gestion des détails de devis
 */
public class DetailDevisService {
    
    private static final String SELECT_ALL_DETAILS = 
        "SELECT id, devis_id, libelle, montant FROM detail_devis ORDER BY devis_id, id";
    
    private static final String SELECT_DETAIL_BY_ID = 
        "SELECT id, devis_id, libelle, montant FROM detail_devis WHERE id = ?";
    
    private static final String SELECT_DETAILS_BY_DEVIS = 
        "SELECT id, devis_id, libelle, montant FROM detail_devis WHERE devis_id = ? ORDER BY id";
    
    private static final String INSERT_DETAIL = 
        "INSERT INTO detail_devis (devis_id, libelle, montant) VALUES (?, ?, ?)";
    
    private static final String UPDATE_DETAIL = 
        "UPDATE detail_devis SET devis_id = ?, libelle = ?, montant = ? WHERE id = ?";
    
    private static final String DELETE_DETAIL = 
        "DELETE FROM detail_devis WHERE id = ?";
    
    /**
     * Récupère tous les détails de devis
     */
    public List<DetailDevis> getAllDetails(Connection conn) throws SQLException {
        List<DetailDevis> details = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_DETAILS);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                DetailDevis detail = new DetailDevis();
                detail.setId(rs.getInt("id"));
                detail.setDevisId(rs.getInt("devis_id"));
                detail.setLibelle(rs.getString("libelle"));
                detail.setMontant(rs.getBigDecimal("montant"));
                details.add(detail);
            }
        }
        
        return details;
    }
    
    /**
     * Récupère un détail par son ID
     */
    public DetailDevis getDetailById(Connection conn, int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_DETAIL_BY_ID)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    DetailDevis detail = new DetailDevis();
                    detail.setId(rs.getInt("id"));
                    detail.setDevisId(rs.getInt("devis_id"));
                    detail.setLibelle(rs.getString("libelle"));
                    detail.setMontant(rs.getBigDecimal("montant"));
                    return detail;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Récupère les détails pour un devis
     */
    public List<DetailDevis> getDetailsByDevis(Connection conn, int devisId) throws SQLException {
        List<DetailDevis> details = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_DETAILS_BY_DEVIS)) {
            stmt.setInt(1, devisId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DetailDevis detail = new DetailDevis();
                    detail.setId(rs.getInt("id"));
                    detail.setDevisId(rs.getInt("devis_id"));
                    detail.setLibelle(rs.getString("libelle"));
                    detail.setMontant(rs.getBigDecimal("montant"));
                    details.add(detail);
                }
            }
        }
        
        return details;
    }
    
    /**
     * Crée un nouveau détail de devis
     */
    public void createDetail(Connection conn, DetailDevis detail) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_DETAIL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, detail.getDevisId());
            stmt.setString(2, detail.getLibelle());
            stmt.setBigDecimal(3, detail.getMontant());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("La création du détail a échoué, aucune ligne affectée.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    detail.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La création du détail a échoué, aucun ID obtenu.");
                }
            }
        }
    }
    
    /**
     * Met à jour un détail de devis
     */
    public boolean updateDetail(Connection conn, DetailDevis detail) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_DETAIL)) {
            stmt.setInt(1, detail.getDevisId());
            stmt.setString(2, detail.getLibelle());
            stmt.setBigDecimal(3, detail.getMontant());
            stmt.setInt(4, detail.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Supprime un détail de devis
     */
    public boolean deleteDetail(Connection conn, int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_DETAIL)) {
            stmt.setInt(1, id);
            
            return stmt.executeUpdate() > 0;
        }
    }
}
