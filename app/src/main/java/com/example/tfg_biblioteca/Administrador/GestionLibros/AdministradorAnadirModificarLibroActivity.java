package com.example.tfg_biblioteca.Administrador.GestionLibros;

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
import com.example.tfg_biblioteca.Clases.Libro;
import com.example.tfg_biblioteca.ControladorUsuarioComun.Utilidades;
import com.example.tfg_biblioteca.R;

import java.util.HashMap;
import java.util.Map;

public class AdministradorAnadirModificarLibroActivity extends AppCompatActivity {

    private EditText nombreLibro, autorLibro, numeroISBN, descripcionLibro;
    private Button btnAnadirModificar;
    private Libro libro;
    private ImageButton btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador_anadir_modificar_libro);

        nombreLibro = findViewById(R.id.nombreLibro);
        autorLibro = findViewById(R.id.autorLibro);
        numeroISBN = findViewById(R.id.numeroISBN);
        descripcionLibro = findViewById(R.id.descripcionLibro);
        btnAnadirModificar = findViewById(R.id.btnAnadirModificar);
        btnSalir = findViewById(R.id.btnSalir);

        btnSalir.setOnClickListener(v -> Utilidades.getMyUtilidades().cerrarSesion(this));

        String accion = (String) getIntent().getExtras().getSerializable("accion");

        if (accion.equals(getString(R.string.administrador_modificar))) {

            libro = (Libro) getIntent().getExtras().getSerializable("libro");

            nombreLibro.setText(String.valueOf(libro.getNombreLibro()));
            autorLibro.setText(String.valueOf(libro.getAutorLibro()));
            numeroISBN.setText(String.valueOf(libro.getISBN()));
            descripcionLibro.setText(String.valueOf(libro.getDescripcionLibro()));

            btnAnadirModificar.setText(getString(R.string.administrador_modificar));

            btnAnadirModificar.setOnClickListener(view -> {

                if(nombreLibro.getText().toString().equals("")){
                    Toast.makeText(this, R.string.administrador_insertaNombreLibro, Toast.LENGTH_LONG).show();
                }
                else if(autorLibro.getText().toString().equals("")){
                    Toast.makeText(this, R.string.administrador_insertaAutorLibro, Toast.LENGTH_LONG).show();
                }
                else if (numeroISBN.getText().toString().equals("")) {
                    Toast.makeText(this, R.string.administrador_insertaISBNLibro, Toast.LENGTH_LONG).show();
                }
                else if(descripcionLibro.getText().toString().equals("")){
                    Toast.makeText(this, R.string.administrador_insertaDescripcionLibro, Toast.LENGTH_LONG).show();
                }
                else{

                    AlertDialog.Builder dialogo = new AlertDialog.Builder(view.getContext());

                    dialogo.setTitle(getString(R.string.administrador_modificar)).
                            setMessage(getString(R.string.administrador_modificarPregunta))
                            .setPositiveButton(getString(R.string.aceptar), (dialogInterface, j) -> {

                                updateLibro();

                            })
                            .setNegativeButton(getString(R.string.cancelar), (dialogInterface, j) -> {
                                dialogInterface.dismiss();
                            });

                    AlertDialog dialog = dialogo.create();
                    dialog.show();

                }
            });

        }
        else{

            btnAnadirModificar.setText(getString(R.string.administrador_anadir));

            btnAnadirModificar.setOnClickListener(view -> {

                if(nombreLibro.getText().toString().equals("")){
                    Toast.makeText(this, R.string.administrador_insertaNombreLibro, Toast.LENGTH_LONG).show();
                }
                else if(autorLibro.getText().toString().equals("")){
                    Toast.makeText(this, R.string.administrador_insertaAutorLibro, Toast.LENGTH_LONG).show();
                }
                else if (numeroISBN.getText().toString().equals("")) {
                    Toast.makeText(this, R.string.administrador_insertaISBNLibro, Toast.LENGTH_LONG).show();
                }
                else if(descripcionLibro.getText().toString().equals("")){
                    Toast.makeText(this, R.string.administrador_insertaDescripcionLibro, Toast.LENGTH_LONG).show();
                }
                else{

                    AlertDialog.Builder dialogo = new AlertDialog.Builder(view.getContext());

                    dialogo.setTitle(getString(R.string.administrador_anadir)).
                            setMessage(getString(R.string.administrador_anadirPregunta))
                            .setPositiveButton(getString(R.string.aceptar), (dialogInterface, j) -> {

                                anadirLibro();

                            })
                            .setNegativeButton(getString(R.string.cancelar), (dialogInterface, j) -> {
                                dialogInterface.dismiss();
                            });

                    AlertDialog dialog = dialogo.create();
                    dialog.show();

                }


            });


        }

    }

    private void anadirLibro() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/administrador_anadirLibro.php",

                response -> {
                    Toast.makeText(this, R.string.administrador_libroAnadido, Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(this, PantallaPrincipalAdministradorActivity.class);
                    startActivity(myIntent);
                },
                error -> Toast.makeText(this, R.string.errorGenerico, Toast.LENGTH_LONG).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombreLibro", nombreLibro.getText().toString());
                params.put("autorLibro", autorLibro.getText().toString());
                params.put("numeroISBN", numeroISBN.getText().toString());
                params.put("descripcionLibro", descripcionLibro.getText().toString());
                return params;
            }

        };

        requestQueue.add(stringRequest);

    }

    private void updateLibro() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/administrador_updateLibro.php",

                response ->{
                    Toast.makeText(this, R.string.administrador_libroModificado, Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(this, PantallaPrincipalAdministradorActivity.class);
                    startActivity(myIntent);
                },
                error -> Toast.makeText(this, R.string.errorGenerico, Toast.LENGTH_LONG).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idLibro", String.valueOf(libro.getIdLibro()));
                params.put("nombreLibro", nombreLibro.getText().toString());
                params.put("autorLibro", autorLibro.getText().toString());
                params.put("numeroISBN", numeroISBN.getText().toString());
                params.put("descripcionLibro", descripcionLibro.getText().toString());
                return params;
            }

        };

        requestQueue.add(stringRequest);

    }
}