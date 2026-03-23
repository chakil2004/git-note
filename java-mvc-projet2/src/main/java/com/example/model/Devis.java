package com.example.model;

import java.time.LocalDateTime;

/**
 * Modèle pour la table devis
 */
public class Devis {
    private int id;
    private int demandeId;
    private int typeDevisId;
    private LocalDateTime dateDevis;
    private int statutId;
    
    public Devis() {}
    
    public Devis(int id, int demandeId, int typeDevisId, LocalDateTime dateDevis, int statutId) {
        this.id = id;
        this.demandeId = demandeId;
        this.typeDevisId = typeDevisId;
        this.dateDevis = dateDevis;
        this.statutId = statutId;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getDemandeId() {
        return demandeId;
    }
    
    public void setDemandeId(int demandeId) {
        this.demandeId = demandeId;
    }
    
    public int getTypeDevisId() {
        return typeDevisId;
    }
    
    public void setTypeDevisId(int typeDevisId) {
        this.typeDevisId = typeDevisId;
    }
    
    public LocalDateTime getDateDevis() {
        return dateDevis;
    }
    
    public void setDateDevis(LocalDateTime dateDevis) {
        this.dateDevis = dateDevis;
    }
    
    public int getStatutId() {
        return statutId;
    }
    
    public void setStatutId(int statutId) {
        this.statutId = statutId;
    }
    
    @Override
    public String toString() {
        return "Devis{" +
                "id=" + id +
                ", demandeId=" + demandeId +
                ", typeDevisId=" + typeDevisId +
                ", dateDevis=" + dateDevis +
                ", statutId=" + statutId +
                '}';
    }
}
