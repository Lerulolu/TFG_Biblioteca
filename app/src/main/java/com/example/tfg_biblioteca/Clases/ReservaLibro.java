package com.example.tfg_biblioteca.Clases;

import androidx.recyclerview.widget.AsyncListUtil;

import java.util.Date;

public class ReservaLibro {

    private int idReservaLibro;
    private Libro libro;
    private Date fecha;
    private Usuario idUsuario;

    public ReservaLibro() {

    }

    public ReservaLibro(int idReservaLibro, Date fecha) {
        this.idReservaLibro = idReservaLibro;
        this.fecha = fecha;
    }

    public ReservaLibro(int idReservaLibro, Libro libro, Date fecha, Usuario idUsuario) {
        this.idReservaLibro = idReservaLibro;
        this.libro = libro;
        this.fecha = fecha;
        this.idUsuario = idUsuario;
    }

    public int getIdReservaLibro() {
        return idReservaLibro;
    }

    public void setIdReservaLibro(int idReservaLibro) {
        this.idReservaLibro = idReservaLibro;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        String cadena = "Autor Libro: " + libro.getAutorLibro() + "\n" +
                "Titulo Libro: " + libro.getNombreLibro() + "\n" +
                "ISBN: " + libro.getISBN() + "\n" +
                "Fecha Reserva: " + fecha.toString() + "\n";

        return cadena;
    }
}
