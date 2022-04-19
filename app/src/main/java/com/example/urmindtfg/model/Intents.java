package com.example.urmindtfg.model;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.urmindtfg.Inicio;
import com.example.urmindtfg.ProviderType;

public class Intents extends AppCompatActivity {

    public void showHome(String email, ProviderType proveedor){

        Intent homeIntent = new Intent(getBaseContext(), Inicio.class);
        homeIntent.putExtra("Email",email);
        homeIntent.putExtra("Provider",proveedor.name());

        startActivity(homeIntent);
    }
}
