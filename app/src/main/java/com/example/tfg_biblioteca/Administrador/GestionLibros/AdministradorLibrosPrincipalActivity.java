package com.example.tfg_biblioteca.Administrador.GestionLibros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.tfg_biblioteca.ControladorUsuarioComun.Utilidades;
import com.example.tfg_biblioteca.R;

public class AdministradorLibrosPrincipalActivity extends AppCompatActivity {

    private Button btnAnadir, btnEliminarLibro, btnModificarLibro;

    private ImageButton btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador_libros_principal);

        btnAnadir = findViewById(R.id.btnAnadir);
        btnEliminarLibro = findViewById(R.id.btnEliminarLibro);
        btnModificarLibro = findViewById(R.id.btnModificarLibro);
        btnSalir = findViewById(R.id.btnSalir);

        btnSalir.setOnClickListener(v -> Utilidades.getMyUtilidades().cerrarSesion(this));

        btnAnadir.setOnClickListener(view -> {
            Intent myIntent = new Intent(this, AdministradorAnadirModificarLibroActivity.class);
            myIntent.putExtra("accion", getString(R.string.administrador_anadir));
            startActivity(myIntent);
        });

        btnEliminarLibro.setOnClickListener(view -> {
            Intent myIntent = new Intent(this, AdministradorModificarListaLibrosActivity.class);
            myIntent.putExtra("accion", getString(R.string.administrador_eliminar));
            startActivity(myIntent);
        });

        btnModificarLibro.setOnClickListener(view -> {
            Intent myIntent = new Intent(this, AdministradorModificarListaLibrosActivity.class);
            myIntent.putExtra("accion", getString(R.string.administrador_modificar));
            startActivity(myIntent);
        });

    }
}