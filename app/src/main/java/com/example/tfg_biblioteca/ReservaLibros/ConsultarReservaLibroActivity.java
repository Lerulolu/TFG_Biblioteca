package com.example.tfg_biblioteca.ReservaLibros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfg_biblioteca.Clases.ReservaLibro;
import com.example.tfg_biblioteca.ControladorUsuarioComun.Utilidades;
import com.example.tfg_biblioteca.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ConsultarReservaLibroActivity extends AppCompatActivity {

    private TextView autorLibro, ISBNLibro, nombreLibro, estadoReserva, fechaReserva, idReserva;
    private ImageView fotoLibro;
    private Bundle bundle;
    private ImageButton btnSalir;
    private ReservaLibro reservaElegida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_reserva_libro);

        autorLibro = findViewById(R.id.autorLibro);
        ISBNLibro = findViewById(R.id.ISBNLibro);
        nombreLibro = findViewById(R.id.nombreLibro);
        estadoReserva = findViewById(R.id.estadoReserva);
        fechaReserva = findViewById(R.id.fechaReserva);
        idReserva = findViewById(R.id.idReserva);
        btnSalir = findViewById(R.id.btnSalir);
        fotoLibro = findViewById(R.id.fotoLibro);

        bundle = getIntent().getExtras();

        reservaElegida = (ReservaLibro) bundle.getSerializable("reservaElegida");

        autorLibro.setText(reservaElegida.getLibro().getAutorLibro());
        ISBNLibro.setText(reservaElegida.getLibro().getISBN());
        nombreLibro.setText(reservaElegida.getLibro().getNombreLibro());
        idReserva.setText(String.valueOf(reservaElegida.getIdReservaLibro()));

        String nombreImagen = reservaElegida.getLibro().getSrcImagenLibro();
        int resourceId = getResources().getIdentifier(nombreImagen, "drawable", getPackageName());
        fotoLibro.setImageResource(resourceId);

        fechaReserva.setText(reservaElegida.getFechaReserva());

        //Si han pasado 3 dias a partir de la fecha de reserva -> Estado = COMPLETADO
        //Si han pasado 3 dias a partir de la fecha de reserva -> Estado = EN PROCESO
        Calendar calendar = Calendar.getInstance();

        Date fechaActual = calendar.getTime();

        int estado = compararFechas(fechaActual, reservaElegida.getFechaReserva());

        if (estado == 1) {
            estadoReserva.setText(R.string.consultaReservaLibro_estadoCompletado);
            estadoReserva.setTextColor(getResources().getColor(R.color.verde, null));
        } else {
            estadoReserva.setText(R.string.consultaReservaLibro_estadoProceso);
            estadoReserva.setTextColor(getResources().getColor(R.color.azulLibro, null));
        }

        btnSalir.setOnClickListener(view -> Utilidades.getMyUtilidades().cerrarSesion(this));

    }

    private int compararFechas(Date fechaAct, String fechaReserva) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int estado = 0;

        try {

            Date fechaDeReserva = dateFormat.parse(fechaReserva);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaDeReserva);
            calendar.add(Calendar.DAY_OF_YEAR, 3);

            fechaDeReserva = calendar.getTime();

            if (fechaAct.compareTo(fechaDeReserva) < 0) {
                estado = 0; //ESTADO EN PROCESO
            } else if (fechaAct.compareTo(fechaDeReserva) >= 0) {
                estado = 1; //ESTADO COMPLETADO
            }

            return estado;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return estado;
    }

}
