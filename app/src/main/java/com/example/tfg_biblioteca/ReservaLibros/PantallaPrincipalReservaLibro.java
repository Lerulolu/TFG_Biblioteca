package com.example.tfg_biblioteca.ReservaLibros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.R;

public class PantallaPrincipalReservaLibro extends AppCompatActivity {

    Bundle bundle;
    ImageButton btnReservarLibro, btnCancelarReserva, btnConsultarReserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal_reserva_libro);

        this.btnReservarLibro = findViewById(R.id.btnReservarLibro);
        this.btnCancelarReserva = findViewById(R.id.btnCancelarReserva);
        this.btnConsultarReserva = findViewById(R.id.btnConsultarReserva);

        bundle = getIntent().getExtras();

        Usuario usuario = (Usuario) bundle.getSerializable("usuario");

        btnReservarLibro.setOnClickListener(view -> {

            Intent myIntent = new Intent(view.getContext(), ReservarListaLibros.class);
            myIntent.putExtra("usuario", usuario);
            startActivity(myIntent);

        });

        btnCancelarReserva.setOnClickListener(view -> {

            Intent myIntent = new Intent(view.getContext(), CancelarListaReservas.class);
            myIntent.putExtra("usuario", usuario);
            startActivity(myIntent);

        });

        btnConsultarReserva.setOnClickListener(view -> {

            Intent myIntent = new Intent(view.getContext(), ConsultarListaReservas.class);
            myIntent.putExtra("usuario", usuario);
            startActivity(myIntent);

        });

    }



}