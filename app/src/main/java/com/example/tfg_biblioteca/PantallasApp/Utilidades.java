package com.example.tfg_biblioteca.PantallasApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Base64;

import androidx.appcompat.app.AlertDialog;

import com.example.tfg_biblioteca.Clases.Usuario;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Utilidades {

    private static Utilidades myUtilidades;

    private Utilidades(){

    }

    public static Utilidades getMyUtilidades(){

        if(myUtilidades == null){
            myUtilidades = new Utilidades();
        }
        return myUtilidades;

    }

    public Usuario obtenerUsuario(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("usuario", Context.MODE_PRIVATE);

        String objetoSerializado = sharedPreferences.getString("usuario", "");

        Usuario objetoRecuperado = null;

        if (!objetoSerializado.isEmpty()) {
            byte[] bytes = Base64.decode(objetoSerializado, Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                objetoRecuperado = (Usuario) objectInputStream.readObject();
                objectInputStream.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return objetoRecuperado;

    }

    public void cerrarSesion(Context context){

        AlertDialog.Builder dialogo = new AlertDialog.Builder(context);

        dialogo.setTitle("Cerrar Sesion").
                setMessage("¿Estas seguro de que quieres salir de la aplicación?")
                .setPositiveButton("Aceptar", (dialogInterface, i) -> {
                    Intent myIntent = new Intent(context, Login.class);
                    context.startActivity(myIntent);
                })
                .setNegativeButton("Cancelar", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });

        AlertDialog dialog = dialogo.create();
        dialog.show();

    }


}
