package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Modèle pour la table devis
 */
@Entity
@Table(name = "devis")
public class Devis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "demande_id")
    private Demande demande;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_devis_id")
    private TypeDevis typeDevis;
    
    @Column(name = "date_devis")
    private LocalDateTime dateDevis;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statut_id")
    private Statut statut;
    
    @OneToMany(mappedBy = "devis", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DetailDevis> details;
    
    public Devis() {}
    
    public Devis(int id, Demande demande, TypeDevis typeDevis, LocalDateTime dateDevis, Statut statut) {
        this.id = id;
        this.demande = demande;
        this.typeDevis = typeDevis;
        this.dateDevis = dateDevis;
        this.statut = statut;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Demande getDemande() {
        return demande;
    }
    
    public void setDemande(Demande demande) {
        this.demande = demande;
    }
    
    public TypeDevis getTypeDevis() {
        return typeDevis;
    }
    
    public void setTypeDevis(TypeDevis typeDevis) {
        this.typeDevis = typeDevis;
    }
    
    // Pour compatibilité avec le code existant
    public int getDemandeId() {
        return demande != null ? demande.getId() : 0;
    }
    
    public void setDemandeId(int demandeId) {
        // Cette méthode est gardée pour compatibilité
    }
    
    public int getTypeDevisId() {
        return typeDevis != null ? typeDevis.getId() : 0;
    }
    
    public void setTypeDevisId(int typeDevisId) {
        // Cette méthode est gardée pour compatibilité
    }
    
    public LocalDateTime getDateDevis() {
        return dateDevis;
    }
    
    public void setDateDevis(LocalDateTime dateDevis) {
        this.dateDevis = dateDevis;
    }
    
    public Statut getStatut() {
        return statut;
    }
    
    public void setStatut(Statut statut) {
        this.statut = statut;
    }
    
    // Pour compatibilité avec le code existant
    public int getStatutId() {
        return statut != null ? statut.getId() : 0;
    }
    
    public void setStatutId(int statutId) {
        // Cette méthode est gardée pour compatibilité
    }
    
    public List<DetailDevis> getDetails() {
        return details;
    }
    
    public void setDetails(List<DetailDevis> details) {
        this.details = details;
    }
    
    @Override
    public String toString() {
        return "Devis{" +
                "id=" + id +
                ", demandeId=" + getDemandeId() +
                ", typeDevisId=" + getTypeDevisId() +
                ", dateDevis=" + dateDevis +
                ", statutId=" + getStatutId() +
                '}';
    }
}
