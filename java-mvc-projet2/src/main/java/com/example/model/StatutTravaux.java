package com.example.model;

import jakarta.persistence.*;

/**
 * Modèle pour la table statut_travaux
 */
@Entity
@Table(name = "statut_travaux")
public class StatutTravaux {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "libelle")
    private String libelle;
    
    public StatutTravaux() {}
    
    public StatutTravaux(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
    @Override
    public String toString() {
        return "StatutTravaux{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
