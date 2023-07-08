package com.example.tfg_biblioteca.ReservaAsientos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_biblioteca.Clases.Asiento;
import com.example.tfg_biblioteca.Clases.Mesa;
import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.PantallasApp.PantallaPrincipal;
import com.example.tfg_biblioteca.PantallasApp.Utilidades;
import com.example.tfg_biblioteca.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReservarAsientoVista extends AppCompatActivity {

    LinearLayout layoutIzq, layoutDrc;

    TextView idMesa;

    ImageButton btnSalir;

    Bundle bundle;

    private Mesa mesa;

    private Usuario usuario;

    private ArrayList<Asiento> listaAsientos;

    private ArrayList<Integer> listaAsientosReservadosId;

    String fechaReserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_asiento_vista);

        layoutIzq = findViewById(R.id.layoutIzq);
        layoutDrc = findViewById(R.id.layoutDrc);

        bundle = getIntent().getExtras();

        mesa = (Mesa) bundle.getSerializable("mesa");
        usuario = Utilidades.getMyUtilidades().obtenerUsuario(this);
        fechaReserva = (String) bundle.getSerializable("fechaReserva");
        btnSalir = findViewById(R.id.btnSalir);

        idMesa = findViewById(R.id.idMesa);
        idMesa.setText(""+mesa.getNumeroMesa());

        obtenerAsientosReservadosEnFecha();

        btnSalir.setOnClickListener(v -> Utilidades.getMyUtilidades().cerrarSesion(this));

    }


    private void mostrarAlertDialog(Asiento asiento){


        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        dialogo.setTitle("Realizar Reserva").
                setMessage("¿Deseas realizar la reserva de este asiento?")
                .setPositiveButton("Aceptar", (dialogInterface, i) -> {

                    realizarReservaDeAsiento(usuario, asiento);

                })
                .setNegativeButton("Cancelar", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });

        AlertDialog dialog = dialogo.create();
        dialog.show();

    }

    private void realizarReservaDeAsiento(Usuario usuario, Asiento asiento){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/realizarReservaAsiento.php",

                response -> {

                    String respuesta = response.trim();

                    if(respuesta.equals("0")){
                        Toast.makeText(this, "Ya tienes una reserva realizada para esta fecha", Toast.LENGTH_LONG).show();
                    }
                    else{

                        Toast.makeText(this, "Reserva del asiento realizada", Toast.LENGTH_LONG).show();

                        Intent myIntent = new Intent(this, PantallaPrincipal.class);
                        myIntent.putExtra("usuario", usuario);
                        startActivity(myIntent);

                    }


                },
                error -> Toast.makeText(this, "No se ha realizado la reserva del asiento", Toast.LENGTH_LONG).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("idUsuario", ""+usuario.getIdUsuario());
                params.put("idAsiento", ""+asiento.getIdAsiento());
                params.put("fechaReserva", fechaReserva);
                return params;
            }
        };

        requestQueue.add(stringRequest);


    }


    private void obtenerAsientosReservadosEnFecha(){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/obtenerAsientosReservadosEnFecha.php",

                response -> {

                    JSONArray jsonArray;

                    listaAsientosReservadosId = new ArrayList<>();

                    try {

                        jsonArray = new JSONArray(response);

                        for (int j = 0; j < jsonArray.length(); j++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(j);

                            listaAsientosReservadosId.add(jsonObject.getInt("idAsiento"));

                        }

                        inicializarAsientosDeUnaMesa();


                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> Toast.makeText(this, "Los datos han sido mal recuperados", Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("fechaReserva", fechaReserva);
                return params;

            }
        };

        requestQueue.add(stringRequest);

    }

    private void inicializarAsientosDeUnaMesa(){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/obtenerListaSillasPorMesaYPlanta.php",

                response -> {

                    JSONArray jsonArray;

                    listaAsientos = new ArrayList<>();

                    try {

                        jsonArray = new JSONArray(response);

                        for (int j = 0; j < jsonArray.length(); j++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(j);

                            Asiento asiento = new Asiento(
                                    jsonObject.getInt("idAsiento"),
                                    jsonObject.getInt("numAsiento"),
                                    mesa
                            );

                            listaAsientos.add(asiento);

                        }

                        for(int i = 0; i < listaAsientos.size(); i++){

                            Asiento asientoAct = listaAsientos.get(i);
                            Button boton = new Button(this);
                            boton.setText(""+listaAsientos.get(i).getNumAsiento());
                            boton.setTextSize(18);
                            boton.setId(i);

                            boton.setBackgroundColor(getColor(R.color.verde));

                            if(listaAsientosReservadosId.contains(asientoAct.getIdAsiento())){
                                boton.setBackgroundColor(getColor(R.color.rojoLibro));
                                boton.setEnabled(false);
                            }




                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );

                            layoutParams.setMargins(0, 100, 0, 100);
                            boton.setLayoutParams(layoutParams);

                            if(i >= listaAsientos.size()/2){
                                layoutDrc.addView(boton);
                            }
                            else{
                                layoutIzq.addView(boton);
                            }

                            boton.setOnClickListener(v -> {

                                Button clickedButton = (Button) v;

                                int clickedButtonCustomId = clickedButton.getId();

                                switch (clickedButtonCustomId) {

                                    case 0:
                                        mostrarAlertDialog(listaAsientos.get(clickedButtonCustomId));
                                        break;
                                    case 1:
                                        mostrarAlertDialog(listaAsientos.get(clickedButtonCustomId));
                                        break;
                                    case 2:
                                        mostrarAlertDialog(listaAsientos.get(clickedButtonCustomId));
                                        break;
                                    case 3:
                                        mostrarAlertDialog(listaAsientos.get(clickedButtonCustomId));
                                        break;
                                    case 4:
                                        mostrarAlertDialog(listaAsientos.get(clickedButtonCustomId));
                                        break;
                                    case 5:
                                        mostrarAlertDialog(listaAsientos.get(clickedButtonCustomId));
                                        break;
                                    case 6:
                                        mostrarAlertDialog(listaAsientos.get(clickedButtonCustomId));
                                        break;
                                    case 7:
                                        mostrarAlertDialog(listaAsientos.get(clickedButtonCustomId));
                                        break;
                                    case 8:
                                        mostrarAlertDialog(listaAsientos.get(clickedButtonCustomId));
                                        break;
                                    case 9:
                                        mostrarAlertDialog(listaAsientos.get(clickedButtonCustomId));
                                        break;
                                    case 10:
                                        mostrarAlertDialog(listaAsientos.get(clickedButtonCustomId));
                                        break;
                                }
                            });


                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> Toast.makeText(this, "Los datos han sido mal recuperados", Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idMesa", ""+mesa.getIdMesa());
                return params;
            }
        };

        requestQueue.add(stringRequest);


    }

}