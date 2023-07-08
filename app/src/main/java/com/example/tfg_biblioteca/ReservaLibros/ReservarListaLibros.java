package com.example.tfg_biblioteca.ReservaLibros;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_biblioteca.Adaptadores.AdaptadorLibro;
import com.example.tfg_biblioteca.Clases.Libro;
import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.PantallasApp.Login;
import com.example.tfg_biblioteca.PantallasApp.Utilidades;
import com.example.tfg_biblioteca.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReservarListaLibros extends AppCompatActivity {

    private ArrayList<Libro> listaLibros;

    ListView listViewLibros;

    private AdaptadorLibro adapter;

    ImageButton btnSalir;

    SearchView busquedaLibro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reservar_lista_libros);

        this.listViewLibros = findViewById(R.id.layoutListaReserva);

        this.btnSalir = findViewById(R.id.btnSalir);

        busquedaLibro = findViewById(R.id.busquedaLibro);

        cargarListaLibrosSinReserva();

        listViewLibros.setOnItemClickListener((adapterView, view, i, l) -> {

            Intent myIntent = new Intent(view.getContext(), ReservarLibro.class);
            Libro libroElegido = listaLibros.get(i);
            myIntent.putExtra("libro",libroElegido);
            startActivity(myIntent);

        });

        btnSalir.setOnClickListener(view -> Utilidades.getMyUtilidades().cerrarSesion(this));

    }

    public void cargarListaLibrosSinReserva(){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

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

                        adapter = new AdaptadorLibro(this, listaLibros);
                        listViewLibros.setAdapter(adapter);

                        busquedaLibro.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String s) {
                              //  adapter.getFilter().filter(s);
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String s) {
                                adapter.getFilter().filter(s);
                                return true;
                            }
                        });

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