package com.example.tfg_biblioteca.ReservaAsientos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.example.tfg_biblioteca.Clases.Plantas;
import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.PantallasApp.PantallaPrincipal;
import com.example.tfg_biblioteca.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ReservarAsientoVista extends AppCompatActivity {

    LinearLayout layoutIzq, layoutDrc;

    TextView idMesa;

    Bundle bundle;

    private Plantas planta;

    private Mesa mesa;

    private Usuario usuario;

    private ArrayList<Asiento> listaAsientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_asiento_vista);

        layoutIzq = findViewById(R.id.layoutIzq);
        layoutDrc = findViewById(R.id.layoutDrc);

        bundle = getIntent().getExtras();

        planta = (Plantas) bundle.getSerializable("planta");
        mesa = (Mesa) bundle.getSerializable("mesa");
        usuario = (Usuario) bundle.getSerializable("usuario");

        idMesa = findViewById(R.id.idMesa);
        idMesa.setText(""+mesa.getNumeroMesa());



        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/obtenerListaSillasPorMesaYPlanta.php",

                response -> {

                    JSONArray jsonArray;

                    listaAsientos = new ArrayList<>();

                    try {

                        jsonArray = new JSONArray(response);

                        for (int j = 0; j < jsonArray.length(); j++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(j);

                            int ocupado = jsonObject.getInt("ocupado");
                            boolean estaOcupado;

                            if(ocupado == 0){
                                estaOcupado = false;
                            }
                            else{
                                estaOcupado = true;
                            }

                            Asiento asiento = new Asiento(
                                jsonObject.getInt("idAsiento"),
                                mesa, estaOcupado,
                                jsonObject.getInt("numAsiento")
                            );

                            listaAsientos.add(asiento);

                        }

                        for(int i = 0; i < listaAsientos.size(); i++){

                            Button boton = new Button(this);
                            boton.setText(""+listaAsientos.get(i).getNumAsiento());
                            boton.setTextSize(14);
                            boton.setTag(listaAsientos.get(i));

                            int buttonId = View.generateViewId();
                            boton.setId(buttonId);

                            if(!listaAsientos.get(i).isEstaOcupdo()){
                                boton.setBackgroundColor(getColor(R.color.verde));
                            }
                            else{
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

                            boton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Accede al botón pulsado a través de la referencia "v"
                                    Button clickedButton = (Button) v;

                                    // Obtiene el ID personalizado del botón pulsado
                                    int clickedButtonCustomId = clickedButton.getId();

                                    // Realiza acciones en función del botón pulsado
                                    switch (clickedButtonCustomId) {

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

    private void mostrarAlertDialog(Asiento asiento){


        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        dialogo.setTitle("Realizar Reserva").
                setMessage("¿Deseas realizar la reserva de este asiento?")
                .setPositiveButton("Aceptar", (dialogInterface, i) -> {

                    realizarReservaDeAsiento(usuario, planta, mesa, asiento);

                })
                .setNegativeButton("Cancelar", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });

        AlertDialog dialog = dialogo.create();
        dialog.show();

    }

    private void realizarReservaDeAsiento(Usuario usuario, Plantas planta, Mesa mesa, Asiento asiento){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/realizarReservaAsiento.php",

                response -> {
                    Toast.makeText(this, "Reserva del libro realizada", Toast.LENGTH_LONG).show();
                },
                error -> Toast.makeText(this, "No se ha realizado la reserva del libro", Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idUsuario", ""+usuario.getIdUsuario());
                params.put("idPlanta", ""+planta.getIdPlanta());
                params.put("idMesa", ""+mesa.getIdMesa());
                params.put("idAsiento", ""+asiento.getIdAsiento());
                return params;
            }
        };

        requestQueue.add(stringRequest);


    }

}