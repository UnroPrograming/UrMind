package com.example.urmindtfg.model;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class Validaciones {

    public static boolean validacionEmailPass(String email, String pass){
        return email.length()>0 && pass.length()>0;
    }

    public static void showAlert(Context context, String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.setPositiveButton("Aceptar",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
