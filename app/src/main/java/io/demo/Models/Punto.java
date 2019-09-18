package io.demo.Models;

public class Punto {

    private long latitud;
    private long longitud;

    public Punto() {
    }

    public Punto(int latitud, int longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public long getLatitud() {
        return latitud;
    }

    public void setLatitud(int latitud) {
        this.latitud = latitud;
    }

    public long getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }
}
