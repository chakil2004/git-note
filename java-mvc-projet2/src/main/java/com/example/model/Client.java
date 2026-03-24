package com.example.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Modèle pour la table client
 */
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "nom")
    private String nom;
    
    @Column(name = "contact")
    private String contact;
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Demande> demandes;
    
    public Client() {}
    
    public Client(int id, String nom, String contact) {
        this.id = id;
        this.nom = nom;
        this.contact = contact;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getContact() {
        return contact;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }
    
    public List<Demande> getDemandes() {
        return demandes;
    }
    
    public void setDemandes(List<Demande> demandes) {
        this.demandes = demandes;
    }
    
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
