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
import com.example.tfg_biblioteca.Clases.Planta;
import com.example.tfg_biblioteca.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorPlanta extends ArrayAdapter<Planta> implements Filterable {

    private List<Planta> listaPlantas;

    private Planta planta;

    public AdaptadorPlanta(Context context, List<Planta> plantas) {
        super(context, 0, plantas);
        listaPlantas = new ArrayList<>(plantas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.planta, parent, false);
        }

        planta = getItem(position);

        TextView plantaSelec = convertView.findViewById(R.id.planta_fila);

        plantaSelec.setText(String.valueOf(planta.getNumPlanta()));

        return convertView;
    }


    @Override
    public int getCount() {
        return listaPlantas.size();
    }

    @Nullable
    @Override
    public Planta getItem(int position) {
        return listaPlantas.get(position);
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.planta, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.planta_fila);

        Planta item = getItem(position);

        if (item != null) {
            textView.setText(String.valueOf(item.getNumPlanta()));
        }

        return convertView;
    }

}
