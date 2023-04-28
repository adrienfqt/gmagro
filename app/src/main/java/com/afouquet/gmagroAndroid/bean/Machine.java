package com.afouquet.gmagroAndroid.bean;

import java.util.Date;

public class Machine {
    private String code;
    private Date dateFabrication;
    private String numSerie;
    private String uai;
    private String typeCode;

    public Machine(String code, Date dateFabrication, String numSerie, String uai, String typeCode) {
        this.code = code;
        this.dateFabrication = dateFabrication;
        this.numSerie = numSerie;
        this.uai = uai;
        this.typeCode = typeCode;
    }

    public String getCode() {
        return code;
    }

    public Date getDateFabrication() {
        return dateFabrication;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public String getUai() {
        return uai;
    }

    public String getTypeCode() {
        return typeCode;
    }

    @Override
    public String toString() {
        return this.code ;
    }
}
