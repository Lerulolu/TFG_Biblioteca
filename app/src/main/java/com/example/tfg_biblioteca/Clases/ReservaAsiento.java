package com.example.tfg_biblioteca.Clases;

import java.util.Date;

public class ReservaAsiento {

    private int idReservaAsiento;
    private int idMesa;
    private Date fechaReserva;
    private int idAlumno;

    public ReservaAsiento(){

    }

    public int getIdReservaAsiento() {
        return idReservaAsiento;
    }

    public void setIdReservaAsiento(int idReservaAsiento) {
        this.idReservaAsiento = idReservaAsiento;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }
}
