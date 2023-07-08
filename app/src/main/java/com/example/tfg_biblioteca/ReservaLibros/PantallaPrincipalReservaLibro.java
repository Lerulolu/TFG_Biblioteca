package com.example.tfg_biblioteca.ReservaLibros;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.PantallasApp.Login;
import com.example.tfg_biblioteca.PantallasApp.Utilidades;
import com.example.tfg_biblioteca.R;

public class PantallaPrincipalReservaLibro extends AppCompatActivity {

    ImageButton btnReservarLibro, btnCancelarReserva, btnConsultarReserva;

    ImageButton btnSalirPPRL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal_reserva_libro);

        this.btnReservarLibro = findViewById(R.id.btnReservarLibro);
        this.btnCancelarReserva = findViewById(R.id.btnCancelarReserva);
        this.btnConsultarReserva = findViewById(R.id.btnConsultarReserva);
        this.btnSalirPPRL = findViewById(R.id.btnSalirPPRL);


        btnReservarLibro.setOnClickListener(view -> {

            Intent myIntent = new Intent(view.getContext(), ReservarListaLibros.class);
            startActivity(myIntent);

        });

        btnCancelarReserva.setOnClickListener(view -> {

            Intent myIntent = new Intent(view.getContext(), CancelarReservaLibro.class);
            startActivity(myIntent);

        });

        btnConsultarReserva.setOnClickListener(view -> {

            Intent myIntent = new Intent(view.getContext(), ConsultarListaReservasLibro.class);
            startActivity(myIntent);

        });

        btnSalirPPRL.setOnClickListener(view -> Utilidades.getMyUtilidades().cerrarSesion(this));

    }





}