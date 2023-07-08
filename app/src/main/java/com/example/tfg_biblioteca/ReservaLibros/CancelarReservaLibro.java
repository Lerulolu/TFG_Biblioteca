package com.example.tfg_biblioteca.ReservaLibros;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_biblioteca.Clases.ReservaLibro;
import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.PantallasApp.Login;
import com.example.tfg_biblioteca.PantallasApp.PantallaPrincipal;
import com.example.tfg_biblioteca.PantallasApp.Utilidades;
import com.example.tfg_biblioteca.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CancelarReservaLibro extends AppCompatActivity {

    Usuario usuario;
    EditText codigoCancelacion;
    Button btnAceptar;
    ImageButton btnSalir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelar_reserva_libro);

        usuario = Utilidades.getMyUtilidades().obtenerUsuario(this);

        codigoCancelacion = findViewById(R.id.codigoCancelacion);

        btnAceptar = findViewById(R.id.btnAceptar);
        btnSalir = findViewById(R.id.btnSalir);

        btnAceptar.setOnClickListener(view -> cancelarReservaLibro(codigoCancelacion.getText().toString()));

        btnSalir.setOnClickListener(view -> Utilidades.getMyUtilidades().cerrarSesion(this));

    }


    private void cancelarReservaLibro(String idCancelacion){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/cancelarReservaLibro.php",


                response -> {

                    String respuesta = response.trim();

                    if (respuesta.equals("0")) {

                        Toast.makeText(this, R.string.cancelarReservaLibro_idEncontrado, Toast.LENGTH_LONG).show();
                        Intent myIntent = new Intent(this, PantallaPrincipal.class);
                        myIntent.putExtra("usuario", usuario);
                        startActivity(myIntent);

                    } else if(respuesta.equals("1")){

                        codigoCancelacion.setError(String.valueOf(getResources().getString(R.string.cancelarReservaLibro_idNoEncontrado)));
                        Handler handler = new Handler();
                        handler.postDelayed(() -> codigoCancelacion.setError(null), 4000);

                    }
                    else if(respuesta.equals("2")){
                        codigoCancelacion.setError(String.valueOf(getResources().getString(R.string.cancelarReservaLibro_estadoCompletado)));
                        Handler handler = new Handler();
                        handler.postDelayed(() -> codigoCancelacion.setError(null), 3000);
                    }


                },
                error -> Toast.makeText(this, "Ha habido un error al cancelar la reserva", Toast.LENGTH_LONG).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idReservaLibro", idCancelacion);
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }
}