package com.example.model;

import java.time.LocalDateTime;

public class Deliberation {
    private int id;
    private int etudiantId;
    private int matiereId;
    private int solutionId;
    private LocalDateTime dateDeliberation;

    public Deliberation() {
    }

    public Deliberation(int id, int etudiantId, int matiereId, int solutionId, LocalDateTime dateDeliberation) {
        this.id = id;
        this.etudiantId = etudiantId;
        this.matiereId = matiereId;
        this.solutionId = solutionId;
        this.dateDeliberation = dateDeliberation;
    }

    public Deliberation(int etudiantId, int matiereId, int solutionId) {
        this.etudiantId = etudiantId;
        this.matiereId = matiereId;
        this.solutionId = solutionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(int solutionId) {
        this.solutionId = solutionId;
    }

    public LocalDateTime getDateDeliberation() {
        return dateDeliberation;
    }

    public void setDateDeliberation(LocalDateTime dateDeliberation) {
        this.dateDeliberation = dateDeliberation;
    }

    @Override
    public String toString() {
        return "Deliberation{" +
                "id=" + id +
                ", etudiantId=" + etudiantId +
                ", matiereId=" + matiereId +
                ", solutionId=" + solutionId +
                ", dateDeliberation=" + dateDeliberation +
                '}';
    }
}
