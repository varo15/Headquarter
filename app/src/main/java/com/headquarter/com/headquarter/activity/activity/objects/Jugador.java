package com.headquarter.com.headquarter.activity.activity.objects;

import java.util.Date;

public class Jugador {

    public String DNI;
    public String nombre;
    public Date fechaNacimiento;
    public String telefono;
    public String email;
    public int id_equipo_fk;
    public String equipo;
    public String numeroFAA;
    public boolean registrado;

    public Jugador() {
    }

    public Jugador(String DNI, String nombre, Date fechaNacimiento, String telefono, String email, int id_equipo_fk, String equipo, String numeroFAA, boolean registrado) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.email = email;
        this.id_equipo_fk = id_equipo_fk;
        this.equipo = equipo;
        this.numeroFAA = numeroFAA;
        this.registrado = registrado;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId_equipo_fk() {
        return id_equipo_fk;
    }

    public void setId_equipo_fk(int id_equipo_fk) {
        this.id_equipo_fk = id_equipo_fk;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getNumeroFAA() {
        return numeroFAA;
    }

    public void setNumeroFAA(String numeroFAA) {
        this.numeroFAA = numeroFAA;
    }

    public boolean isRegistrado() {
        return registrado;
    }

    public void setRegistrado(boolean registrado) {
        this.registrado = registrado;
    }
}

