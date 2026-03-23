package com.example.model;

/**
 * Modèle pour la table type_devis
 */
public class TypeDevis {
    private int id;
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
