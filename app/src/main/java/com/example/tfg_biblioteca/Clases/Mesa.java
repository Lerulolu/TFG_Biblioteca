package com.example.tfg_biblioteca.Clases;

import java.io.Serializable;

public class Mesa implements Serializable {

    private int idMesa;
    private int numeroMesa;

    private Plantas planta;

    public Mesa(){

    }

    public Mesa(int idMesa, int numeroMesa, Plantas planta) {
        this.idMesa = idMesa;
        this.numeroMesa = numeroMesa;
        this.planta = planta;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public Plantas getPlanta() {
        return planta;
    }

    public void setPlanta(Plantas planta) {
        this.planta = planta;
    }

    @Override
    public String toString() {
        String cadena = "Mesa " + numeroMesa + "\n";
        return cadena;
    }
}
