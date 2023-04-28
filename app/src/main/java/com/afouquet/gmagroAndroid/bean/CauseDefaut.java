package com.afouquet.gmagroAndroid.bean;

public class CauseDefaut {
    private String code;
    private String libelle;
    private String uai;

    public CauseDefaut(String code, String libelle, String uai) {
        this.code = code;
        this.libelle = libelle;
        this.uai = uai;
    }

    public String getCode() {
        return code;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getUai() {
        return uai;
    }

    @Override
    public String toString() {
        return this.libelle;
    }
}
