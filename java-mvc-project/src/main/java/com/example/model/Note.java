package com.example.model;

import java.math.BigDecimal;

public class Note {
    private int etudiantId;
    private int profId;
    private int matiereId;
    private BigDecimal valeur;

    public Note() {
    }

    public Note(int etudiantId, int profId, int matiereId, BigDecimal valeur) {
        this.etudiantId = etudiantId;
        this.profId = profId;
        this.matiereId = matiereId;
        this.valeur = valeur;
    }

    public Note(int etudiantId, int profId, int matiereId, double valeur) {
        this.etudiantId = etudiantId;
        this.profId = profId;
        this.matiereId = matiereId;
        this.valeur = BigDecimal.valueOf(valeur);
    }

    public int getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(int etudiantId) {
        this.etudiantId = etudiantId;
    }

    public int getProfId() {
        return profId;
    }

    public void setProfId(int profId) {
        this.profId = profId;
    }

    public int getMatiereId() {
        return matiereId;
    }

    public void setMatiereId(int matiereId) {
        this.matiereId = matiereId;
    }

    public BigDecimal getValeur() {
        return valeur;
    }

    public void setValeur(BigDecimal valeur) {
        this.valeur = valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = BigDecimal.valueOf(valeur);
    }

    @Override
    public String toString() {
        return "Note{" +
                "etudiantId=" + etudiantId +
                ", profId=" + profId +
                ", matiereId=" + matiereId +
                ", valeur=" + valeur +
                '}';
    }
}
