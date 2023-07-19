package com.example.tfg_biblioteca.Clases;

import java.io.Serializable;

public class Usuario implements Serializable {

    private int idUsuario;
    private int ldapUsuario;
    private String contrasenaUsuario;
    private String nombreUsuario;
    private int tipoUsuario;

    public Usuario() {

    }

    public Usuario(int idUsuario, int ldapUsuario, String nombreUsuario, String contrasenaUsuario, int tipoUsuario) {
        this.idUsuario = idUsuario;
        this.ldapUsuario = ldapUsuario;
        this.contrasenaUsuario = contrasenaUsuario;
        this.nombreUsuario = nombreUsuario;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getLdapUsuario() {
        return ldapUsuario;
    }

    public void setLdapUsuario(int ldapUsuario) {
        this.ldapUsuario = ldapUsuario;
    }

    public String getContrasenaUsuario() {
        return contrasenaUsuario;
    }

    public void setContrasenaUsuario(String contrasenaUsuario) {
        this.contrasenaUsuario = contrasenaUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
