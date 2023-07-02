package com.example.tfg_biblioteca.Clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class Libro  {

    private int idLibro;
    private String ISBN;
    private String nombreLibro;
    private String autorLibro;
    private Date fechaAlta;
    private Date fechaBaja;

    public Libro(int idLibro, String ISBN, String nombreLibro, String autorLibro, Date fechaAlta, Date fechaBaja) {
        this.idLibro = idLibro;
        this.ISBN = ISBN;
        this.nombreLibro = nombreLibro;
        this.autorLibro = autorLibro;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
    }

    public Libro() {

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

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    @Override
    public String toString() {
        String cadena = "Autor Libro: " + autorLibro + "\n" +
                "Titulo Libro: " + nombreLibro + "\n" +
                "ISBN: " + ISBN + "\n";
        return cadena;
    }



}
