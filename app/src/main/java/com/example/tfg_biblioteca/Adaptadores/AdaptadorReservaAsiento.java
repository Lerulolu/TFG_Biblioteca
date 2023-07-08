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

import com.example.tfg_biblioteca.Clases.ReservaAsiento;
import com.example.tfg_biblioteca.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorReservaAsiento extends ArrayAdapter<ReservaAsiento> implements Filterable {

    private List<ReservaAsiento> listaReservasAsientos;

    public AdaptadorReservaAsiento(Context context, List<ReservaAsiento> reservaAsientos) {
        super(context, 0, reservaAsientos);
        listaReservasAsientos = new ArrayList<>(reservaAsientos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lista_reserva_asiento, parent, false);
        }

        ReservaAsiento reservaAsiento = getItem(position);

        TextView idReserva_fila = convertView.findViewById(R.id.idReserva_fila);
        TextView fechaReserva_fila = convertView.findViewById(R.id.fechaReserva_fila);
        TextView planta_fila = convertView.findViewById(R.id.planta_fila);
        TextView mesa_fila = convertView.findViewById(R.id.mesa_fila);
        TextView asiento_fila = convertView.findViewById(R.id.asiento_fila);

        idReserva_fila.setText(String.valueOf(reservaAsiento.getIdReservaAsiento()));
        fechaReserva_fila.setText(String.valueOf(reservaAsiento.getFechaReserva()));
        planta_fila.setText(String.valueOf(reservaAsiento.getAsiento().getMesa().getPlanta().getNumPlanta()));
        mesa_fila.setText(String.valueOf(reservaAsiento.getAsiento().getMesa().getNumeroMesa()));
        asiento_fila.setText(String.valueOf(reservaAsiento.getAsiento().getNumAsiento()));

        return convertView;
    }
  

    @Nullable
    @Override
    public ReservaAsiento getItem(int position) {
        return listaReservasAsientos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
