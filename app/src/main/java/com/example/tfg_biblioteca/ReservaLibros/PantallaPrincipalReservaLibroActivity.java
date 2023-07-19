package com.example.tfg_biblioteca.ReservaLibros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.tfg_biblioteca.ControladorUsuarioComun.Utilidades;
import com.example.tfg_biblioteca.R;

public class PantallaPrincipalReservaLibroActivity extends AppCompatActivity {

    private ImageButton btnReservarLibro, btnCancelarReserva, btnConsultarReserva;

    private ImageButton btnSalirPPRL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal_reserva_libro);

        this.btnReservarLibro = findViewById(R.id.btnReservarLibro);
        this.btnCancelarReserva = findViewById(R.id.btnCancelarReserva);
        this.btnConsultarReserva = findViewById(R.id.btnConsultarReserva);
        this.btnSalirPPRL = findViewById(R.id.btnSalirPPRL);


        btnReservarLibro.setOnClickListener(view -> {

            Intent myIntent = new Intent(view.getContext(), ReservarListaLibrosActivity.class);
            startActivity(myIntent);

        });

        btnCancelarReserva.setOnClickListener(view -> {

            Intent myIntent = new Intent(view.getContext(), CancelarReservaLibroActivity.class);
            startActivity(myIntent);

        });

        btnConsultarReserva.setOnClickListener(view -> {

            Intent myIntent = new Intent(view.getContext(), ConsultarListaReservasLibroActivity.class);
            startActivity(myIntent);

        });

        btnSalirPPRL.setOnClickListener(view -> Utilidades.getMyUtilidades().cerrarSesion(this));

    }





}