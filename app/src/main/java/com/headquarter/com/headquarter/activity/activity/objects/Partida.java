package com.headquarter.com.headquarter.activity.activity.objects;

import android.graphics.Bitmap;
import android.os.Parcelable;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
/*
 * Objeto Partida
 */
public class Partida {

    int idPartida;
    String nombrePartida;
    Date fechaPartida;
    Blob fotoPartida;
    Bitmap fotoPartidaBitmap;
    String aforoPartida;
    String tipoPartida;
    String campoPartida;

    public Partida() {
    }

    public Partida(int idPartida, String nombrePartida, Date fechaPartida, Blob fotoPartida, Bitmap fotoPartidaBitmap, String aforoPartida, String tipoPartida, String campoPartida) {
        this.idPartida = idPartida;
        this.nombrePartida = nombrePartida;
        this.fechaPartida = fechaPartida;
        this.fotoPartida = fotoPartida;
        this.fotoPartidaBitmap = fotoPartidaBitmap;
        this.aforoPartida = aforoPartida;
        this.tipoPartida = tipoPartida;
        this.campoPartida = campoPartida;
    }

    public Bitmap getFotoPartidaBitmap() {
        return fotoPartidaBitmap;
    }

    public void setFotoPartidaBitmap(Bitmap fotoPartidaBitmap) {
        this.fotoPartidaBitmap = fotoPartidaBitmap;
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