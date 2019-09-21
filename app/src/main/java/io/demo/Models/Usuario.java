package io.demo.Models;

public class Usuario {

    private String nombre;
    private double latitud;
    private double longitud;

    private String rol;  //Roles: 'Deliver' | 'Cliente'

    public Usuario(String nombre, double latitud, double longitud, String rol) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
