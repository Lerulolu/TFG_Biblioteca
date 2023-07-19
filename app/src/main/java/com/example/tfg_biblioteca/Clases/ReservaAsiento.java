package com.example.tfg_biblioteca.Clases;

public class ReservaAsiento {

    private int idReservaAsiento;

    private Asiento asiento;

    private String fechaReserva;

    private Usuario usuario;

    public ReservaAsiento(){

    }

    public ReservaAsiento(int idReservaAsiento, Asiento asiento, String fechaReserva, Usuario usuario) {
        this.idReservaAsiento = idReservaAsiento;
        this.asiento = asiento;
        this.fechaReserva = fechaReserva;
        this.usuario = usuario;
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

    public Usuario getIdUsuario() {
        return usuario;
    }

    public void setIdUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


}
