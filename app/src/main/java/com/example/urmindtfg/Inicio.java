package com.example.urmindtfg;

import androidx.appcompat.app.AppCompatActivity;

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
    }

    @Override
    public void onClick(View v) {
        //Para deslogearse
        FirebaseAuth.getInstance().signOut();
        onBackPressed();
    }

    private void setup(String email, String provider){
        txtEmail.setText(email);
        txtPass.setText(provider);
    }


}