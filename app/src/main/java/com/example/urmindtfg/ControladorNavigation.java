package com.example.urmindtfg;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.urmindtfg.databinding.ActivityMainBinding;
import com.example.urmindtfg.entitis.Constantes;
import com.example.urmindtfg.entitis.Usuario;
import com.example.urmindtfg.model.Img;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class ControladorNavigation extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private String email,proveedor;
    private Usuario usuario;
    private TextView txt_email, txt_nombre;
    private ImageView img_perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        //Para poder coger los datos del navView
        View headView = navigationView.getHeaderView(0);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_logros, R.id.nav_cuenta, R.id.nav_recomendaciones, R.id.nav_temas, R.id.nav_ubicaciones, R.id.nav_configuracion,R.id.nav_chat).setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        txt_nombre = headView.findViewById(R.id.txt_nombre);
        txt_email = headView.findViewById(R.id.txt_correo);
        img_perfil = headView.findViewById(R.id.img_perfil);

        Bundle extras = getIntent().getExtras();
        email = extras.getString(Constantes.KEY_EMAIL_USUARIOS);
        proveedor = extras.getString(Constantes.KEY_PROVEEDOR_USUARIOS);
        usuario = new Usuario();

        recogerDatosUsuario();
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

    //Recoger datos del usuario
    private void recogerDatosUsuario(){
        FirebaseFirestore dB = FirebaseFirestore.getInstance();
        DocumentReference docRef = dB.collection(Constantes.KEY_TABLA_USUARIOS).document(email);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();

                if (document.exists()) {
                    Map<String, Object> datosObtenidos = document.getData();

                    usuario.setNombre(datosObtenidos.get(Constantes.KEY_NOMBRE_USUARIOS).toString());
                    usuario.setApellidos(datosObtenidos.get(Constantes.KEY_APELLIDO_USUARIOS).toString());
                    usuario.setEmail(datosObtenidos.get(Constantes.KEY_EMAIL_USUARIOS).toString());
                    usuario.setDNI(datosObtenidos.get(Constantes.KEY_DNI_USUARIOS).toString());
                    usuario.setTelefono(Integer.parseInt(datosObtenidos.get(Constantes.KEY_TELEFONO_USUARIOS).toString()));
                    usuario.setProveedor(datosObtenidos.get(Constantes.KEY_PROVEEDOR_USUARIOS).toString());
                    usuario.setImagen(datosObtenidos.get(Constantes.KEY_IMG_USUARIOS).toString());

                    guardarDatos();
                    setDatosUsuarios();
                }
            }
        });
    }

    //Guarda los datos en una librería interna
    private void guardarDatos(){
        SharedPreferences.Editor prefsEdit = getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE).edit();
        prefsEdit.putString(Constantes.KEY_EMAIL_USUARIOS, email);
        prefsEdit.putString(Constantes.KEY_PROVEEDOR_USUARIOS, proveedor);
        prefsEdit.putString(Constantes.KEY_NOMBRE_USUARIOS, usuario.getNombre());
        prefsEdit.putString(Constantes.KEY_APELLIDO_USUARIOS, usuario.getApellidos());
        prefsEdit.putString(Constantes.KEY_DNI_USUARIOS, usuario.getDNI());
        prefsEdit.putString(Constantes.KEY_TELEFONO_USUARIOS, String.valueOf(usuario.getTelefono()));
        prefsEdit.putString(Constantes.KEY_IMG_USUARIOS, usuario.getImagen());
        prefsEdit.apply();
    }

    //Pone los datos del usuario en el recylerView
    private void setDatosUsuarios(){
        txt_nombre.setText(usuario.getNombre());
        txt_email.setText(usuario.getEmail());
        img_perfil.setImageBitmap(Img.getImgDesencriptada(usuario.getImagen()));
    }
}