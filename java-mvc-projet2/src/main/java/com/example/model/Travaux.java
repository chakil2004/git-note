package com.example.model;

import java.time.LocalDateTime;

/**
 * Modèle pour la table travaux
 */
public class Travaux {
    private int id;
    private int demandeId;
    private int statutTravauxId;
    
    public Travaux() {}
    
    public Travaux(int id, int demandeId, int statutTravauxId) {
        this.id = id;
        this.demandeId = demandeId;
        this.statutTravauxId = statutTravauxId;
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
    
    public int getStatutTravauxId() {
        return statutTravauxId;
    }
    
    public void setStatutTravauxId(int statutTravauxId) {
        this.statutTravauxId = statutTravauxId;
    }
    
    @Override
    public String toString() {
        return "Travaux{" +
                "id=" + id +
                ", demandeId=" + demandeId +
                ", statutTravauxId=" + statutTravauxId +
                '}';
    }
}
