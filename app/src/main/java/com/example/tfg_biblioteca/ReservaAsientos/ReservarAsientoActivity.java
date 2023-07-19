package com.example.tfg_biblioteca.ReservaAsientos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_biblioteca.Adaptadores.AdaptadorMesa;
import com.example.tfg_biblioteca.Adaptadores.AdaptadorPlanta;
import com.example.tfg_biblioteca.Clases.Mesa;
import com.example.tfg_biblioteca.Clases.Planta;
import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.ControladorUsuarioComun.Utilidades;
import com.example.tfg_biblioteca.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ReservarAsientoActivity extends AppCompatActivity {

    private ArrayList<Planta> listaPlantas;
    private ArrayList<Mesa> listaMesas;
    private Spinner seleccionPiso, seleccionMesa;
    private Button btnBuscarSitios;
    private ImageButton btnSalir;
    private Usuario usuario;
    private ImageButton btnAbrirCalendario;
    private TextView fechaSeleccionada;
    private Planta plantaSeleccionada;
    private AdaptadorPlanta adapterPlanta;
    private AdaptadorMesa adapterMesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_asiento);

        seleccionPiso = findViewById(R.id.seleccionPiso);
        seleccionMesa = findViewById(R.id.seleccionMesa);
        btnBuscarSitios = findViewById(R.id.btnBuscarSitios);
        fechaSeleccionada = findViewById(R.id.fechaSeleccionada);
        btnAbrirCalendario = findViewById(R.id.btnAbrirCalendario);
        btnSalir = findViewById(R.id.btnSalir);

        usuario = Utilidades.getMyUtilidades().obtenerUsuario(this);

        obtenerListaPlantas();
        seleccionPiso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                plantaSeleccionada = listaPlantas.get(i);
                obtenerMesasPorPlanta(plantaSeleccionada);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fechaSeleccionada.setOnClickListener(view -> abrirCalendario());

        btnBuscarSitios.setOnClickListener(view -> {

            if(fechaSeleccionada.getText().toString().equals("")){
                Toast.makeText(this, R.string.reservarAsiento_fechaReserva, Toast.LENGTH_LONG).show();
            }
            else{
                tieneReservasEnEsaFecha();
            }
        });

        btnAbrirCalendario.setOnClickListener(view -> abrirCalendario());
        btnSalir.setOnClickListener(view -> Utilidades.getMyUtilidades().cerrarSesion(this));



    }


    private void obtenerListaPlantas(){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/obtenerListaPlantas.php",

                response -> {

                    JSONArray jsonArray;

                    listaPlantas = new  ArrayList<>();

                    try {

                        jsonArray = new JSONArray(response);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Planta planta = new Planta(
                                    jsonObject.getInt("idPlanta"),
                                    jsonObject.getInt("numPlanta")
                            );

                            listaPlantas.add(planta);
                        }

                        adapterPlanta = new AdaptadorPlanta(this, listaPlantas);
                        adapterPlanta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        seleccionPiso.setAdapter(adapterPlanta);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                },
                error -> Toast.makeText(this, R.string.errorGenerico, Toast.LENGTH_LONG).show()) {

        };

        requestQueue.add(stringRequest);

    }

    private void obtenerMesasPorPlanta(Planta plantaSeleccionada){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

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

                        adapterMesa = new AdaptadorMesa(this, listaMesas);
                        adapterMesa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        seleccionMesa.setAdapter(adapterMesa);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }



                },
                error -> Toast.makeText(this, R.string.errorGenerico, Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idPlanta", ""+plantaSeleccionada.getNumPlanta());
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    private void tieneReservasEnEsaFecha(){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/tieneReservasEnFecha.php",

                response -> {

                    String respuesta = response.trim();

                    if(respuesta.equals("0")){

                        Planta plantaSelec = listaPlantas.get(seleccionPiso.getSelectedItemPosition());
                        Mesa mesaSelec = listaMesas.get(seleccionMesa.getSelectedItemPosition());
                        Intent myIntent = new Intent(this, ReservarAsientoVistaActivity.class);
                        myIntent.putExtra("planta", plantaSelec);
                        myIntent.putExtra("mesa", mesaSelec);
                        myIntent.putExtra("usuario", usuario);
                        myIntent.putExtra("fechaReserva", fechaSeleccionada.getText().toString());
                        startActivity(myIntent);

                    }
                    else{
                        Toast.makeText(this, R.string.reservarAsiento_reservaYaRealizada, Toast.LENGTH_LONG).show();
                    }


                },
                error -> Toast.makeText(this, R.string.errorGenerico, Toast.LENGTH_LONG).show()) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("idUsuario", ""+usuario.getIdUsuario());
                params.put("fechaReserva", fechaSeleccionada.getText().toString());

                return params;
            }
        };

        requestQueue.add(stringRequest);


    }

    private void abrirCalendario(){

        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Calendar maxDate = Calendar.getInstance();
        maxDate.set(year, month, c.getActualMaximum(Calendar.DAY_OF_MONTH));

        DatePickerDialog datePickerDialog = new DatePickerDialog(ReservarAsientoActivity.this,(view1, year1, monthOfYear, dayOfMonth) -> {

            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(Calendar.YEAR, year);
            selectedDate.set(Calendar.MONTH, month);
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Verificar si la fecha seleccionada es un día de semana (no sábado ni domingo)
            int dayOfWeek = selectedDate.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String formattedDate = sdf.format(selectedDate.getTime());
                    fechaSeleccionada.setText(formattedDate);
            } else {

                Toast.makeText(this, R.string.reservarAsiento_biblioCerrada, Toast.LENGTH_LONG).show();

            }

        }, year, month, day);


        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());

        datePickerDialog.show();

    }

}