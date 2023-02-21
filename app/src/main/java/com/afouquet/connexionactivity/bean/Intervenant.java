package com.afouquet.connexionactivity.bean;

public class Intervenant {

    private String nom;
    private String prenom;

    public Intervenant(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    @Override
    public String toString() {
        return this.prenom+" " + this.nom;
    }
}
