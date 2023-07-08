package com.example.tfg_biblioteca.Clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Libro implements Serializable {

    private int idLibro;
    private String ISBN;
    private String nombreLibro;
    private String autorLibro;
    private String descripcionLibro;
    private String srcImagenLibro;

    public Libro(int idLibro, String ISBN, String nombreLibro, String autorLibro, String descripcionLibro, String imagenLibro) {
        this.idLibro = idLibro;
        this.ISBN = ISBN;
        this.nombreLibro = nombreLibro;
        this.autorLibro = autorLibro;
        this.descripcionLibro = descripcionLibro;
        this.srcImagenLibro = imagenLibro;
    }

    public Libro() {

    }


    public String getDescripcionLibro() {
        return descripcionLibro;
    }

    public void setDescripcionLibro(String descripcionLibro) {
        this.descripcionLibro = descripcionLibro;
    }

    public String getSrcImagenLibro() {
        return srcImagenLibro;
    }

    public void setSrcImagenLibro(String srcImagenLibro) {
        this.srcImagenLibro = srcImagenLibro;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getNombreLibro() {
        return nombreLibro;
    }

    public void setNombreLibro(String nombreLibro) {
        this.nombreLibro = nombreLibro;
    }

    public String getAutorLibro() {
        return autorLibro;
    }

    public void setAutorLibro(String autorLibro) {
        this.autorLibro = autorLibro;
    }


    @Override
    public String toString() {
        String cadena =
                "\nAutor Libro: " + autorLibro + "\n\n" +
                "Titulo Libro: " + nombreLibro + "\n\n" +
                "ISBN: " + ISBN + "\n" ;
        return cadena;
    }



}
