package com.example.urmindtfg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Inicio extends AppCompatActivity implements View.OnClickListener{

    private TextView txtEmail, txtPass;
    private Button btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Bundle extras = getIntent().getExtras();
        txtEmail = findViewById(R.id.txt_email);
        txtPass = findViewById(R.id.txt_pass);
        btnCerrarSesion = findViewById(R.id.btn_cerrarSesion);
        btnCerrarSesion.setOnClickListener(this);

        //Setup
        setup(extras.getString("Email"),extras.getString("Provider"));

        guardarDatos();
    }

    @Override
    public void onClick(View v) {
        //Para deslogearse de firebase
        FirebaseAuth.getInstance().signOut();
        onBackPressed();

        //Para borrar la librería interna
        SharedPreferences.Editor prefsEdit = getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE).edit();
        prefsEdit.clear();
        prefsEdit.apply();
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
}