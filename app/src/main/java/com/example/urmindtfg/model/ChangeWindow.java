package com.example.urmindtfg.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.urmindtfg.Inicio;
import com.example.urmindtfg.Inicio_;
import com.example.urmindtfg.entitis.Usuario;

import java.util.HashMap;

public class ChangeWindow {

    public static void cambiarVentana(Context context, Class clase){

        Intent intent = new Intent(context, clase);

        context.startActivity(intent);
    }

    public static void cambiarVentana(Context context, Class clase, Boolean flag){
        if(flag){
            Intent intent = new Intent(context, clase);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

    }

    public static void cambiarVentana(Context context, HashMap<String, String> lista, Class clase){

        Intent intent = new Intent(context, clase);

        lista.forEach((clave, valor)->{
            intent.putExtra(clave, valor);
        });

        context.startActivity(intent);
    }

}
