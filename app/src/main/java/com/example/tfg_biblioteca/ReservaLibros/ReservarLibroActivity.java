package com.example.tfg_biblioteca.ReservaLibros;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_biblioteca.Clases.Libro;
import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.ControladorUsuarioComun.PantallaPrincipalActivity;
import com.example.tfg_biblioteca.ControladorUsuarioComun.Utilidades;
import com.example.tfg_biblioteca.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReservarLibroActivity extends AppCompatActivity {

    private TextView autorLibro, ISBNLibro, nombreLibro, descripcionLibro;
    private Button btnReservarLibro;
    private ImageView fotoLibro;
    private Bundle bundle;
    private ImageButton btnSalir;
    private Libro libro;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_libro);

        bundle = getIntent().getExtras();
        libro = (Libro) bundle.getSerializable("libro");
        usuario = Utilidades.getMyUtilidades().obtenerUsuario(this);

        autorLibro = findViewById(R.id.autorLibro);
        ISBNLibro = findViewById(R.id.ISBNLibro);
        nombreLibro = findViewById(R.id.nombreLibro);
        descripcionLibro = findViewById(R.id.descripcionLibro);
        btnReservarLibro = findViewById(R.id.btnReservarLibro);
        fotoLibro = findViewById(R.id.fotoLibro);
        btnSalir = findViewById(R.id.btnSalir);


        autorLibro.setText(libro.getAutorLibro());
        ISBNLibro.setText(libro.getISBN());
        nombreLibro.setText(libro.getNombreLibro());
        descripcionLibro.setText(libro.getDescripcionLibro());

        String nombreImagen = libro.getSrcImagenLibro();

        int resourceId = getResources().getIdentifier(nombreImagen, "drawable", getPackageName());
        fotoLibro.setImageResource(resourceId);

        btnReservarLibro.setOnClickListener(view -> {

            AlertDialog.Builder dialogo = new AlertDialog.Builder(view.getContext());

            dialogo.setTitle(R.string.reservaLibro_realizarReserva).
                    setMessage(R.string.reservaLibro_deseaReservar)
                    .setPositiveButton(R.string.aceptar, (dialogInterface, i) -> {

                        realizarReservaLibro(libro, usuario, "http://192.168.0.37:80/proyecto_tfg/realizarReserva.php");

                        Intent myIntent = new Intent(view.getContext(), PantallaPrincipalActivity.class);
                        myIntent.putExtra("usuario", usuario);
                        startActivity(myIntent);

                    })
                    .setNegativeButton(R.string.cancelar, (dialogInterface, i) -> dialogInterface.dismiss());

            AlertDialog dialog = dialogo.create();
            dialog.show();

        });

        btnSalir.setOnClickListener(view -> Utilidades.getMyUtilidades().cerrarSesion(this));

    }


    private void realizarReservaLibro(Libro libro, Usuario usuario, String URL){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                response -> {
                    Toast.makeText(this, R.string.reservaLibro_reservaRealizada, Toast.LENGTH_LONG).show();
                },
                error -> Toast.makeText(this, R.string.errorGenerico, Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {

                Date fechaActual = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Map<String, String> params = new HashMap<>();
                params.put("idLibro", ""+libro.getIdLibro());
                params.put("idUsuario", ""+usuario.getIdUsuario());
                params.put("fechaReserva", dateFormat.format(fechaActual));
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

}