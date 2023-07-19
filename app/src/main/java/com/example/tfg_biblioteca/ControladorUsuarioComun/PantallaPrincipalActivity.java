package com.example.tfg_biblioteca.ControladorUsuarioComun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.tfg_biblioteca.R;
import com.example.tfg_biblioteca.ReservaAsientos.PantallaPrincipalReservaAsientosActivity;
import com.example.tfg_biblioteca.ReservaLibros.PantallaPrincipalReservaLibroActivity;

public class PantallaPrincipalActivity extends AppCompatActivity {

    private ImageButton btnBiblioteca, btnReservaSitio, btnAjustes, btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        this.btnBiblioteca = findViewById(R.id.btnBiblioteca);
        this.btnReservaSitio = findViewById(R.id.btnReservaSitio);
        this.btnAjustes = findViewById(R.id.btnAjustes);
        btnSalir = findViewById(R.id.btnSalir);

        btnReservaSitio.setOnClickListener(view -> {
            Intent myIntent = new Intent(view.getContext(), PantallaPrincipalReservaAsientosActivity.class);
            startActivity(myIntent);
        });

        btnBiblioteca.setOnClickListener(view -> {
            Intent myIntent = new Intent(view.getContext(), PantallaPrincipalReservaLibroActivity.class);
            startActivity(myIntent);
        });

        btnAjustes.setOnClickListener(view -> {
            Intent myIntent = new Intent(view.getContext(), AjustesLoginActivity.class);
            startActivity(myIntent);
        });

        btnSalir.setOnClickListener(view -> Utilidades.getMyUtilidades().cerrarSesion(this));

    }

    public void onBackPressed(){
        Utilidades.getMyUtilidades().cerrarSesion(this);
    }



}