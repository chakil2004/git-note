package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Modèle pour la table demande
 */
@Entity
@Table(name = "demande")
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;
    
    @Column(name = "date_demande")
    private LocalDateTime dateDemande;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "lieu")
    private String lieu;
    
    @OneToMany(mappedBy = "demande", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Devis> devis;
    
    @OneToOne(mappedBy = "demande", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Travaux travaux;
    
    public Demande() {}
    
    public Demande(int id, Client client, LocalDateTime dateDemande, String description, String lieu) {
        this.id = id;
        this.client = client;
        this.dateDemande = dateDemande;
        this.description = description;
        this.lieu = lieu;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Client getClient() {
        return client;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }
    
    // Pour compatibilité avec le code existant
    public int getClientId() {
        return client != null ? client.getId() : 0;
    }
    
    public void setClientId(int clientId) {
        // Cette méthode est gardée pour compatibilité
        // mais ne devrait plus être utilisée directement
    }
    
    public LocalDateTime getDateDemande() {
        return dateDemande;
    }
    
    public void setDateDemande(LocalDateTime dateDemande) {
        this.dateDemande = dateDemande;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getLieu() {
        return lieu;
    }
    
    public void setLieu(String lieu) {
        this.lieu = lieu;
    }
    
    public List<Devis> getDevis() {
        return devis;
    }
    
    public void setDevis(List<Devis> devis) {
        this.devis = devis;
    }
    
    public Travaux getTravaux() {
        return travaux;
    }
    
    public void setTravaux(Travaux travaux) {
        this.travaux = travaux;
    }
    
    @Override
    public String toString() {
        return "Demande{" +
                "id=" + id +
                ", clientId=" + getClientId() +
                ", dateDemande=" + dateDemande +
                ", description='" + description + '\'' +
                ", lieu='" + lieu + '\'' +
                '}';
    }
}
