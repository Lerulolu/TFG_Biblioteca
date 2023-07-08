package com.example.tfg_biblioteca.Clases;

import static com.example.tfg_biblioteca.PantallasApp.Ajustes.cambiarIdioma;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg_biblioteca.R;

import java.io.Serializable;
import java.util.ArrayList;

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

    @Override
    public String toString() {
        return  "Planta "+numPlanta;
    }
}
