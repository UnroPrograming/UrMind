package com.example.urmindtfg.model;

import android.content.Context;
import android.content.Intent;

import com.example.urmindtfg.Inicio;

import java.util.HashMap;

public class ChangeWindow {

    //String email, ProviderType proveedor
    public static void showHome(Context context, HashMap<String, String> lista){

        Intent intent = new Intent(context, Inicio.class);

        lista.forEach((clave, valor)->{
            intent.putExtra(clave, valor);
        });

        context.startActivity(intent);
    }

}
