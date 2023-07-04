package com.example.tfg_biblioteca.Clases;

import java.io.Serializable;
import java.util.ArrayList;

public class Plantas  implements Serializable {

    private int idPlanta;

    private int numPlanta;

    ArrayList<Mesa> listaMesas;

    public Plantas(){
        listaMesas = new ArrayList<>();
    }



    public Plantas(int idPlanta, int numPlanta) {
        this.idPlanta = idPlanta;
        this.numPlanta = numPlanta;
        listaMesas = new ArrayList<>();
    }

    public int getIdPlanta() {
        return idPlanta;
    }

    public void setIdPlanta(int idPlanta) {
        this.idPlanta = idPlanta;
    }

    public int getNumPlanta() {
        return numPlanta;
    }

    public void setNumPlanta(int numPlanta) {
        this.numPlanta = numPlanta;
    }

    @Override
    public String toString() {
        String cadena = "Planta " + numPlanta + "\n" ;
        return cadena;
    }
}
