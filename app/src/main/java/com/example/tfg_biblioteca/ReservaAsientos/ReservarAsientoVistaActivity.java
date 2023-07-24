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
import com.example.tfg_biblioteca.ControladorUsuarioComun.PantallaPrincipalActivity;
import com.example.tfg_biblioteca.ControladorUsuarioComun.Utilidades;
import com.example.tfg_biblioteca.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReservarAsientoVistaActivity extends AppCompatActivity {

    private LinearLayout layoutIzq, layoutDrc;
    private TextView idMesa;
    private ImageButton btnSalir;
    private Bundle bundle;
    private Mesa mesa;
    private Usuario usuario;
    private ArrayList<Asiento> listaAsientos;
    private ArrayList<Integer> listaAsientosReservadosId;
    private String fechaReserva;

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

        dialogo.setTitle(R.string.reservaLibro_realizarReserva).
                setMessage(R.string.reservaAsiento_deseaReservar)
                .setPositiveButton(R.string.aceptar, (dialogInterface, i) -> {

                    realizarReservaDeAsiento(usuario, asiento);

                })
                .setNegativeButton(R.string.cancelar, (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });

        AlertDialog dialog = dialogo.create();
        dialog.show();

    }

    private void realizarReservaDeAsiento(Usuario usuario, Asiento asiento){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/asiento_realizar_reserva.php",

                response -> {

                    String respuesta = response.trim();

                    if(respuesta.equals("0")){
                        Toast.makeText(this, R.string.reservarAsiento_reservaYaRealizada, Toast.LENGTH_LONG).show();
                    }
                    else{

                        Toast.makeText(this, R.string.reservaAsiento_reservaRealizada, Toast.LENGTH_LONG).show();

                        Intent myIntent = new Intent(this, PantallaPrincipalActivity.class);
                        myIntent.putExtra("usuario", usuario);
                        startActivity(myIntent);

                    }


                },
                error -> Toast.makeText(this, R.string.errorGenerico, Toast.LENGTH_LONG).show()) {

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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/asientos_obtener_asientos_reservados_en_fecha.php",

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
                error -> Toast.makeText(this, R.string.errorGenerico, Toast.LENGTH_LONG).show()) {
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/asientos_obtener_sillas_por_mesa_y_planta.php",

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
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    1
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

                                mostrarAlertDialog(listaAsientos.get(clickedButtonCustomId));

                            });


                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> Toast.makeText(this, R.string.errorGenerico, Toast.LENGTH_LONG).show()) {
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