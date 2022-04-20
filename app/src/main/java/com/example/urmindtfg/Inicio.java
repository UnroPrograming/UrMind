package com.example.urmindtfg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;

public class Inicio extends AppCompatActivity implements View.OnClickListener{

    //Variables de android
    private TextView txtEmail, txtPass, txtDireccion, txtTelefono;
    private Button btnCerrarSesion, btnForzarFallo, btnGuardar, btnRecuperar, btnEliminar;

    //Variables de firebase
    private FirebaseCrashlytics firebaseCrashlytics;//Para los informes de errores
    private FirebaseRemoteConfig remoteConfig;//Para editar la app desde firebase
    private FirebaseFirestore dataBase;//Para la base de datos

    //Variables Login
    private String email, proveedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //Android
        Bundle extras = getIntent().getExtras();
        txtEmail = findViewById(R.id.txt_email);
        txtPass = findViewById(R.id.txt_pass);
        txtDireccion = findViewById(R.id.txt_direccion);
        txtTelefono = findViewById(R.id.txt_telefono);

        btnCerrarSesion = findViewById(R.id.btn_cerrarSesion);
        btnCerrarSesion.setOnClickListener(this);
        btnForzarFallo = findViewById(R.id.btn_forzarFallo);
        btnForzarFallo.setOnClickListener(this);
        btnGuardar = findViewById(R.id.btn_guardar);
        btnGuardar.setOnClickListener(this);
        btnRecuperar = findViewById(R.id.btn_recuperar);
        btnRecuperar.setOnClickListener(this);
        btnEliminar = findViewById(R.id.btn_eliminar);
        btnEliminar.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cerrarSesion:
                //Para deslogearse de firebase
                FirebaseAuth.getInstance().signOut();
                onBackPressed();

                //Para borrar la librería interna
                SharedPreferences.Editor prefsEdit = getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE).edit();
                prefsEdit.clear();
                prefsEdit.apply();
                break;

            case R.id.btn_guardar:
                //Creamos una lista de objetos
                HashMap<String,Object> datos = new HashMap<>();
                datos.put("proveedor" , proveedor);
                datos.put("direccion", txtDireccion.getText().toString());
                datos.put("phone", txtTelefono.getText().toString());

                dataBase.collection("users").document(email).set(datos);
                break;

            case R.id.btn_recuperar:
                dataBase.collection("users").document(email).get().addOnSuccessListener(e -> {
                   txtDireccion.setText(e.get("direccion").toString());
                   txtTelefono.setText(e.get("phone").toString());
                });
                break;

            case R.id.btn_eliminar:
                dataBase.collection("users").document(email).delete();
                break;

            case R.id.btn_forzarFallo:
                //Enviar información adicional
                firebaseCrashlytics.setUserId(email);
                firebaseCrashlytics.setCustomKey("provider", proveedor);

                //Enviar log de contexto
                firebaseCrashlytics.log("Se ha pulsado el botón FORZAR ERROR");

                //Forzado de error
                throw new RuntimeException("Forzado de error");
        }

    }

    //Le damos el correo y el proveedor
    private void setup(String email, String provider){
        txtEmail.setText(email);
        txtPass.setText(provider);
    }

    //Guarda los datos en una librería interna
    private void guardarDatos(){
        SharedPreferences.Editor prefsEdit = getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE).edit();
        prefsEdit.putString("email",txtEmail.getText().toString());
        prefsEdit.putString("proveedor",txtPass.getText().toString());
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
                btnForzarFallo.setVisibility(View.VISIBLE);
            } else {
                btnForzarFallo.setVisibility(View.INVISIBLE);
            }
            btnForzarFallo.setText(btn_forzarFallo_text);

        });
    }
}