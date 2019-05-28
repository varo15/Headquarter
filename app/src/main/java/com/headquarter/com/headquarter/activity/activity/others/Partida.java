package com.headquarter.com.headquarter.activity.activity.others;

import java.sql.Blob;
import java.util.Date;

public class Partida {

    public int idPartida;
    public String nombrePartida;
    public Blob guionPartida;
    public Date fechaPartida;
    public Blob fotoPartida;
    public String aforoPartida;
    public String tipoPartida;
    public String campoPartida;

    public Partida() {
    }

    public Partida(int idPartida, String nombrePartida, Blob guionPartida, Date fechaPartida, Blob fotoPartida, String aforoPartida, String tipoPartida, String campoPartida) {
        this.idPartida = idPartida;
        this.nombrePartida = nombrePartida;
        this.guionPartida = guionPartida;
        this.fechaPartida = fechaPartida;
        this.fotoPartida = fotoPartida;
        this.aforoPartida = aforoPartida;
        this.tipoPartida = tipoPartida;
        this.campoPartida = campoPartida;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public String getNombrePartida() {
        return nombrePartida;
    }

    public void setNombrePartida(String nombrePartida) {
        this.nombrePartida = nombrePartida;
    }

    public Blob getGuionPartida() {
        return guionPartida;
    }

    public void setGuionPartida(Blob guionPartida) {
        this.guionPartida = guionPartida;
    }

    public Date getFechaPartida() {
        return fechaPartida;
    }

    public void setFechaPartida(Date fechaPartida) {
        this.fechaPartida = fechaPartida;
    }

    public Blob getFotoPartida() {
        return fotoPartida;
    }

    public void setFotoPartida(Blob fotoPartida) {
        this.fotoPartida = fotoPartida;
    }

    public String getAforoPartida() {
        return aforoPartida;
    }

    public void setAforoPartida(String aforoPartida) {
        this.aforoPartida = aforoPartida;
    }

    public String getTipoPartida() {
        return tipoPartida;
    }

    public void setTipoPartida(String tipoPartida) {
        this.tipoPartida = tipoPartida;
    }

    public String getCampoPartida() {
        return campoPartida;
    }

    public void setCampoPartida(String campoPartida) {
        this.campoPartida = campoPartida;
    }
}
