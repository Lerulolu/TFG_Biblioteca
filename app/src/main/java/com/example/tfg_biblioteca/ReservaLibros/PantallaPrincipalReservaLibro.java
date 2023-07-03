package com.example.tfg_biblioteca.ReservaLibros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.tfg_biblioteca.R;

public class PantallaPrincipalReservaLibro extends AppCompatActivity {

    ImageButton btnReservarLibro, btnCancelarReserva, btnConsultarReserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal_reserva_libro);

        this.btnReservarLibro = findViewById(R.id.btnReservarLibro);
        this.btnCancelarReserva = findViewById(R.id.btnCancelarReserva);
        this.btnConsultarReserva = findViewById(R.id.btnConsultarReserva);

        btnReservarLibro.setOnClickListener(view -> {

            Intent myIntent = new Intent(view.getContext(), ReservarListaLibros.class);
            startActivity(myIntent);

        });

        btnCancelarReserva.setOnClickListener(view -> {

        });

        btnConsultarReserva.setOnClickListener(view -> {

        });

    }



}