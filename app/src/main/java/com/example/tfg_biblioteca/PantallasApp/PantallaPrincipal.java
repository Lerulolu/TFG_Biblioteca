package com.example.tfg_biblioteca.PantallasApp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.R;
import com.example.tfg_biblioteca.ReservaLibros.PantallaPrincipalReservaLibro;

public class PantallaPrincipal extends AppCompatActivity {

    Bundle bundle;

    ImageButton btnBiblioteca, btnReservaSitio, btnAjustes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        bundle = getIntent().getExtras();

        Usuario usuario = (Usuario) bundle.getSerializable("usuario");


        this.btnBiblioteca = findViewById(R.id.btnBiblioteca);
        this.btnReservaSitio = findViewById(R.id.btnReservaSitio);
        this.btnAjustes = findViewById(R.id.btnAjustes);

        btnReservaSitio.setOnClickListener(view -> {

        });

        btnBiblioteca.setOnClickListener(view -> {
            Intent myIntent = new Intent(view.getContext(), PantallaPrincipalReservaLibro.class);
            myIntent.putExtra("usuario", usuario);
            startActivity(myIntent);
        });

        btnAjustes.setOnClickListener(view -> {

        });

    }

    public void onBackPressed(){

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        dialogo.setTitle("Cerrar Sesion").
                setMessage("¿Estas seguro de que quieres salir de la aplicación?")
                .setPositiveButton("Aceptar", (dialogInterface, i) -> {
                    Intent myIntent = new Intent(this, Login.class);
                    startActivity(myIntent);
                })
                .setNegativeButton("Cancelar", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });

        AlertDialog dialog = dialogo.create();
        dialog.show();

    }
}