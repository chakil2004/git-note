package com.example.model;

import java.math.BigDecimal;

public class NoteFinale {
    private int etudiantId;
    private int matiereId;
    private BigDecimal valeur;

    public NoteFinale() {
    }

    public NoteFinale(int etudiantId, int matiereId, BigDecimal valeur) {
        this.etudiantId = etudiantId;
        this.matiereId = matiereId;
        this.valeur = valeur;
    }

    public int getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(int etudiantId) {
        this.etudiantId = etudiantId;
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

    @Override
    public String toString() {
        return "NoteFinale{" +
                "etudiantId=" + etudiantId +
                ", matiereId=" + matiereId +
                ", valeur=" + valeur +
                '}';
    }
}
