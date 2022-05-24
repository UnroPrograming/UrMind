package com.example.urmindtfg;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.urmindtfg.databinding.ActivityMainBinding;
import com.example.urmindtfg.entitis.Constantes;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class ControladorNavigation extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private String email,proveedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_logros, R.id.nav_cuenta, R.id.nav_recomendaciones, R.id.nav_temas, R.id.nav_ubicaciones, R.id.nav_configuracion,R.id.nav_chat).setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Bundle extras = getIntent().getExtras();
        email = extras.getString(Constantes.KEY_EMAIL_USUARIOS);
        proveedor = extras.getString(Constantes.KEY_PROVEEDOR_USUARIOS);
        guardarDatos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //Guarda los datos en una librería interna
    private void guardarDatos(){
        SharedPreferences.Editor prefsEdit = getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE).edit();
        prefsEdit.putString(Constantes.KEY_EMAIL_USUARIOS,email);
        prefsEdit.putString(Constantes.KEY_PROVEEDOR_USUARIOS,proveedor);
        prefsEdit.apply();
    }
}