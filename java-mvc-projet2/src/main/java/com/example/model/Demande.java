package com.example.model;

import java.time.LocalDateTime;

/**
 * Modèle pour la table demande
 */
public class Demande {
    private int id;
    private int clientId;
    private LocalDateTime dateDemande;
    private String description;
    private String lieu;
    
    public Demande() {}
    
    public Demande(int id, int clientId, LocalDateTime dateDemande, String description, String lieu) {
        this.id = id;
        this.clientId = clientId;
        this.dateDemande = dateDemande;
        this.description = description;
        this.lieu = lieu;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getClientId() {
        return clientId;
    }
    
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    
    public LocalDateTime getDateDemande() {
        return dateDemande;
    }
    
    public void setDateDemande(LocalDateTime dateDemande) {
        this.dateDemande = dateDemande;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getLieu() {
        return lieu;
    }
    
    public void setLieu(String lieu) {
        this.lieu = lieu;
    }
    
    @Override
    public String toString() {
        return "Demande{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", dateDemande=" + dateDemande +
                ", description='" + description + '\'' +
                ", lieu='" + lieu + '\'' +
                '}';
    }
}
