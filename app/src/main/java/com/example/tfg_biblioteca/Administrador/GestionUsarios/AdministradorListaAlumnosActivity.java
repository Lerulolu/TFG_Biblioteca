package com.example.tfg_biblioteca.Administrador.GestionUsarios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_biblioteca.Adaptadores.AdaptadorUsuario;
import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.ControladorUsuarioComun.Utilidades;
import com.example.tfg_biblioteca.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdministradorListaAlumnosActivity extends AppCompatActivity {

    private ArrayList<Usuario> listaUsuarios;
    private ImageButton btnSalir;
    private ListView listViewUsuarios;
    private AdaptadorUsuario adapter;
    private SearchView busquedaAlumno;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador_lista_alumnos);

        this.listViewUsuarios = findViewById(R.id.layoutListaAlumnos);

        this.btnSalir = findViewById(R.id.btnSalir);

        this.busquedaAlumno = findViewById(R.id.busquedaAlumno);

        cargarListaAlumnos();

        listViewUsuarios.setOnItemClickListener((adapterView, view, i, l) -> {

            usuario = listaUsuarios.get(i);
            Intent myIntent = new Intent(view.getContext(), AdministradorEditarUsuarioActivity.class);
            myIntent.putExtra("alumno",usuario);
            startActivity(myIntent);

        });

        btnSalir.setOnClickListener(v -> Utilidades.getMyUtilidades().cerrarSesion(this));
    }

    private void cargarListaAlumnos() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.37:80/proyecto_tfg/administrador_obtenerListaAlumnos.php",

                response -> {

                    JSONArray jsonArray;

                    listaUsuarios = new ArrayList<>();

                    try {

                        jsonArray = new JSONArray(response);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Usuario usuario = new Usuario(
                                    jsonObject.getInt("idUsuario"),
                                    jsonObject.getInt("ldapUsuario"),
                                    jsonObject.getString("nombreUsuario"),
                                    jsonObject.getString("contrasenaUsuario"),
                                    jsonObject.getInt("tipoUsuario"));

                            listaUsuarios.add(usuario);

                        }

                        adapter = new AdaptadorUsuario(this, listaUsuarios);
                        listViewUsuarios.setAdapter(adapter);

                        busquedaAlumno.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String s) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String s) {
                                adapter.getFilter().filter(s);
                                return true;
                            }
                        });

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                },
                error -> {
                    Toast.makeText(this, "No se han podido recuperar los objetos", Toast.LENGTH_LONG).show();
                });

        requestQueue.add(stringRequest);
    }
}