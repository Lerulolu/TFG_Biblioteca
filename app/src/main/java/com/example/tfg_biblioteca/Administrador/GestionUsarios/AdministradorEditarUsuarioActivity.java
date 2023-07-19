package com.example.tfg_biblioteca.Administrador.GestionUsarios;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_biblioteca.Administrador.PantallaPrincipalAdministradorActivity;
import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.ControladorUsuarioComun.Utilidades;
import com.example.tfg_biblioteca.R;

import java.util.HashMap;
import java.util.Map;

public class AdministradorEditarUsuarioActivity extends AppCompatActivity {

    private Usuario usuario;

    private Button btnGuardar;

    private EditText nombreAlumno, LDAPalumno;

    private ImageButton btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador_editar_usuario);

        usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");

        btnGuardar = findViewById(R.id.btnGuardar);
        nombreAlumno = findViewById(R.id.nombreAlumno);
        LDAPalumno = findViewById(R.id.LDAPalumno);
        btnSalir = findViewById(R.id.btnSalir);

        nombreAlumno.setText(usuario.getNombreUsuario());
        LDAPalumno.setText(String.valueOf(usuario.getLdapUsuario()));

        btnGuardar.setOnClickListener(view -> {

            if(nombreAlumno.getText().toString().equals("")){
                Toast.makeText(this, R.string.administrador_insertaNombreAlumno, Toast.LENGTH_LONG).show();
            }
            else if(LDAPalumno.getText().toString().equals("")){
                Toast.makeText(this, R.string.administrador_insertaLDAP, Toast.LENGTH_LONG).show();
            }
            else{

                AlertDialog.Builder dialogo = new AlertDialog.Builder(view.getContext());

                dialogo.setTitle(getString(R.string.administrador_modificar)).
                        setMessage(getString(R.string.administrador_editarAlumno))
                        .setPositiveButton(getString(R.string.aceptar), (dialogInterface, j) -> {

                            actualizarAlumno();

                            Intent myIntent = new Intent(this, PantallaPrincipalAdministradorActivity.class);
                            startActivity(myIntent);

                        })
                        .setNegativeButton(getString(R.string.cancelar), (dialogInterface, j) -> {
                            dialogInterface.dismiss();
                        });

                AlertDialog dialog = dialogo.create();
                dialog.show();

            }

        });

        btnSalir.setOnClickListener(v -> Utilidades.getMyUtilidades().cerrarSesion(this));

    }

    private void actualizarAlumno() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/administrador_actualizarAlumno.php",

                response -> Toast.makeText(this, R.string.administrador_alumnoActualizado, Toast.LENGTH_LONG).show(),
                error -> Toast.makeText(this, R.string.administrador_noAlumnoActualizado, Toast.LENGTH_LONG).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idUsuario", String.valueOf(usuario.getIdUsuario()));
                params.put("nombreAlumno", nombreAlumno.getText().toString());
                params.put("LDAPalumno", LDAPalumno.getText().toString());
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }
}