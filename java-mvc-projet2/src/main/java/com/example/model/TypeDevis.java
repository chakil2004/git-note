package com.example.model;

import jakarta.persistence.*;

/**
 * Modèle pour la table type_devis
 */
@Entity
@Table(name = "type_devis")
public class TypeDevis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "libelle")
    private String libelle;
    
    public TypeDevis() {}
    
    public TypeDevis(int id, String libelle) {
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
        return "TypeDevis{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
