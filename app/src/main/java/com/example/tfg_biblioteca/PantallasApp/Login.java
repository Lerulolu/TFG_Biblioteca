package com.example.tfg_biblioteca.PantallasApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText contrasenaUsuario, ldapUsuario;
    Button btnAgregar;
    ImageButton btnAjustes;

    private static final String TAG = Login.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.contrasenaUsuario = findViewById(R.id.contrasenaUsuario);
        this.ldapUsuario = findViewById(R.id.ldapUsuario);

        btnAgregar = findViewById(R.id.btnLogin);
        btnAjustes = findViewById(R.id.btnAjustes);

        btnAgregar.setOnClickListener(v -> {

            try {
                loginUsuario("http://192.168.0.37:80/proyecto_tfg/login.php");

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }

        });

        btnAjustes.setOnClickListener(v -> {
            Intent myIntent = new Intent(v.getContext(), Informacion.class);
            startActivity(myIntent);
        });
    }

    private void loginUsuario(String URL) throws IllegalAccessException, InstantiationException {

        if(contrasenaUsuario.getText().toString().equals("") || ldapUsuario.getText().toString().equals("")){

            Toast.makeText(Login.this, "Por favor introduce los datos del loggin", Toast.LENGTH_LONG).show();

        }
        else{

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                    response -> {

                        JSONArray jsonArray;

                        try {

                            jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);

                            Usuario usuario = new Usuario(
                                    jsonObject.getInt("idUsuario"),
                                    jsonObject.getInt("ldapUsuario"),
                                    jsonObject.getString("nombreUsuario"),
                                    jsonObject.getString("contrasenaUsuario"));

                            if (usuario != null) {
                                Intent myIntent = new Intent(this, PantallaPrincipal.class);
                                myIntent.putExtra("usuario", usuario);
                                startActivity(myIntent);
                            }
                            else{
                                Toast.makeText(this, "Ha habido un error en el Login", Toast.LENGTH_LONG).show();
                            }


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    },
                    error -> Toast.makeText(this, "Loggin mal", Toast.LENGTH_LONG).show()) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("ldapUsuario", ldapUsuario.getText().toString());
                    params.put("contrasenaUsuario", contrasenaUsuario.getText().toString());
                    return params;
                }
            };

            requestQueue.add(stringRequest);

        }
    }

}