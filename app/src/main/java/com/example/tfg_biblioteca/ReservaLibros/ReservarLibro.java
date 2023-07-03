package com.example.tfg_biblioteca.ReservaLibros;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_biblioteca.Clases.Libro;
import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.PantallasApp.Login;
import com.example.tfg_biblioteca.PantallasApp.PantallaPrincipal;
import com.example.tfg_biblioteca.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReservarLibro extends AppCompatActivity {

    TextView autorLibro, ISBNLibro, nombreLibro, descripcionLibro;

    Button btnReservarLibro;

    ImageView fotoLibro;

    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_libro);

        bundle = getIntent().getExtras();
        Libro libro = (Libro) bundle.getSerializable("libro");
        Usuario usuario = (Usuario) bundle.getSerializable("usuario");

        autorLibro = findViewById(R.id.autorLibro);
        ISBNLibro = findViewById(R.id.ISBNLibro);
        nombreLibro = findViewById(R.id.nombreLibro);
        descripcionLibro = findViewById(R.id.descripcionLibro);
        btnReservarLibro = findViewById(R.id.btnReservarLibro);
        fotoLibro = findViewById(R.id.fotoLibro);


        autorLibro.setText(libro.getAutorLibro());
        ISBNLibro.setText(libro.getISBN());
        nombreLibro.setText(libro.getNombreLibro());
        descripcionLibro.setText(libro.getDescripcionLibro());
        fotoLibro.setImageURI(Uri.parse("/src/main/res/drawable/ajustes.png"));
        btnReservarLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialogo = new AlertDialog.Builder(view.getContext());

                dialogo.setTitle("Realizar Reserva").
                        setMessage("¿Deseas realizar la reserva de este libro?")
                        .setPositiveButton("Aceptar", (dialogInterface, i) -> {
                            realizarReservaLibro(libro, usuario, "http://192.168.0.37:80/proyecto_tfg/realizarReserva.php");

                            Intent myIntent = new Intent(view.getContext(), PantallaPrincipal.class);
                            myIntent.putExtra("usuario", usuario);
                            startActivity(myIntent);
                        })
                        .setNegativeButton("Cancelar", (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        });

                AlertDialog dialog = dialogo.create();
                dialog.show();

            }
        });

    }


    private void realizarReservaLibro(Libro libro, Usuario usuario, String URL){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                response -> {
                    Toast.makeText(this, "Reserva del libro realizada", Toast.LENGTH_LONG).show();
                },
                error -> Toast.makeText(this, "No se ha realizado la reserva del libro", Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idLibro", ""+libro.getIdLibro());
                params.put("idUsuario", ""+usuario.getIdUsuario());
                params.put("fechaAlta", ""+Calendar.getInstance().getTime());
                params.put("fechaBaja", ""+Calendar.getInstance().getTime());
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

}