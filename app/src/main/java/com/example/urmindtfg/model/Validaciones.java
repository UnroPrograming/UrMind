package com.example.urmindtfg.model;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.example.urmindtfg.entitis.Usuario;

public class Validaciones {
    public static void showAlert(Context context, String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.setPositiveButton("Aceptar",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static boolean validacionEmailPass(String email, String pass){
        return email.length()>0 && pass.length()>0;
    }

    public static boolean validarUsuario(String nombre, String apellidos, String telefono, String DNI,String imagen) {
        if (imagen == null || imagen.length()<=0){
            return false;
        }
        return true;
    }

    public static boolean validarPsicologo(String numColegiado, String nombre, String apellidos, String telefono, String DNI,String imagen) {
        if (imagen == null || imagen.length()<=0){
            return false;
        }
        return true;
    }
}
