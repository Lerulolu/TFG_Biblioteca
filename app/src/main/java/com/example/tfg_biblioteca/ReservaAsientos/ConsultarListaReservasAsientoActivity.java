package com.example.tfg_biblioteca.ReservaAsientos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_biblioteca.Adaptadores.AdaptadorReservaAsiento;
import com.example.tfg_biblioteca.Clases.Asiento;
import com.example.tfg_biblioteca.Clases.Mesa;
import com.example.tfg_biblioteca.Clases.Planta;
import com.example.tfg_biblioteca.Clases.ReservaAsiento;
import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.ControladorUsuarioComun.Utilidades;
import com.example.tfg_biblioteca.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConsultarListaReservasAsientoActivity extends AppCompatActivity {

    private ImageButton btnSalir;

    private ListView layoutListaConsultaReservasAsientos;

    private ArrayList<ReservaAsiento> listaReservaAsientos;

    private AdaptadorReservaAsiento adapter;

    private Usuario usuario;

    private Planta planta;

    private Mesa mesa;

    private Asiento asiento;

    private ReservaAsiento reservaAsiento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_lista_reservas_asiento);

        usuario = Utilidades.getMyUtilidades().obtenerUsuario(this);

        layoutListaConsultaReservasAsientos = findViewById(R.id.layoutListaConsultaReservasAsientos);

        btnSalir = findViewById(R.id.btnSalir);

        cargarListaReservaAsientos(usuario);

        btnSalir.setOnClickListener(view ->  Utilidades.getMyUtilidades().cerrarSesion(this));


    }

    private void cargarListaReservaAsientos(Usuario usuario){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/obtenerListaReservasAsientos.php",

                response -> {

                    JSONArray jsonArray;

                    listaReservaAsientos = new ArrayList<>();

                    try {

                        jsonArray = new JSONArray(response);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            planta = new Planta(
                                jsonObject.getInt("idPlanta"),
                                jsonObject.getInt("numPlanta")
                            );

                            mesa = new Mesa(
                                jsonObject.getInt("idMesa"),
                                jsonObject.getInt("numeroMesa"),
                                planta
                            );

                            asiento = new Asiento(
                                    jsonObject.getInt("idAsiento"),
                                    jsonObject.getInt("numAsiento"),
                                    mesa
                            );

                            reservaAsiento = new ReservaAsiento(
                                    jsonObject.getInt("idReservaAsiento"),
                                    asiento, jsonObject.getString("fechaReserva"),
                                    new Usuario(usuario.getIdUsuario())
                            );

                            listaReservaAsientos.add(reservaAsiento);

                        }

                        adapter = new AdaptadorReservaAsiento(this, listaReservaAsientos);
                        layoutListaConsultaReservasAsientos.setAdapter(adapter);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                },
                error -> Toast.makeText(this, R.string.consultarReservaAsiento_errorRecuperReservas, Toast.LENGTH_LONG).show()) {
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