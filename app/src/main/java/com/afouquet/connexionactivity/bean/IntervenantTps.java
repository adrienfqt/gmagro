package com.afouquet.connexionactivity.bean;

import java.sql.Time;

public class IntervenantTps {
    private Intervenant intervenant;
    private Time tps;

    public IntervenantTps(Intervenant intervenant, Time tps) {
        this.intervenant = intervenant;
        this.tps = tps;
    }

    public Intervenant getIntervenant() {
        return intervenant;
    }

    public Time getTps() {
        return tps;
    }

    @Override
    public String toString() {
        return this.intervenant+"("+this.tps+")";
    }
}
