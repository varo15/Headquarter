package com.headquarter.com.headquarter.activity.activity.objects;

import java.sql.Blob;

public class Equipo {
    String idEquipo;
    String nombreEquipo;
    Blob escudoEquipo;

    public Equipo() {
    }

    public Equipo(String idEquipo, String nombreEquipo, Blob escudoEquipo) {
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
        this.escudoEquipo = escudoEquipo;
    }

    public String getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(String idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public Blob getEscudoEquipo() {
        return escudoEquipo;
    }

    public void setEscudoEquipo(Blob escudoEquipo) {
        this.escudoEquipo = escudoEquipo;
    }
}


