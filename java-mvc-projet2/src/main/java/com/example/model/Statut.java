package com.example.model;

/**
 * Modèle pour la table statut
 */
public class Statut {
    private int id;
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
