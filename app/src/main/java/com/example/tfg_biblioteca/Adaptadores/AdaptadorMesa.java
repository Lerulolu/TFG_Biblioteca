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

import com.example.tfg_biblioteca.Clases.Mesa;
import com.example.tfg_biblioteca.Clases.Planta;
import com.example.tfg_biblioteca.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorMesa extends ArrayAdapter<Mesa> implements Filterable {

    private List<Mesa> listaMesas;

    private Mesa mesa;

    public AdaptadorMesa(Context context, List<Mesa> mesas) {
        super(context, 0, mesas);
        listaMesas = new ArrayList<>(mesas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.mesa, parent, false);
        }

        mesa = getItem(position);

        TextView plantaSelec = convertView.findViewById(R.id.mesa_fila);

        plantaSelec.setText(String.valueOf(mesa.getNumeroMesa()));

        return convertView;
    }


    @Override
    public int getCount() {
        return listaMesas.size();
    }

    @Nullable
    @Override
    public Mesa getItem(int position) {
        return listaMesas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.mesa, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.mesa_fila);

        Mesa mesa = getItem(position);

        if (mesa != null) {
            textView.setText(String.valueOf(mesa.getNumeroMesa()));
        }

        return convertView;
    }

}
