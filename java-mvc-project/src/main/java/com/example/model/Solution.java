package com.example.model;

public class Solution {
    private int id;
    private String stringValeur;
    private String ref;

    public Solution() {
    }

    public Solution(int id, String stringValeur, String ref) {
        this.id = id;
        this.stringValeur = stringValeur;
        this.ref = ref;
    }

    public Solution(String stringValeur, String ref) {
        this.stringValeur = stringValeur;
        this.ref = ref;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStringValeur() {
        return stringValeur;
    }

    public void setStringValeur(String stringValeur) {
        this.stringValeur = stringValeur;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
                ", stringValeur='" + stringValeur + '\'' +
                ", ref='" + ref + '\'' +
                '}';
    }
}
