package com.example.tfg_biblioteca.Clases;

import com.example.tfg_biblioteca.R;

import java.io.Serializable;

public class Mesa implements Serializable {

    private int idMesa;
    private int numeroMesa;
    private Planta planta;

    public Mesa(){

    }

    public Mesa(int idMesa, int numeroMesa, Planta planta) {
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

    public Planta getPlanta() {
        return planta;
    }

    public void setPlanta(Planta planta) {
        this.planta = planta;
    }

    @Override
    public String toString() {
        return  "Mesa "+numeroMesa;
    }
}
