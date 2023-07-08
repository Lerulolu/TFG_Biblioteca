package com.example.tfg_biblioteca.ReservaAsientos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.PantallasApp.Utilidades;
import com.example.tfg_biblioteca.R;
import com.example.tfg_biblioteca.ReservaLibros.ReservarListaLibros;

public class PantallaPrincipalReservaAsientos extends AppCompatActivity {

    ImageButton btnReservarAsiento, btnCancelarReserva, btnConsultarReserva, btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal_reserva_asientos);

        this.btnReservarAsiento = findViewById(R.id.btnReservarAsiento);
        this.btnConsultarReserva = findViewById(R.id.btnConsultarReservaAsiento);
        this.btnCancelarReserva = findViewById(R.id.btnCancelarReservaAsiento);
        this.btnSalir = findViewById(R.id.btnSalir);

        btnReservarAsiento.setOnClickListener(view -> {

            Intent myIntent = new Intent(view.getContext(), ReservarAsiento.class);
            startActivity(myIntent);

        });

        btnConsultarReserva.setOnClickListener(view -> {
            Intent myIntent = new Intent(view.getContext(), ConsultarListaReservasAsiento.class);
            startActivity(myIntent);
        });

        btnCancelarReserva.setOnClickListener(view -> {
            Intent myIntent = new Intent(view.getContext(), CancelarReservaAsiento.class);
            startActivity(myIntent);
        });

        btnSalir.setOnClickListener(view -> Utilidades.getMyUtilidades().cerrarSesion(this));

    }
}