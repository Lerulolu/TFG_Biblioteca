package com.example.tfg_biblioteca.PantallasApp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tfg_biblioteca.Clases.ReservaAsiento;
import com.example.tfg_biblioteca.Clases.ReservaLibro;
import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.R;
import com.example.tfg_biblioteca.ReservaAsientos.PantallaPrincipalReservaAsientos;
import com.example.tfg_biblioteca.ReservaAsientos.ReservarAsiento;
import com.example.tfg_biblioteca.ReservaLibros.PantallaPrincipalReservaLibro;
import com.example.tfg_biblioteca.ReservaLibros.ReservarLibro;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class PantallaPrincipal extends AppCompatActivity {

    private Usuario usuario;

    ImageButton btnBiblioteca, btnReservaSitio, btnAjustes;

    ImageButton btnSalir;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        usuario = Utilidades.getMyUtilidades().obtenerUsuario(this);

        this.btnBiblioteca = findViewById(R.id.btnBiblioteca);
        this.btnReservaSitio = findViewById(R.id.btnReservaSitio);
        this.btnAjustes = findViewById(R.id.btnAjustes);
        btnSalir = findViewById(R.id.btnSalir);

        btnReservaSitio.setOnClickListener(view -> {
            Intent myIntent = new Intent(view.getContext(), PantallaPrincipalReservaAsientos.class);
            startActivity(myIntent);
        });

        btnBiblioteca.setOnClickListener(view -> {
            Intent myIntent = new Intent(view.getContext(), PantallaPrincipalReservaLibro.class);
            startActivity(myIntent);
        });

        btnAjustes.setOnClickListener(view -> {
            Intent myIntent = new Intent(view.getContext(), AjustesLogin.class);
            startActivity(myIntent);
        });

        btnSalir.setOnClickListener(view -> Utilidades.getMyUtilidades().cerrarSesion(this));

    }

    public void onBackPressed(){
        Utilidades.getMyUtilidades().cerrarSesion(this);
    }



}