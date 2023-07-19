package com.example.tfg_biblioteca.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tfg_biblioteca.Clases.Libro;
import com.example.tfg_biblioteca.Clases.Usuario;
import com.example.tfg_biblioteca.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorUsuario extends ArrayAdapter<Usuario> implements Filterable {

    private List<Usuario> listaAlumnos;
    private List<Usuario> listaAlumnosFiltrados;
    private AdaptadorUsuario.UsuarioFilter filtro;
    private Usuario usuario;

    public AdaptadorUsuario(Context context, List<Usuario> usuarios) {
        super(context, 0, usuarios);
        listaAlumnos = new ArrayList<>(usuarios);
        listaAlumnosFiltrados = new ArrayList<>(usuarios);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lista_alumnos, parent, false);
        }

        usuario = getItem(position);

        TextView nombreAlumno_fila = convertView.findViewById(R.id.nombreAlumno_fila);
        TextView LDAPAlumno_fila = convertView.findViewById(R.id.LDAPAlumno_fila);

        nombreAlumno_fila.setText(usuario.getNombreUsuario());
        LDAPAlumno_fila.setText(String.valueOf(usuario.getLdapUsuario()));

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (filtro == null) {
            filtro = new AdaptadorUsuario.UsuarioFilter();
        }
        return filtro;
    }

    private class UsuarioFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                results.values = listaAlumnos;
                results.count = listaAlumnos.size();
            } else {
                List<Usuario> filtrados = new ArrayList<>();

                String filtroPattern = constraint.toString().toLowerCase().trim();


                for (Usuario user : listaAlumnos) {

                    String LDAP = String.valueOf(user.getLdapUsuario());
                    if (user.getNombreUsuario().toLowerCase().contains(filtroPattern) ||
                            LDAP.toLowerCase().contains(filtroPattern)) {
                        filtrados.add(user);
                    }
                }

                results.values = filtrados;
                results.count = filtrados.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listaAlumnosFiltrados = (List<Usuario>) results.values;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return listaAlumnosFiltrados.size();
    }

    @Nullable
    @Override
    public Usuario getItem(int position) {
        return listaAlumnosFiltrados.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
