package com.example.tfg_biblioteca.ReservaLibros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_biblioteca.Clases.Libro;
import com.example.tfg_biblioteca.PantallasApp.PantallaPrincipal;
import com.example.tfg_biblioteca.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

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

            Intent myIntent = new Intent(view.getContext(), ReservarLibro.class);
            startActivity(myIntent);

        });

        btnCancelarReserva.setOnClickListener(view -> {

        });

        btnConsultarReserva.setOnClickListener(view -> {

        });

    }



}