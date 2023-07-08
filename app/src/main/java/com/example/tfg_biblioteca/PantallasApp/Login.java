package com.example.tfg_biblioteca.PantallasApp;

import static com.example.tfg_biblioteca.PantallasApp.Ajustes.cambiarIdioma;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText contrasenaUsuario, ldapUsuario;
    Button btnLogin, olvidoContrasena;
    ImageButton btnAjustesLogin, btnInformacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = getSharedPreferences("idioma", Context.MODE_PRIVATE);

        String idioma = preferences.getString("idioma", "");

        if (!idioma.isEmpty()) {
            if(idioma.equals("eu")){
                cambiarIdioma("eu", getResources(),this);
            }
            else{
                cambiarIdioma("es", getResources(),this);
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.contrasenaUsuario = findViewById(R.id.contrasenaUsuario);
        this.ldapUsuario = findViewById(R.id.ldapUsuario);

        btnLogin = findViewById(R.id.btnLogin);
        btnAjustesLogin = findViewById(R.id.btnAjustesLogin);
        btnInformacion = findViewById(R.id.btnInformacion);

        olvidoContrasena = findViewById(R.id.olvidoContrasena);

        olvidoContrasena.setOnClickListener(v -> {
            String forgotPasswordUrl = "https://www.ehu.eus/bilatu/buscar/bilatu.php?lang=es";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(forgotPasswordUrl));
            startActivity(intent);
        });

        btnInformacion.setOnClickListener(view -> {

            Intent myIntent = new Intent(view.getContext(), Informacion.class);
            startActivity(myIntent);

        });

        btnAjustesLogin.setOnClickListener(view -> {
            Intent myIntent = new Intent(view.getContext(), Ajustes.class);
            startActivity(myIntent);
        });


        btnLogin.setOnClickListener(v -> {

            try {
                loginUsuario("http://192.168.0.37:80/proyecto_tfg/login.php");

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }

        });

    }

    private void loginUsuario(String URL) throws IllegalAccessException, InstantiationException {

        if(contrasenaUsuario.getText().toString().equals("") || ldapUsuario.getText().toString().equals("")){

            Toast.makeText(Login.this, "Por favor introduce los datos del Log in", Toast.LENGTH_LONG).show();

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

                                usuarioASharedPreferences(usuario);

                                Intent myIntent = new Intent(this, PantallaPrincipal.class);
                                myIntent.putExtra("usuario", usuario);
                                startActivity(myIntent);

                            }
                            else{
                                Toast.makeText(this, "Ha habido un error en el Log in", Toast.LENGTH_LONG).show();
                            }


                        } catch (JSONException e) {
                            Toast.makeText(this, "El usuario o la contraseña es erróneo", Toast.LENGTH_LONG).show();
                        }

                    },
                    error -> Toast.makeText(this, "Ha habido un error en el Login", Toast.LENGTH_LONG).show()) {
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

    public void onBackPressed(){

        finishAffinity();

    }

    private void usuarioASharedPreferences(Usuario usuario){

        SharedPreferences sharedPreferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(usuario);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String objetoSerializado = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

        editor.putString("usuario", objetoSerializado);
        editor.apply();
    }

}