package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Modèle pour la table demande_statut
 */
@Entity
@Table(name = "demande_statut")
public class DemandeStatut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "demande_id")
    private Demande demande;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statut")
    private Statut statut;
    
    @Column(name = "date")
    private LocalDateTime date;
    
    public DemandeStatut() {}
    
    public DemandeStatut(int id, Demande demande, Statut statut, LocalDateTime date) {
        this.id = id;
        this.demande = demande;
        this.statut = statut;
        this.date = date;
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
    
    // Pour compatibilité avec le code existant
    public int getDemandeId() {
        return demande != null ? demande.getId() : 0;
    }
    
    public void setDemandeId(int demandeId) {
        // Cette méthode est gardée pour compatibilité
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
    
    public LocalDateTime getDate() {
        return date;
    }
    
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    @Override
    public String toString() {
        return "DemandeStatut{" +
                "id=" + id +
                ", demandeId=" + getDemandeId() +
                ", statutId=" + getStatutId() +
                ", date=" + date +
                '}';
    }
}
