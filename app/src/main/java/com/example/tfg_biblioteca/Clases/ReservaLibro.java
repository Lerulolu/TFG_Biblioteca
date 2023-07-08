package com.example.tfg_biblioteca.Clases;

import androidx.recyclerview.widget.AsyncListUtil;

import java.io.Serializable;
import java.util.Date;

public class ReservaLibro implements Serializable {

    private int idReservaLibro;
    private Libro libro;
    private String fechaReserva;
    private Usuario usuario;

    public ReservaLibro() {

    }

    public ReservaLibro(int idReservaLibro, String fecha) {
        this.idReservaLibro = idReservaLibro;
        this.fechaReserva = fecha;
    }

    public ReservaLibro(int idReservaLibro, Libro libro, String fecha, Usuario v) {
        this.idReservaLibro = idReservaLibro;
        this.libro = libro;
        this.fechaReserva = fecha;
        this.usuario = usuario;
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

    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fecha) {
        this.fechaReserva = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario idUsuario) {
        this.usuario = idUsuario;
    }

    @Override
    public String toString() {
        String cadena =
                "\nID Reserva: " + idReservaLibro + "\n\n" +
                "Autor Libro: " + libro.getAutorLibro() + "\n\n" +
                "Titulo Libro: " + libro.getNombreLibro() + "\n\n" +
                "ISBN: " + libro.getISBN() + "\n\n" +
                "Fecha Reserva: " + fechaReserva + "\n";

        return cadena;
    }


}
