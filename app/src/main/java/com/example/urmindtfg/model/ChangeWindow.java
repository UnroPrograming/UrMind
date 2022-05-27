package com.example.urmindtfg.model;

import static android.provider.Settings.System.getString;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.urmindtfg.Inicio;
import com.example.urmindtfg.Inicio_;
import com.example.urmindtfg.R;
import com.example.urmindtfg.entitis.Constantes;
import com.example.urmindtfg.entitis.ProviderType;
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

    public static void cambiarVentana(Context context, String email, String proveedor, Class clase){
        HashMap<String, String> lista = new HashMap();
        lista.put(Constantes.KEY_EMAIL_USUARIOS, email);
        lista.put(Constantes.KEY_PROVEEDOR_USUARIOS, proveedor);

        cambiarVentana(context,lista,clase);
    }

    public static void cambiarVentana(Context context, HashMap<String, String> lista, Class clase){

        Intent intent = new Intent(context, clase);

        lista.forEach((clave, valor)->{
            intent.putExtra(clave, valor);
        });

        context.startActivity(intent);
    }

    public static void cambiarVentana(Context context, Usuario usuario, Class clase){

        Intent intent = new Intent(context, clase);
        intent.putExtra(Constantes.KEY_USUARIO, usuario);
        context.startActivity(intent);
    }

    public static void guardarDatosLibreriaInterna(SharedPreferences.Editor prefsEdit, Usuario usuario){
        prefsEdit.putString(Constantes.KEY_EMAIL_USUARIOS, usuario.getEmail());
        prefsEdit.putString(Constantes.KEY_PROVEEDOR_USUARIOS, usuario.getProveedor());
        prefsEdit.putString(Constantes.KEY_NOMBRE_USUARIOS, usuario.getNombre());
        prefsEdit.putString(Constantes.KEY_APELLIDO_USUARIOS, usuario.getApellidos());
        prefsEdit.putString(Constantes.KEY_DNI_USUARIOS, usuario.getDNI());
        prefsEdit.putString(Constantes.KEY_TELEFONO_USUARIOS, String.valueOf(usuario.getTelefono()));
        prefsEdit.putString(Constantes.KEY_IMG_USUARIOS, usuario.getImagen());
    }
    public static void recogerDatosUsuario(Usuario usuario, SharedPreferences prefs){

        usuario.setEmail(prefs.getString(Constantes.KEY_EMAIL_USUARIOS,null));
        usuario.setProveedor(prefs.getString(Constantes.KEY_PROVEEDOR_USUARIOS,null));
        usuario.setNombre(prefs.getString(Constantes.KEY_NOMBRE_USUARIOS,null));
        usuario.setApellidos(prefs.getString(Constantes.KEY_APELLIDO_USUARIOS,null));
        usuario.setTelefono(Integer.parseInt(prefs.getString(Constantes.KEY_TELEFONO_USUARIOS,null)));
        usuario.setDNI(prefs.getString(Constantes.KEY_DNI_USUARIOS,null));
        usuario.setImagen(prefs.getString(Constantes.KEY_IMG_USUARIOS,null));
    }

}
