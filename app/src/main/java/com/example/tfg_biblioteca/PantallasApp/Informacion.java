package com.example.tfg_biblioteca.PantallasApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg_biblioteca.R;

public class Informacion extends AppCompatActivity {

    TextView telefono, mailUniversidad, ubicacionUniversidad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        telefono = findViewById(R.id.telefonoUni);
        mailUniversidad = findViewById(R.id.mailUniversidad);
        ubicacionUniversidad = findViewById(R.id.ubicacionUniversidad);

        telefono.setText("945 22 91 55"); // Establece el número de teléfono
        mailUniversidad.setText("ehualumni@ehu.eus");
        ubicacionUniversidad.setText(R.string.direccion);

        Linkify.addLinks(telefono, Linkify.PHONE_NUMBERS);
        Linkify.addLinks(mailUniversidad, Linkify.EMAIL_ADDRESSES);
        Linkify.addLinks(ubicacionUniversidad, Linkify.MAP_ADDRESSES);

        telefono.setOnClickListener(v -> {
            String numeroTelefono = telefono.getText().toString();
            Uri uri = Uri.parse("tel:" + numeroTelefono);

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(uri);
            startActivity(intent);
        });

        mailUniversidad.setOnClickListener(v -> {

            String direccionCorreo = mailUniversidad.getText().toString();
            Uri uri = Uri.parse("mailto:" + direccionCorreo);

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(uri);
            startActivity(Intent.createChooser(intent, "Enviar correo"));

        });

        ubicacionUniversidad.setOnClickListener(v -> {

            String ubicacion = ubicacionUniversidad.getText().toString();
            Uri uri = Uri.parse("geo:0,0?q=" + Uri.encode(ubicacion));

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this,R.string.informacion_noEncuentraMAPS,Toast.LENGTH_LONG);
            }
        });
    }
}