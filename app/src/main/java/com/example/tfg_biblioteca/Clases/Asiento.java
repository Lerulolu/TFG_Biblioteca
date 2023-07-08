package com.example.tfg_biblioteca.Clases;

public class Asiento {

    private int idAsiento;
    private Mesa mesa;
    private int numAsiento;

    public Asiento(int idAsiento, int numasiento, Mesa mesa) {
        this.idAsiento = idAsiento;
        this.mesa = mesa;
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

}
