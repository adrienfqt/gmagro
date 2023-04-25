package com.afouquet.connexionactivity.bean;

public class Intervenant {

    private String nom;
    private String prenom;
    private String mail;

    public Intervenant(String nom, String prenom,String mail) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }

    @Override
    public String toString() {
        return this.prenom+" " + this.nom;
    }
}
