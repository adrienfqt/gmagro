package com.afouquet.gmagroAndroid.bean;

import java.util.Date;

public class Intervention {
    private int id;
    private Date dhDeb;
    private Date dhFin;
    private String commentaire;
    private Date tempsArret;
    private boolean chgtOrgane;
    private boolean perte;
    private Date dhCreation;
    private Date dhDerniereMaj;
    private String mailIntervenant;
    private String codeActivite;
    private String codeMachine;
    private String causeDefaut;
    private String causeObjet;
    private String symptomeDefaut;
    private String symptomeObjet;

    public Intervention(int id, Date dhDeb, Date dhFin, String commentaire, Date tempsArret, boolean chgtOrgane, boolean perte, Date dhCreation, Date dhDerniereMaj, String mailIntervenant, String codeActivite, String codeMachine, String causeDefaut, String causeObjet, String symptomeDefaut, String symptomeObjet) {
        this.id = id;
        this.dhDeb = dhDeb;
        this.dhFin = dhFin;
        this.commentaire = commentaire;
        this.tempsArret = tempsArret;
        this.chgtOrgane = chgtOrgane;
        this.perte = perte;
        this.dhCreation = dhCreation;
        this.dhDerniereMaj = dhDerniereMaj;
        this.mailIntervenant = mailIntervenant;
        this.codeActivite = codeActivite;
        this.codeMachine = codeMachine;
        this.causeDefaut = causeDefaut;
        this.causeObjet = causeObjet;
        this.symptomeDefaut = symptomeDefaut;
        this.symptomeObjet = symptomeObjet;
    }

    public int getId() {
        return id;
    }

    public Date getDhDeb() {
        return dhDeb;
    }

    public Date getDhFin() {
        return dhFin;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Date getTempsArret() {
        return tempsArret;
    }

    public boolean isChgtOrgane() {
        return chgtOrgane;
    }

    public boolean isPerte() {
        return perte;
    }

    public Date getDhCreation() {
        return dhCreation;
    }

    public Date getDhDerniereMaj() {
        return dhDerniereMaj;
    }

    public String getMailIntervenant() {
        return mailIntervenant;
    }

    public String getCodeActivite() {
        return codeActivite;
    }

    public String getCodeMachine() {
        return codeMachine;
    }

    public String getCauseDefaut() {
        return causeDefaut;
    }

    public String getCauseObjet() {
        return causeObjet;
    }

    public String getSymptomeDefaut() {
        return symptomeDefaut;
    }

    public String getSymptomeObjet() {
        return symptomeObjet;
    }

    @Override
    public String toString() {
        return this.id + " - "+ this.commentaire;
    }
}
