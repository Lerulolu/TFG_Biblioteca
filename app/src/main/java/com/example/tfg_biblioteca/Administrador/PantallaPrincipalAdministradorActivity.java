package com.example.tfg_biblioteca.Administrador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tfg_biblioteca.Administrador.GestionLibros.AdministradorLibrosPrincipalActivity;
import com.example.tfg_biblioteca.Administrador.GestionUsarios.AdministradorListaAlumnosActivity;
import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.ControladorUsuarioComun.Utilidades;
import com.example.tfg_biblioteca.R;

public class PantallaPrincipalAdministradorActivity extends AppCompatActivity {

    private Button btnGestionUsuario, btnGestionLibro;
    private ImageButton btnSalir;
    private TextView nombreUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal_administrador);

        btnSalir = findViewById(R.id.btnSalir);
        btnGestionUsuario = findViewById(R.id.btnGestionUsuario);
        btnGestionLibro = findViewById(R.id.btnGestionLibro);
        nombreUsuario = findViewById(R.id.nombreUsuario);

        Usuario user = Utilidades.getMyUtilidades().obtenerUsuario(this);

        if(user != null){
            nombreUsuario.setText(user.getNombreUsuario());
        }

        btnSalir.setOnClickListener(view -> Utilidades.getMyUtilidades().cerrarSesion(this));

        btnGestionLibro.setOnClickListener(view -> {
            Intent myIntent = new Intent(this, AdministradorLibrosPrincipalActivity.class);
            startActivity(myIntent);
        });

        btnGestionUsuario.setOnClickListener(view -> {
            Intent myIntent = new Intent(this, AdministradorListaAlumnosActivity.class);
            startActivity(myIntent);
        });


    }
}