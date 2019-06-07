package com.headquarter.com.headquarter.activity.activity.objects;

import java.sql.Blob;

public class Equipo {
    int idEquipo;
    String nombreEquipo;
    Blob escudoEquipo;

    public Equipo() {
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
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

    @Override
    public String toString() {
        return nombreEquipo;
    }
}


