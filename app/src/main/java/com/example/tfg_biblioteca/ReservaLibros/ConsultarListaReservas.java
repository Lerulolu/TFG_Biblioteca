package com.example.tfg_biblioteca.ReservaLibros;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_biblioteca.Clases.Libro;
import com.example.tfg_biblioteca.Clases.ReservaLibro;
import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConsultarListaReservas extends AppCompatActivity {

    Bundle bundle;

    Usuario usuario;

    ListView layoutListaReservas;

    ArrayList<ReservaLibro> listaReservasLibros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_lista_reservas);

        layoutListaReservas = findViewById(R.id.layoutListaReservas);

        bundle = getIntent().getExtras();

        usuario = (Usuario) bundle.getSerializable("usuario");

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //PONEMOS EL ARRAY DE LIBROS DENTRO DE EL LAYOUT
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/obtenerListaReservasAlumno.php",

                response -> {

                    JSONArray jsonArray;

                    listaReservasLibros = new ArrayList<>();

                    try {

                        jsonArray = new JSONArray(response);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Libro libro = new Libro(
                                    jsonObject.getInt("idLibro"),
                                    jsonObject.getString("ISBN"),
                                    jsonObject.getString("tituloLibro"),
                                    jsonObject.getString("autorLibro"),
                                    jsonObject.getString("descripcionLibro"),
                                    jsonObject.getString("srcImagenLibro"));


                            String fechaAct = jsonObject.getString("fechaAlta");

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                            Date date = new Date();

                            try {
                                date = dateFormat.parse(fechaAct);
                            } catch (ParseException e) {
                                e.printStackTrace();

                            }


                            ReservaLibro reservaLibro = new ReservaLibro(
                                    jsonObject.getInt("idReservaLibro"),
                                    date
                            );

                            reservaLibro.setLibro(libro);

                            listaReservasLibros.add(reservaLibro);

                        }

                        ArrayAdapter<ReservaLibro> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaReservasLibros);

                        layoutListaReservas.setAdapter(adapter);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                },
                error -> Toast.makeText(this, "Error al recuperar las reservas del usuario", Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idUsuario", ""+usuario.getIdUsuario());
                return params;
            }
        };

        requestQueue.add(stringRequest);

        layoutListaReservas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                ReservaLibro reservaElegida = listaReservasLibros.get(i);
                
            }
        });
    }
}