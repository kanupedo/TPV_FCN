package com.fcano.tpv.modelos;

/**
 * Created by Fernando on 19/06/2014.
 */
public class Detalle {

    //Atributos Detalles
    private int COD_PED;
    private int ticket;
    private int linea;
    private int COD_PROD;
    private String detalle;
    private int dto;
    private int cantidad;
    private float precio;
    private int iva;
    private float pvp;
    private float total;

    public Detalle() {


    }

    public Detalle(int COD_PED, int linea, int COD_PROD, String detalle, int dto, int cantidad, float precio, int iva, float pvp, int ticket, float total) {
        this.COD_PED = COD_PED;
        this.linea = linea;
        this.COD_PROD = COD_PROD;
        this.detalle = detalle;
        this.dto = dto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.iva = iva;
        this.pvp = pvp;
        this.ticket = ticket;
        this.total = total;
    }


    public int getCOD_PED() {
        return COD_PED;
    }

    public void setCOD_PED(int COD_PED) {
        this.COD_PED = COD_PED;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }


    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getCOD_PROD() {
        return COD_PROD;
    }

    public void setCOD_PROD(int COD_PROD) {
        this.COD_PROD = COD_PROD;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getDto() {
        return dto;
    }

    public void setDto(int dto) {
        this.dto = dto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public int getIva() {
        return iva;
    }

    public float getPvp() {
        return pvp;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}//END CLASE
