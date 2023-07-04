package com.example.tfg_biblioteca.Clases;

public class Asiento {

    private int idAsiento;
    private Mesa mesa;
    private boolean estaOcupdo;

    private int numAsiento;

    public Asiento(){

    }

    public Asiento(int idAsiento, Mesa mesa, boolean estaOcupdo, int numasiento) {
        this.idAsiento = idAsiento;
        this.mesa = mesa;
        this.estaOcupdo = estaOcupdo;
        this.numAsiento = numasiento;
    }

    public int getNumAsiento() {
        return numAsiento;
    }

    public void setNumAsiento(int numAsiento) {
        this.numAsiento = numAsiento;
    }

    public int getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(int idAsiento) {
        this.idAsiento = idAsiento;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public boolean isEstaOcupdo() {
        return estaOcupdo;
    }

    public void setEstaOcupdo(boolean estaOcupdo) {
        this.estaOcupdo = estaOcupdo;
    }
}
