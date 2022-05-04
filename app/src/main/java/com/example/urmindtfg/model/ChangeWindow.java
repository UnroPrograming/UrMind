package com.example.urmindtfg.model;

import android.content.Context;
import android.content.Intent;

import com.example.urmindtfg.Inicio;
import com.example.urmindtfg.Inicio_;

import java.util.HashMap;

public class ChangeWindow {

    //String email, ProviderType proveedor
    public static void cambiarVentana(Context context, Class clase){

        Intent intent = new Intent(context, clase);

        context.startActivity(intent);
    }

    //String email, ProviderType proveedor
    public static void cambiarVentana(Context context, HashMap<String, String> lista, Class clase){

        Intent intent = new Intent(context, clase);

        lista.forEach((clave, valor)->{
            intent.putExtra(clave, valor);
        });

        context.startActivity(intent);
    }

}
