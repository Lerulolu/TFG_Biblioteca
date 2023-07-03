package com.example.tfg_biblioteca.ReservaAsientos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.tfg_biblioteca.R;
import com.example.tfg_biblioteca.ReservaLibros.ReservarListaLibros;

public class PantallaPrincipalReservaAsientos extends AppCompatActivity {

    ImageButton btnReservarAsiento, btnCancelarReserva, btnConsultarReserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal_reserva_asientos);

        this.btnReservarAsiento = findViewById(R.id.btnReservarAsiento);
        this.btnConsultarReserva = findViewById(R.id.btnConsultarReservaAsiento);
        this.btnCancelarReserva = findViewById(R.id.btnCancelarReservaAsiento);

        btnConsultarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnConsultarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ReservarListaLibros.class);
                startActivity(myIntent);
            }
        });

        btnCancelarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}