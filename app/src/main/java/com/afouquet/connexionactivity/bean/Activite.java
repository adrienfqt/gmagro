package com.afouquet.connexionactivity.bean;

public class Activite {

    private String code;
    private String libelle;

    public Activite(String code,String libelle) {
        this.code = code;
        this.libelle=libelle;
    }

    @Override
    public String toString() {
        return this.libelle+" - "+ this.code;
    }
}
