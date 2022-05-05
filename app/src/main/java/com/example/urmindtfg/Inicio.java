package com.example.urmindtfg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.urmindtfg.model.ChangeWindow;
import com.example.urmindtfg.model.Controlador_Navigation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;

@EActivity(R.layout.activity_inicio)
public class Inicio extends AppCompatActivity{

    //Variables de android
    @ViewById
    public TextView txt_email;
    @ViewById
    public TextView txt_provider;
    @ViewById
    public TextView txt_direccion;
    @ViewById
    public TextView txt_telefono;

    @ViewById
    public Button btn_cerrarSesion;
    @ViewById
    public Button btn_forzarFallo;
    @ViewById
    public Button btn_guardar;
    @ViewById
    public Button btn_recuperar;
    @ViewById
    public Button btn_eliminar;

    //Variables de firebase
    private FirebaseCrashlytics firebaseCrashlytics;//Para los informes de errores
    private FirebaseRemoteConfig remoteConfig;//Para editar la app desde firebase
    private FirebaseFirestore dataBase;//Para la base de datos

    //Variables Login
    private String email, proveedor;

    @AfterViews
    public void onCreate() {
        Bundle extras = getIntent().getExtras();

        //Firebase
        firebaseCrashlytics = FirebaseCrashlytics.getInstance();
        remoteConfig = FirebaseRemoteConfig.getInstance();
        dataBase = FirebaseFirestore.getInstance();

        //Login
        email = extras.getString("Email");
        proveedor = extras.getString("Provider");

        //Setup
        setup(email, proveedor);

        guardarDatos();
        remoteConfig();
    }

    @Click
    void btn_cerrarSesion(){
        //Para deslogearse de firebase
        FirebaseAuth.getInstance().signOut();
        onBackPressed();

        //Para borrar la librería interna
        SharedPreferences.Editor prefsEdit = getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE).edit();
        prefsEdit.clear();
        prefsEdit.apply();
    }
    @Click
    void btn_guardar(){
        //Creamos una lista de objetos
        HashMap<String,Object> datos = new HashMap<>();
        datos.put("proveedor" , proveedor);
        datos.put("direccion", txt_direccion.getText().toString());
        datos.put("phone", txt_telefono.getText().toString());

        dataBase.collection("users").document(email).set(datos);
    }

    @Click
    void btn_recuperar(){
        dataBase.collection("users").document(email).get().addOnSuccessListener(e -> {
            txt_direccion.setText(e.get("direccion").toString());
            txt_telefono.setText(e.get("phone").toString());
        });
    }

    @Click
    void btn_eliminar(){
        dataBase.collection("users").document(email).delete();
    }

    @Click
    void btn_forzarFallo(){
        //Enviar información adicional
        firebaseCrashlytics.setUserId(email);
        firebaseCrashlytics.setCustomKey("provider", proveedor);

        //Enviar log de contexto
        firebaseCrashlytics.log("Se ha pulsado el botón FORZAR ERROR");

        //Forzado de error
        throw new RuntimeException("Forzado de error");
    }

    @Click
    void btn_cambiarVentana(){
        HashMap<String,String> lista = new HashMap();
        lista.put("Email",email);
        lista.put("Provider", proveedor);

        ChangeWindow.cambiarVentana(this, lista, reg_usuario_.class);
    }
    @Click
    void btn_Chat(){
        ChangeWindow.cambiarVentana(this, Controlador_Navigation.class);
    }

    //Le damos el correo y el proveedor
    private void setup(String email, String provider){
        txt_email.setText(email);
        txt_provider.setText(provider);
    }

    //Guarda los datos en una librería interna
    private void guardarDatos(){
        SharedPreferences.Editor prefsEdit = getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE).edit();
        prefsEdit.putString("email",txt_email.getText().toString());
        prefsEdit.putString("proveedor",txt_provider.getText().toString());
        prefsEdit.apply();
    }

    private void remoteConfig(){

        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(60).build();
        remoteConfig.setConfigSettingsAsync(configSettings);

        HashMap<String, Object> valoresDefecto = new HashMap();
        valoresDefecto.put("btn_ForzarFallo_show",true);
        valoresDefecto.put("btn_ForzarFallo_text","Forzar error");

        remoteConfig.setDefaultsAsync(valoresDefecto);

        final Task<Void> fetch = remoteConfig.fetch(0);
        fetch.addOnSuccessListener(this, unused -> {
            //Activamos la búsqueda
            remoteConfig.fetchAndActivate();

            //Recuperamos los datos del botón
            boolean btn_forzarFallo_show = remoteConfig.getBoolean("btn_ForzarFallo_show");
            String btn_forzarFallo_text = remoteConfig.getString("btn_ForzarFallo_text");

            //Los metemos en la aplicación
            if (btn_forzarFallo_show) {
                btn_forzarFallo.setVisibility(View.VISIBLE);
            } else {
                btn_forzarFallo.setVisibility(View.INVISIBLE);
            }
            btn_forzarFallo.setText(btn_forzarFallo_text);

        });
    }
}