package com.example.tfg_biblioteca.Adaptadores;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfg_biblioteca.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tfg_biblioteca.Clases.Libro;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdaptadorLibro extends ArrayAdapter<Libro> implements Filterable {

    private List<Libro> listaLibros;
    private List<Libro> listaLibrosFiltrados;
    private LibroFilter filtro;
    private Libro libro;

    public AdaptadorLibro(Context context, List<Libro> libros) {
        super(context, 0, libros);
        listaLibros = new ArrayList<>(libros);
        listaLibrosFiltrados = new ArrayList<>(libros);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lista_libros, parent, false);
        }

        libro = getItem(position);

        TextView autorLibro = convertView.findViewById(R.id.autorLibro_fila);
        TextView ISBN = convertView.findViewById(R.id.ISBN_fila);
        TextView nombreLibro = convertView.findViewById(R.id.titulo_fila);

        autorLibro.setText(libro.getAutorLibro());
        ISBN.setText(libro.getISBN());
        nombreLibro.setText(libro.getNombreLibro());

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (filtro == null) {
            filtro = new LibroFilter();
        }
        return filtro;
    }

    private class LibroFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                results.values = listaLibros;
                results.count = listaLibros.size();
            } else {
                List<Libro> filtrados = new ArrayList<>();

                String filtroPattern = constraint.toString().toLowerCase().trim();

                for (Libro libro : listaLibros) {
                    if (libro.getNombreLibro().toLowerCase().contains(filtroPattern) ||
                            libro.getAutorLibro().toLowerCase().contains(filtroPattern) ||
                            libro.getISBN().toLowerCase().contains(filtroPattern)) {
                        filtrados.add(libro);
                    }
                }

                results.values = filtrados;
                results.count = filtrados.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listaLibrosFiltrados = (List<Libro>) results.values;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return listaLibrosFiltrados.size();
    }

    @Nullable
    @Override
    public Libro getItem(int position) {
        return listaLibrosFiltrados.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
