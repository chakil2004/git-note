package com.example.model;

import java.math.BigDecimal;

/**
 * Modèle pour la table detail_devis
 */
public class DetailDevis {
    private int id;
    private int devisId;
    private String libelle;
    private BigDecimal montant;
    
    public DetailDevis() {}
    
    public DetailDevis(int id, int devisId, String libelle, BigDecimal montant) {
        this.id = id;
        this.devisId = devisId;
        this.libelle = libelle;
        this.montant = montant;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getDevisId() {
        return devisId;
    }
    
    public void setDevisId(int devisId) {
        this.devisId = devisId;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
    public BigDecimal getMontant() {
        return montant;
    }
    
    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }
    
    @Override
    public String toString() {
        return "DetailDevis{" +
                "id=" + id +
                ", devisId=" + devisId +
                ", libelle='" + libelle + '\'' +
                ", montant=" + montant +
                '}';
    }
}
