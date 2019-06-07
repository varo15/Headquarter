package com.headquarter.com.headquarter.activity.activity.objects;

public class Jugador {

    public String idGoogle;
    public String DNI;
    public String nombre;
    public String fechaNacimiento;
    public String telefono;
    public String email;
    public int id_equipo_fk;
    public String equipo;
    public String numeroFAA;
    public int registrado;

    public Jugador() {
    }

    public String getIdGoogle() {
        return idGoogle;
    }

    public void setIdGoogle(String idGoogle) {
        this.idGoogle = idGoogle;
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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
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

    public int getRegistrado() {
        return registrado;
    }

    public void setRegistrado(int registrado) {
        this.registrado = registrado;
    }
}

