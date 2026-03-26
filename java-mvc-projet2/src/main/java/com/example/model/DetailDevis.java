package com.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Modèle pour la table detail_devis
 */
@Entity
@Table(name = "detail_devis")
public class DetailDevis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "devis_id")
    private Devis devis;
    
    @Column(name = "libelle")
    private String libelle;
    
    @Column(name = "prix_unitaire")
    private BigDecimal prixUnitaire;
    
    @Column(name = "quantite")
    private int quantite;
    
    public DetailDevis() {}
    
    public DetailDevis(int id, Devis devis, String libelle, BigDecimal prixUnitaire, int quantite) {
        this.id = id;
        this.devis = devis;
        this.libelle = libelle;
        this.prixUnitaire = prixUnitaire;
        this.quantite = quantite;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Devis getDevis() {
        return devis;
    }
    
    public void setDevis(Devis devis) {
        this.devis = devis;
    }
    
    // Pour compatibilité avec le code existant
    public int getDevisId() {
        return devis != null ? devis.getId() : 0;
    }
    
    public void setDevisId(int devisId) {
        // Cette méthode est gardée pour compatibilité
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }
    
    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
    
    public int getQuantite() {
        return quantite;
    }
    
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    
    @Override
    public String toString() {
        return "DetailDevis{" +
                "id=" + id +
                ", devisId=" + getDevisId() +
                ", libelle='" + libelle + '\'' +
                ", prixUnitaire=" + prixUnitaire +
                ", quantite=" + quantite +
                '}';
    }
}
