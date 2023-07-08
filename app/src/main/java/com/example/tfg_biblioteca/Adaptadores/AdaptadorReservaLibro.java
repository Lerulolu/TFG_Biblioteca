package com.example.tfg_biblioteca.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tfg_biblioteca.Clases.ReservaLibro;
import com.example.tfg_biblioteca.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorReservaLibro extends ArrayAdapter<ReservaLibro> implements Filterable {

    private List<ReservaLibro> listaReservasLibros;

    public AdaptadorReservaLibro(Context context, List<ReservaLibro> reservaLibros) {
        super(context, 0, reservaLibros);
        listaReservasLibros = new ArrayList<>(reservaLibros);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lista_reserva_libro, parent, false);
        }

        ReservaLibro reservaLibro = getItem(position);

        TextView idReserva_fila = convertView.findViewById(R.id.idReserva_fila);
        TextView fechaReserva_fila = convertView.findViewById(R.id.fechaReserva_fila);
        TextView autor_fila = convertView.findViewById(R.id.autor_fila);
        TextView ISBN_fila = convertView.findViewById(R.id.ISBN_fila);
        TextView nombreLibro_fila = convertView.findViewById(R.id.nombreLibro_fila);

        idReserva_fila.setText(String.valueOf(reservaLibro.getIdReservaLibro()));
        fechaReserva_fila.setText(String.valueOf(reservaLibro.getFechaReserva()));
        autor_fila.setText(String.valueOf(reservaLibro.getLibro().getAutorLibro()));
        ISBN_fila.setText(String.valueOf(reservaLibro.getLibro().getISBN()));
        nombreLibro_fila.setText(String.valueOf(reservaLibro.getLibro().getNombreLibro()));

        return convertView;
    }
  

    @Nullable
    @Override
    public ReservaLibro getItem(int position) {
        return listaReservasLibros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
