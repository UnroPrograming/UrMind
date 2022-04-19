package com.example.urmindtfg.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.urmindtfg.Login;
import com.example.urmindtfg.ProviderType;
import com.example.urmindtfg.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class Firebase extends AppCompatActivity {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();//Para autentificacion con firebase
    private FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();//Para mandar notificaciones

    //Nos valida si se ha iniciado sesión anteriormente y así pase directamente al menú home
    public void comprobarSesion(LinearLayout layLogin){
        SharedPreferences prefs = getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE);
        String email = prefs.getString("email",null);
        String proveedor = prefs.getString("proveedor",null);

        Intents myIntents = new Intents();

        if (email!= null && proveedor != null) {
            layLogin.setVisibility(View.INVISIBLE);//Si hay sesión iniciada no aparece el formulario
            myIntents.showHome(email, ProviderType.valueOf(proveedor));//Si hay sesión iniciada pasa a la pestaña de inicio
        }
    }

    //Cuando se ejecute este método este usuario pertenecerá a un grupo
    public void nombrarGrupo(){
        //A los grupos les llamaremos Temas(Topics)
        firebaseMessaging.subscribeToTopic("topic1");
    }
}

