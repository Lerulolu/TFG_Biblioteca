package com.example.tfg_biblioteca.ReservaLibros;

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
import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.PantallasApp.Informacion;
import com.example.tfg_biblioteca.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReservarListaLibros extends AppCompatActivity {

    Bundle bundle;

    private ArrayList<Libro> listaLibros;

    ListView layoutListaReserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_lista_libros);

        this.layoutListaReserva = findViewById(R.id.layoutListaReserva);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        bundle = getIntent().getExtras();

        Usuario usuario = (Usuario) bundle.getSerializable("usuario");

        //PONEMOS EL ARRAY DE LIBROS DENTRO DE EL LAYOUT
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/obtenerLibrosSinReserva.php",

                response -> {

                    JSONArray jsonArray;

                    listaLibros = new ArrayList<>();

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

        layoutListaReserva.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent myIntent = new Intent(view.getContext(), ReservarLibro.class);
                Libro libroElegido = listaLibros.get(i);
                myIntent.putExtra("libro",libroElegido);
                myIntent.putExtra("usuario", usuario);
                startActivity(myIntent);

            }
        });

    }





}