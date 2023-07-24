package com.example.tfg_biblioteca.ReservaLibros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.ControladorUsuarioComun.PantallaPrincipalActivity;
import com.example.tfg_biblioteca.ControladorUsuarioComun.Utilidades;
import com.example.tfg_biblioteca.R;

import java.util.HashMap;
import java.util.Map;

public class CancelarReservaLibroActivity extends AppCompatActivity {

    private Usuario usuario;
    private EditText codigoCancelacion;
    private Button btnAceptar;
    private ImageButton btnSalir;


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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/libro_cancelar_reserva.php",


                response -> {

                    String respuesta = response.trim();

                    if (respuesta.equals("0")) {

                        Toast.makeText(this, R.string.cancelarReservaLibro_idEncontrado, Toast.LENGTH_LONG).show();
                        Intent myIntent = new Intent(this, PantallaPrincipalActivity.class);
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
                error -> Toast.makeText(this, R.string.errorGenerico, Toast.LENGTH_LONG).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idReservaLibro", idCancelacion);
                params.put("idUsuario", String.valueOf(usuario.getIdUsuario()));
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }
}