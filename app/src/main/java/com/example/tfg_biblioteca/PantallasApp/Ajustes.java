package com.example.tfg_biblioteca.PantallasApp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.R;

import java.util.Locale;

public class Ajustes extends AppCompatActivity {

    Button valorarAPP;
    Button btnGuardarIdioma;
    Spinner spinnerIdiomas;


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
        setContentView(R.layout.activity_ajustes);

        valorarAPP = findViewById(R.id.valorarAPP);
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

        spinnerIdiomas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            private boolean isFirstSelection = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (isFirstSelection) {
                    isFirstSelection = false;
                    return;
                }

                String idiomaSeleccionado = idiomas[position];

                if(idiomaSeleccionado.equals("Euskera")){
                    cambiarIdioma("eu", getResources(), view.getContext());
                }
                else{
                    cambiarIdioma("es", getResources(), view.getContext());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Maneja el caso cuando no se ha seleccionado ningún idioma
            }
        });

        valorarAPP.setOnClickListener(view -> valorarApp());

        btnGuardarIdioma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(spinnerIdiomas.getSelectedItemPosition() == 0){
                    cambiarIdioma("es", getResources(), view.getContext());
                }
                else{
                    cambiarIdioma("eu", getResources(), view.getContext());
                }

                Intent myIntent = new Intent(view.getContext(), Login.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);

            }
        });
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

        // Guardar el idioma seleccionado en las preferencias compartidas para que persista entre sesiones
        SharedPreferences.Editor editor = context.getSharedPreferences("idioma", Context.MODE_PRIVATE).edit();
        editor.putString("idioma", codigoIdioma);
        editor.apply();

    }

}