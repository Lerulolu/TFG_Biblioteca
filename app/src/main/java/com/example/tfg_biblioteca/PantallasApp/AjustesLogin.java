package com.example.tfg_biblioteca.PantallasApp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tfg_biblioteca.R;

import java.util.Locale;

public class AjustesLogin extends AppCompatActivity {

    Button valorarAPP, cambiarContrasena, btnGuardarIdioma;

    Spinner spinnerIdiomas;

    ImageButton btnSalir;

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
        setContentView(R.layout.activity_ajustes_login);

        valorarAPP = findViewById(R.id.valorarAPP);
        cambiarContrasena = findViewById(R.id.cambiarContrasena);
        btnSalir = findViewById(R.id.btnSalir);
        spinnerIdiomas = findViewById(R.id.spinnerIdiomas);
        btnGuardarIdioma = findViewById(R.id.guardarIdioma);

        final String[] idiomas;

        if(idioma.equals("eu")){
            idiomas = getResources().getStringArray(R.array.idiomasEu);
        }
        else{
            idiomas = getResources().getStringArray(R.array.idiomasEs);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, idiomas);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerIdiomas.setAdapter(adapter);


        btnGuardarIdioma.setOnClickListener(view -> {

            if(spinnerIdiomas.getSelectedItemPosition() == 0){
                cambiarIdioma("es", getResources(), view.getContext());
            }
            else{
                cambiarIdioma("eu", getResources(), view.getContext());
            }

            Intent myIntent = new Intent(view.getContext(), PantallaPrincipal.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(myIntent);

        });

        btnSalir.setOnClickListener(view -> Utilidades.getMyUtilidades().cerrarSesion(this));

        cambiarContrasena.setOnClickListener(view -> {
            String forgotPasswordUrl = "https://www.ehu.eus/bilatu/pass/pass.php?lang=es";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(forgotPasswordUrl));
            startActivity(intent);
        });

        valorarAPP.setOnClickListener(view -> valorarApp());
    }

    private void valorarApp(){

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://play.google.com"));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.ajustes_appNoInstalada, Toast.LENGTH_LONG).show();
        }

    }

    public static void cambiarIdioma(String codigoIdioma, Resources resources, Context context) {

        Locale locale = new Locale(codigoIdioma);
        Locale.setDefault(locale);

        Configuration config = resources.getConfiguration();
        config.locale = locale;
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        SharedPreferences.Editor editor = context.getSharedPreferences("idioma", Context.MODE_PRIVATE).edit();
        editor.putString("idioma", codigoIdioma);
        editor.apply();

    }
}