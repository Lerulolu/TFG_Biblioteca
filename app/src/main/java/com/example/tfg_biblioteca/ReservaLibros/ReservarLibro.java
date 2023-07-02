package com.example.tfg_biblioteca.ReservaLibros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_biblioteca.Clases.Libro;
import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReservarLibro extends AppCompatActivity {

    Bundle bundle;

    ListView layoutListaReserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_libro);

        this.layoutListaReserva = findViewById(R.id.layoutListaReserva);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //PONEMOS EL ARRAY DE LIBROS DENTRO DE EL LAYOUT
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/obtenerLibrosSinReserva.php",

                response -> {

                    JSONArray jsonArray;

                    ArrayList<Libro> listaLibros = new ArrayList<>();

                    try {

                        jsonArray = new JSONArray(response);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Libro libro = new Libro(
                                    jsonObject.getInt("idLibro"),
                                    jsonObject.getString("ISBN"),
                                    jsonObject.getString("tituloLibro"),
                                    jsonObject.getString("autorLibro"),
                                    null, null);

                            listaLibros.add(libro);

                        }

                        ArrayAdapter<Libro> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaLibros);

                        layoutListaReserva.setAdapter(adapter);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                },
                error -> {
                    Toast.makeText(this, "No se han podido recuperar los objetos", Toast.LENGTH_LONG).show();
                });

        requestQueue.add(stringRequest);

    }




}