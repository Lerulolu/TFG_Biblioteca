package com.example.tfg_biblioteca.ReservaAsientos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_biblioteca.Clases.Mesa;
import com.example.tfg_biblioteca.Clases.Plantas;
import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReservarAsiento extends AppCompatActivity {

    private ArrayList<Plantas> listaPlantas;
    private ArrayList<Mesa> listaMesas;
    Spinner seleccionPiso, seleccionMesa;
    Button btnBuscarSitios;
    Bundle bundle;

    Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_asiento);

        seleccionPiso = findViewById(R.id.seleccionPiso);
        seleccionMesa = findViewById(R.id.seleccionMesa);
        btnBuscarSitios = findViewById(R.id.btnBuscarSitios);

        bundle = getIntent().getExtras();

        usuario = (Usuario) bundle.getSerializable("usuario");

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/obtenerListaPlantas.php",

                response -> {

                    JSONArray jsonArray;

                    listaPlantas = new  ArrayList<>();

                    try {

                        jsonArray = new JSONArray(response);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Plantas planta = new Plantas(
                                    jsonObject.getInt("idPlanta"),
                                    jsonObject.getInt("numPlanta")
                            );

                            listaPlantas.add(planta);
                        }


                        ArrayAdapter<Plantas> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaPlantas);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        seleccionPiso.setAdapter(adapter);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                },
                error -> Toast.makeText(this, "No se han podido recuperar los datos", Toast.LENGTH_LONG).show()) {

        };

        requestQueue.add(stringRequest);


        seleccionPiso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Plantas plantaSeleccionada = listaPlantas.get(i);

                RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());

                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/obtenerListaMesasPorPlanta.php",

                        response -> {

                            JSONArray jsonArray;

                            listaMesas = new ArrayList<>();

                            try {

                                jsonArray = new JSONArray(response);

                                for (int j = 0; j < jsonArray.length(); j++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(j);

                                    Mesa mesa = new Mesa(
                                            jsonObject.getInt("idMesa"),
                                            jsonObject.getInt("numeroMesa"),
                                            plantaSeleccionada);

                                    listaMesas.add(mesa);

                                }

                                ArrayAdapter<Mesa> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, listaMesas);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                seleccionMesa.setAdapter(adapter);

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }



                        },
                        error -> Toast.makeText(view.getContext(), "Los datos han sido mal recuperados", Toast.LENGTH_LONG).show()) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("idPlanta", ""+plantaSeleccionada.getNumPlanta());
                        return params;
                    }
                };

                requestQueue.add(stringRequest);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnBuscarSitios.setOnClickListener(view -> {

            Plantas plantaSelec = listaPlantas.get(seleccionPiso.getSelectedItemPosition());
            Mesa mesaSelec = listaMesas.get(seleccionMesa.getSelectedItemPosition());
            Intent myIntent = new Intent(view.getContext(), ReservarAsientoVista.class);
            myIntent.putExtra("planta", plantaSelec);
            myIntent.putExtra("mesa", mesaSelec);
            myIntent.putExtra("usuario", usuario);
            startActivity(myIntent);
        });


    }
}