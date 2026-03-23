package com.example.service;

import com.example.model.TypeDevis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service pour la gestion des types de devis
 */
public class TypeDevisService {
    
    private static final String SELECT_ALL_TYPES = 
        "SELECT id, libelle FROM type_devis ORDER BY libelle ASC";
    
    private static final String SELECT_TYPE_BY_ID = 
        "SELECT id, libelle FROM type_devis WHERE id = ?";
    
    private static final String INSERT_TYPE = 
        "INSERT INTO type_devis (libelle) VALUES (?)";
    
    private static final String UPDATE_TYPE = 
        "UPDATE type_devis SET libelle = ? WHERE id = ?";
    
    private static final String DELETE_TYPE = 
        "DELETE FROM type_devis WHERE id = ?";
    
    /**
     * Récupère tous les types de devis
     */
    public List<TypeDevis> getAllTypes(Connection conn) throws SQLException {
        List<TypeDevis> types = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_TYPES);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                TypeDevis type = new TypeDevis();
                type.setId(rs.getInt("id"));
                type.setLibelle(rs.getString("libelle"));
                types.add(type);
            }
        }
        
        return types;
    }
    
    /**
     * Récupère un type par son ID
     */
    public TypeDevis getTypeById(Connection conn, int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_TYPE_BY_ID)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    TypeDevis type = new TypeDevis();
                    type.setId(rs.getInt("id"));
                    type.setLibelle(rs.getString("libelle"));
                    return type;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Crée un nouveau type de devis
     */
    public void createType(Connection conn, TypeDevis type) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_TYPE, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, type.getLibelle());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("La création du type a échoué, aucune ligne affectée.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    type.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La création du type a échoué, aucun ID obtenu.");
                }
            }
        }
    }
    
    /**
     * Met à jour un type de devis
     */
    public boolean updateType(Connection conn, TypeDevis type) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_TYPE)) {
            stmt.setString(1, type.getLibelle());
            stmt.setInt(2, type.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Supprime un type de devis
     */
    public boolean deleteType(Connection conn, int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_TYPE)) {
            stmt.setInt(1, id);
            
            return stmt.executeUpdate() > 0;
        }
    }
}
