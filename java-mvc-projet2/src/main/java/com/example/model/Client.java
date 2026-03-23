package com.example.model;

/**
 * Modèle pour la table client
 */
public class Client {
    private int id;
    private String nom;
    private String contact;
    
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
    
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
