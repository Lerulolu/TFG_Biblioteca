package com.example.tfg_biblioteca.Clases;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class Planta extends AppCompatActivity implements Serializable {

    private int idPlanta;

    private int numPlanta;


    public Planta(int idPlanta, int numPlanta) {
        this.idPlanta = idPlanta;
        this.numPlanta = numPlanta;
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


}
