package com.example.model;

import java.math.BigDecimal;

public class Parametre {
    private int id;
    private int matiereId;
    private int methodeId;
    private int solutionId;
    private BigDecimal seuil;

    public Parametre() {
    }

    public Parametre(int id, int matiereId, int methodeId, int solutionId, BigDecimal seuil) {
        this.id = id;
        this.matiereId = matiereId;
        this.methodeId = methodeId;
        this.solutionId = solutionId;
        this.seuil = seuil;
    }

    public Parametre(int matiereId, int methodeId, int solutionId, BigDecimal seuil) {
        this.matiereId = matiereId;
        this.methodeId = methodeId;
        this.solutionId = solutionId;
        this.seuil = seuil;
    }

    public Parametre(int matiereId, int methodeId, int solutionId, double seuil) {
        this.matiereId = matiereId;
        this.methodeId = methodeId;
        this.solutionId = solutionId;
        this.seuil = BigDecimal.valueOf(seuil);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatiereId() {
        return matiereId;
    }

    public void setMatiereId(int matiereId) {
        this.matiereId = matiereId;
    }

    public int getMethodeId() {
        return methodeId;
    }

    public void setMethodeId(int methodeId) {
        this.methodeId = methodeId;
    }

    public int getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(int solutionId) {
        this.solutionId = solutionId;
    }

    public BigDecimal getSeuil() {
        return seuil;
    }

    public void setSeuil(BigDecimal seuil) {
        this.seuil = seuil;
    }

    public void setSeuil(double seuil) {
        this.seuil = BigDecimal.valueOf(seuil);
    }

    @Override
    public String toString() {
        return "Parametre{" +
                "id=" + id +
                ", matiereId=" + matiereId +
                ", methodeId=" + methodeId +
                ", solutionId=" + solutionId +
                ", seuil=" + seuil +
                '}';
    }
}
