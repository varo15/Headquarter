package com.headquarter.com.headquarter.activity.activity.objects;

import java.sql.Blob;

public class Equipo {
    /**
     * Variables de la clase equipo
     */
    int idEquipo;
    String nombreEquipo;
    Blob escudoEquipo;

    /**
     * Constructor vacio de la clase Equipo
     */
    public Equipo() {
    }

    /**
     * Constructor de la clase equipo
     * @param idEquipo
     * @param nombreEquipo
     * @param escudoEquipo
     */
    public Equipo(int idEquipo, String nombreEquipo, Blob escudoEquipo) {
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
        this.escudoEquipo = escudoEquipo;
    }

    /**
     * Getter del id del equipo
     * @return idEquipo
     */
    public int getIdEquipo() {
        return idEquipo;
    }

    /**
     * Setter del id del equipo
     * @param idEquipo
     */
    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    /**
     * Getter del nombre del equipo
     * @return nombreEquipo
     */
    public String getNombreEquipo() {
        return nombreEquipo;
    }

    /**
     * Setter del nombre del equipo
     * @param nombreEquipo
     */
    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    /**
     * Getter del escudo del equipo
     * @return escudoEquipo
     */
    public Blob getEscudoEquipo() {
        return escudoEquipo;
    }

    /**
     * Setter del escudo del equipo
     * @param escudoEquipo
     */
    public void setEscudoEquipo(Blob escudoEquipo) {
        this.escudoEquipo = escudoEquipo;
    }

    /**
     * Metodo toString de la clase
     * @return nombreEquipo
     */
    @Override
    public String toString() {
        return nombreEquipo;
    }
}


