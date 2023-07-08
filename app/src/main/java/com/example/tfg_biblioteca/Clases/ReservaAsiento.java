package com.example.tfg_biblioteca.Clases;

import java.util.Date;

public class ReservaAsiento {

    private int idReservaAsiento;

    private Asiento asiento;

    private String fechaReserva;

    private int idUsuario;

    public ReservaAsiento(){

    }

    public ReservaAsiento(int idReservaAsiento, Asiento asiento, String fechaReserva, int idUsuario) {
        this.idReservaAsiento = idReservaAsiento;
        this.asiento = asiento;
        this.fechaReserva = fechaReserva;
        this.idUsuario = idUsuario;
    }

    public ReservaAsiento(int idReservaAsiento) {
        this.idReservaAsiento = idReservaAsiento;
    }

    public int getIdReservaAsiento() {
        return idReservaAsiento;
    }

    public void setIdReservaAsiento(int idReservaAsiento) {
        this.idReservaAsiento = idReservaAsiento;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String toString() {

        String cadena =
                "\nID Reserva: " + idReservaAsiento + "\n\n" +
                        "Nº Planta: " + asiento.getIdAsiento() + "\n\n" +
                        "Nº Mesa: " + asiento.getMesa().getNumeroMesa() + "\n\n" +
                        "Nº Asiento: " + asiento.getMesa().getPlanta().getNumPlanta() + "\n\n" +
                        "Fecha Reserva: " + fechaReserva + "\n";

        return cadena;
    }
}
