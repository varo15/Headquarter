package com.headquarter.com.headquarter.activity.activity.objects;

public class Jugador {

    /**
     * Variables de la clase jugador
     */
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

    /**
     * Constructor vacio de la clase Jugador
     */
    public Jugador() {
    }

    /**
     * Constructor de la clase Jugador
     * @param idGoogle
     * @param DNI
     * @param nombre
     * @param fechaNacimiento
     * @param telefono
     * @param email
     * @param id_equipo_fk
     * @param equipo
     * @param numeroFAA
     * @param registrado
     */
    public Jugador(String idGoogle, String DNI, String nombre, String fechaNacimiento, String telefono, String email, int id_equipo_fk, String equipo, String numeroFAA, int registrado) {
        this.idGoogle = idGoogle;
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

    /**
     * Getter del id de Google
     * @return idGoogle
     */
    public String getIdGoogle() {
        return idGoogle;
    }

    /**
     * Setter del id de Google
     * @param idGoogle
     */
    public void setIdGoogle(String idGoogle) {
        this.idGoogle = idGoogle;
    }

    /**
     * Getter del DNI del jugador
     * @return DNI
     */
    public String getDNI() {
        return DNI;
    }

    /**
     * Setter del DNI del jugador
     * @param DNI
     */
    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    /**
     * Getter del nombre del jugador
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter del nombre del jugador
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter de la fecha de nacimiento del jugador
     * @return fechaNacimiento
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Setter de la fecha de nacimiento del jugador
     * @param fechaNacimiento
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Getter del telefono del jugador
     * @return telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Setter del telefono del jugador
     * @param telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Getter del email del jugador
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter del email del jugador
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter del id del equipo del jugador
     * @return id_equipo_fk
     */
    public int getId_equipo_fk() {
        return id_equipo_fk;
    }

    /**
     * Setter del id del equipo del jugador
     * @param id_equipo_fk
     */
    public void setId_equipo_fk(int id_equipo_fk) {
        this.id_equipo_fk = id_equipo_fk;
    }

    /**
     * Getter del equipo del jugador
     * @return equipo
     */
    public String getEquipo() {
        return equipo;
    }

    /**
     * Setter del equipo del jugador
     * @param equipo
     */
    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    /**
     * Getter del numero de federacion del jugador
     * @return numeroFAA
     */
    public String getNumeroFAA() {
        return numeroFAA;
    }

    /**
     * Setter del numero de federacion del jugador
     * @param numeroFAA
     */
    public void setNumeroFAA(String numeroFAA) {
        this.numeroFAA = numeroFAA;
    }

    /**
     * Getter del valor de registro del jugador
     * @return registrado
     */
    public int getRegistrado() {
        return registrado;
    }

    /**
     * Setter del valor de registro del jugador
     * @param registrado
     */
    public void setRegistrado(int registrado) {
        this.registrado = registrado;
    }
}

