package com.example.urmindtfg.model;

import android.text.Editable;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Validaciones extends AppCompatActivity {

    public static boolean comprobarEmailPass(Editable email, Editable pass){
        return email.length()>0 && pass.length()>0;
    }

    public void showAlert(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.setPositiveButton("Aceptar",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
