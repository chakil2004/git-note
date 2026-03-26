package com.example.model;

import jakarta.persistence.*;

/**
 * Modèle pour la table statut
 */
@Entity
@Table(name = "statut")
public class Statut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "libelle")
    private String libelle;
    
    public Statut() {}
    
    public Statut(int id, String libelle) {
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
        return "Statut{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
