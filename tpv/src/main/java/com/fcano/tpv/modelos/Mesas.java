package com.fcano.tpv.modelos;

/**
 * Created by Fernando on 11/05/2014.
 */
public class Mesas {

    private int id;
    private String nombre_mesa;

    public Mesas(int id, String nombre_mesa) {
        this.id = id;
        this.nombre_mesa = nombre_mesa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_mesa() {
        return nombre_mesa;
    }

    public void setNombre_mesa(String nombre_mesa) {
        this.nombre_mesa = nombre_mesa;
    }
}
