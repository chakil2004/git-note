package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Modèle pour la table travaux
 */
@Entity
@Table(name = "travaux")
public class Travaux {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "demande_id")
    private Demande demande;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statut_travaux_id")
    private StatutTravaux statutTravaux;
    
    public Travaux() {}
    
    public Travaux(int id, Demande demande, StatutTravaux statutTravaux) {
        this.id = id;
        this.demande = demande;
        this.statutTravaux = statutTravaux;
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
    
    public StatutTravaux getStatutTravaux() {
        return statutTravaux;
    }
    
    public void setStatutTravaux(StatutTravaux statutTravaux) {
        this.statutTravaux = statutTravaux;
    }
    
    // Pour compatibilité avec le code existant
    public int getDemandeId() {
        return demande != null ? demande.getId() : 0;
    }
    
    public void setDemandeId(int demandeId) {
        // Cette méthode est gardée pour compatibilité
    }
    
    public int getStatutTravauxId() {
        return statutTravaux != null ? statutTravaux.getId() : 0;
    }
    
    public void setStatutTravauxId(int statutTravauxId) {
        // Cette méthode est gardée pour compatibilité
    }
    
    @Override
    public String toString() {
        return "Travaux{" +
                "id=" + id +
                ", demandeId=" + getDemandeId() +
                ", statutTravauxId=" + getStatutTravauxId() +
                '}';
    }
}
