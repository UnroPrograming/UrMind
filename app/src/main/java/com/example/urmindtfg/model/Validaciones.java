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

    public static boolean validarUsuario(String nombre, String apellidos, String telefono, String DNI) {
        return true;
    }

    public static boolean registroPrevio(String email){
        boolean result = false;
        try {
            Database db = new Database("usuarios");
            Usuario usuario = new Usuario();
            db.getUsuario(usuario,email);
            System.out.println("                                                                                              aaaaaaaaaaaaaaaaaaaaaaaah "+ usuario);
            result = usuario.getEmail().equals(email);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
