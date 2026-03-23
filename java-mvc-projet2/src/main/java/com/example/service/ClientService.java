package com.example.service;

import com.example.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service pour la gestion des clients
 */
public class ClientService {
    
    private static final String SELECT_ALL_CLIENTS = 
        "SELECT id, nom, contact FROM client ORDER BY nom ASC";
    
    private static final String SELECT_CLIENT_BY_ID = 
        "SELECT id, nom, contact FROM client WHERE id = ?";
    
    private static final String INSERT_CLIENT = 
        "INSERT INTO client (nom, contact) VALUES (?, ?)";
    
    private static final String UPDATE_CLIENT = 
        "UPDATE client SET nom = ?, contact = ? WHERE id = ?";
    
    private static final String DELETE_CLIENT = 
        "DELETE FROM client WHERE id = ?";
    
    /**
     * Récupère tous les clients
     */
    public List<Client> getAllClients(Connection conn) throws SQLException {
        List<Client> clients = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_CLIENTS);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setNom(rs.getString("nom"));
                client.setContact(rs.getString("contact"));
                clients.add(client);
            }
        }
        
        return clients;
    }
    
    /**
     * Récupère un client par son ID
     */
    public Client getClientById(Connection conn, int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_CLIENT_BY_ID)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Client client = new Client();
                    client.setId(rs.getInt("id"));
                    client.setNom(rs.getString("nom"));
                    client.setContact(rs.getString("contact"));
                    return client;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Crée un nouveau client
     */
    public void createClient(Connection conn, Client client) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_CLIENT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getContact());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("La création du client a échoué, aucune ligne affectée.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    client.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La création du client a échoué, aucun ID obtenu.");
                }
            }
        }
    }
    
    /**
     * Met à jour un client
     */
    public boolean updateClient(Connection conn, Client client) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_CLIENT)) {
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getContact());
            stmt.setInt(3, client.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Supprime un client
     */
    public boolean deleteClient(Connection conn, int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_CLIENT)) {
            stmt.setInt(1, id);
            
            return stmt.executeUpdate() > 0;
        }
    }
}
