package com.example.tfg_biblioteca.ReservaLibros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_biblioteca.Adaptadores.AdaptadorReservaLibro;
import com.example.tfg_biblioteca.Clases.Libro;
import com.example.tfg_biblioteca.Clases.ReservaLibro;
import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.ControladorUsuarioComun.Utilidades;
import com.example.tfg_biblioteca.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConsultarListaReservasLibroActivity extends AppCompatActivity {

    private Usuario usuario;
    private ListView layoutListaConsultaReservas;
    private ArrayList<ReservaLibro> listaReservasLibros;
    private ImageButton btnSalir;
    private AdaptadorReservaLibro adapter;
    private ReservaLibro reservaElegida, reservaLibro;
    private Libro libro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_lista_reservas_libro);

        layoutListaConsultaReservas = findViewById(R.id.layoutListaConsultaReservas);

        btnSalir = findViewById(R.id.btnSalir);

        usuario = Utilidades.getMyUtilidades().obtenerUsuario(this);

        cargarListaDeReservas();

        layoutListaConsultaReservas.setOnItemClickListener((adapterView, view, i, l) -> {

            reservaElegida = listaReservasLibros.get(i);
            Intent myIntent = new Intent(view.getContext(), ConsultarReservaLibroActivity.class);
            myIntent.putExtra("reservaElegida",reservaElegida);
            startActivity(myIntent);

        });

        btnSalir.setOnClickListener(view -> Utilidades.getMyUtilidades().cerrarSesion(this));
    }

    private void cargarListaDeReservas(){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/libros_obtener_lista_reservas_alumno.php",

                response -> {

                    JSONArray jsonArray;

                    listaReservasLibros = new ArrayList<>();

                    try {

                        jsonArray = new JSONArray(response);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            libro = new Libro(
                                    jsonObject.getInt("idLibro"),
                                    jsonObject.getString("ISBN"),
                                    jsonObject.getString("tituloLibro"),
                                    jsonObject.getString("autorLibro"),
                                    jsonObject.getString("descripcionLibro"),
                                    jsonObject.getString("srcImagenLibro"));


                            reservaLibro = new ReservaLibro(
                                    jsonObject.getInt("idReservaLibro"),
                                    jsonObject.getString("fechaReserva")
                            );

                            reservaLibro.setLibro(libro);

                            listaReservasLibros.add(reservaLibro);

                        }

                        adapter = new AdaptadorReservaLibro(this, listaReservasLibros);
                        layoutListaConsultaReservas.setAdapter(adapter);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                },
                error -> Toast.makeText(this, R.string.errorGenerico, Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idUsuario", ""+usuario.getIdUsuario());
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

}