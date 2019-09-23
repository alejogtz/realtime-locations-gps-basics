package io.demo.Models;

public class Producto {
    private static int ids = 1;

    private String id;
    private String descripcion;
    private int cantidad;
    private int precio;

    public Producto(String descripcion, int cantidad, int precio) {
        this.id = String.valueOf(ids++);
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public Producto() {
        this("Producto generico" + (ids+1), 1, 50);
    }

    public int getTotal(){
        return cantidad * precio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion + "   (" + cantidad + ")" + "Piezas";
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }



}
