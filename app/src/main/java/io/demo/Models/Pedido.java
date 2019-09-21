package io.demo.Models;

import java.io.Serializable;

public class Pedido implements Serializable {

    public final static int ENTREGADO = 1;
    public final static int SIN_PROCESAR = 2;
    public final static int EN_CAMINO = 3;

    private String id; // [Opcional]

    private String descripcion;
    private int costo;

    private Punto location;
    private Punto destino;

    private String fecha_entrega;
    private String fecha_realizacion;

    private String deliver_uid;

    private String cliente_uid;

    private int status;

    public Pedido() {
    }

    public Pedido(String id, String descripcion, int costo, Punto location, Punto destino, String fecha_entrega, String fecha_realizacion, String deliver_uid, String cliente_uid, int status) {
        this.id = id;
        this.descripcion = descripcion;
        this.costo = costo;
        this.location = location;
        this.destino = destino;
        this.fecha_entrega = fecha_entrega;
        this.fecha_realizacion = fecha_realizacion;
        this.deliver_uid = deliver_uid;
        this.cliente_uid = cliente_uid;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public Punto getLocation() {
        return location;
    }

    public void setLocation(Punto location) {
        this.location = location;
    }

    public Punto getDestino() {
        return destino;
    }

    public void setDestino(Punto destino) {
        this.destino = destino;
    }

    public String getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(String fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public String getFecha_realizacion() {
        return fecha_realizacion;
    }

    public void setFecha_realizacion(String fecha_realizacion) {
        this.fecha_realizacion = fecha_realizacion;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDeliver_uid() {
        return deliver_uid;
    }

    public void setDeliver_uid(String deliver_uid) {
        this.deliver_uid = deliver_uid;
    }

    public String getCliente_uid() {
        return cliente_uid;
    }

    public void setCliente_uid(String cliente_uid) {
        this.cliente_uid = cliente_uid;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "descripcion='" + descripcion + '\'' +
                ", costo=" + costo +
                ", status=" + status +
                '}';
    }
}
