package com.headquarter.com.headquarter.activity.activity.objects;

import android.graphics.Bitmap;

import java.sql.Blob;
import java.util.Date;
/*
 * Objeto Partida
 */
public class Partida {

    /**
     * Variables de la clase Partida
     */
    int idPartida;
    String nombrePartida;
    Date fechaPartida;
    Blob fotoPartida;
    Bitmap fotoPartidaBitmap;
    String aforoPartida;
    String tipoPartida;
    String campoPartida;
    String marcoAmbiental;

    /**
     * Constructor vacio de la clase Partida
     */
    public Partida() {
    }

    /**
     * Constructor de la clase Partida
     * @param idPartida
     * @param nombrePartida
     * @param fechaPartida
     * @param fotoPartida
     * @param fotoPartidaBitmap
     * @param aforoPartida
     * @param tipoPartida
     * @param campoPartida
     * @param marcoAmbiental
     */
    public Partida(int idPartida, String nombrePartida, Date fechaPartida, Blob fotoPartida, Bitmap fotoPartidaBitmap, String aforoPartida, String tipoPartida, String campoPartida, String marcoAmbiental) {
        this.idPartida = idPartida;
        this.nombrePartida = nombrePartida;
        this.fechaPartida = fechaPartida;
        this.fotoPartida = fotoPartida;
        this.fotoPartidaBitmap = fotoPartidaBitmap;
        this.aforoPartida = aforoPartida;
        this.tipoPartida = tipoPartida;
        this.campoPartida = campoPartida;
        this.marcoAmbiental = marcoAmbiental;
    }

    /**
     * Getter de la foto, en formato bitmap, de la partida
     * @return fotoPartidaBitmap
     */
    public Bitmap getFotoPartidaBitmap() {
        return fotoPartidaBitmap;
    }

    /**
     * Setter de la foto, en formato bitmap,  de la partida
     * @param fotoPartidaBitmap
     */
    public void setFotoPartidaBitmap(Bitmap fotoPartidaBitmap) {
        this.fotoPartidaBitmap = fotoPartidaBitmap;
    }

    /**
     * Getter del id de la partida
     * @return idPartida
     */
    public int getIdPartida() {
        return idPartida;
    }

    /**
     * Setter del id de la partida
     * @param idPartida
     */
    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    /**
     * Getter del nombre de la partida
     * @return nombrePartida
     */
    public String getNombrePartida() {
        return nombrePartida;
    }

    /**
     * Setter del nombre de la partida
     * @param nombrePartida
     */
    public void setNombrePartida(String nombrePartida) {
        this.nombrePartida = nombrePartida;
    }

    /**
     * Getter de la fecha de la partida
     * @return fechaPartida
     */
    public Date getFechaPartida() {
        return fechaPartida;
    }

    /**
     * Setter de la fecha de la partida
     * @param fechaPartida
     */
    public void setFechaPartida(Date fechaPartida) {
        this.fechaPartida = fechaPartida;
    }

    /**
     * Getter de la foto de la partida
     * @return fotoPartida
     */
    public Blob getFotoPartida() {
        return fotoPartida;
    }

    /**
     * Setter de la foto de la partida
     * @param fotoPartida
     */
    public void setFotoPartida(Blob fotoPartida) {
        this.fotoPartida = fotoPartida;
    }

    /**
     * Getter del aforo de la partida
     * @return aforoPartida
     */
    public String getAforoPartida() {
        return aforoPartida;
    }

    /**
     * Setter del aforo de la partida
     * @param aforoPartida
     */
    public void setAforoPartida(String aforoPartida) {
        this.aforoPartida = aforoPartida;
    }

    /**
     * Getter del tipo de partida
     * @return tipoPartida
     */
    public String getTipoPartida() {
        return tipoPartida;
    }

    /**
     * Setter del tipo de partida
     * @param tipoPartida
     */
    public void setTipoPartida(String tipoPartida) {
        this.tipoPartida = tipoPartida;
    }

    /**
     * Getter del campo de la partida
     * @return campoPartida
     */
    public String getCampoPartida() {
        return campoPartida;
    }

    /**
     * Setter del campo de la partida
     * @param campoPartida
     */
    public void setCampoPartida(String campoPartida) {
        this.campoPartida = campoPartida;
    }

    /**
     * Getter del marco ambiental de la partida
     * @return marcoAmbiental
     */
    public String getMarcoAmbiental() {
        return marcoAmbiental;
    }

    /**
     * Setter del marco ambiental de la partida
     * @param marcoAmbiental
     */
    public void setMarcoAmbiental(String marcoAmbiental) {
        this.marcoAmbiental = marcoAmbiental;
    }
}