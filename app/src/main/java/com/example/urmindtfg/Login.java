package com.example.urmindtfg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener{

    //Para mandar un aviso a analitics
    private FirebaseAnalytics mFirebaseAnalytics;

    //Para autentificacion con firebase
    private FirebaseAuth firebaseAuth;

    //Nos permite crear una librería claveValor interna
    private SharedPreferences prefs = getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE);

    //Elementos android
    private EditText txtEmail,txtPass;
    private Button btnRegistrarse, btnLogin;
    private LinearLayout layInicio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        //layInicio = findViewById(R.id.lay_inicio);
        txtEmail = findViewById(R.id.eTxt_email);
        txtPass = findViewById(R.id.eTxt_pass);
        btnRegistrarse = findViewById(R.id.btn_registrarse);
        btnRegistrarse.setOnClickListener(this);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        //Cada vez que iniciemos la app se mandará un aviso a google analitics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString("Mensaje", "Integración de firebase completa");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        //comprobarSesion();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_registrarse:
                if(validacion()){
                    //Llamamos al metodo para crear un nuevo usuario y contraseña
                    firebaseAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(),txtPass.getText().toString())
                            .addOnCompleteListener(l2 -> {
                                //Si el registro es correcto pasamos a la nueva pantalla
                                if(l2.isSuccessful()){
                                    showHome(l2.getResult().getUser().getEmail(), ProviderType.BASIC);
                                }else{
                                    showAlert("Error","Hay un error al registrarse");
                                }
                            });
                }
                break;
            case R.id.btn_login:
                if(validacion()){
                    //Llamamos al método para iniciar sesion con usuario y contraseña
                    firebaseAuth.signInWithEmailAndPassword(txtEmail.getText().toString(),txtPass.getText().toString())
                            .addOnCompleteListener(l2 -> {
                                //Si el registro es correcto pasamos a la nueva pantalla
                                if(l2.isSuccessful()){
                                    showHome(l2.getResult().getUser().getEmail(), ProviderType.BASIC);
                                }else{
                                    showAlert("Error","Hay un error al registrarse");
                                }
                            });
                }
                break;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        //Cuando iniciemos la pestaña ponemos el layout visible
        layInicio.setVisibility(View.VISIBLE);
    }

    private boolean validacion(){
        return txtEmail.getText().length()>0 && txtPass.getText().length()>0;
    }

    private void showAlert(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.setPositiveButton("Aceptar",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showHome(String email, ProviderType proveedor){

        Intent homeIntent = new Intent(this, Inicio.class);
        homeIntent.putExtra("Email",email);
        homeIntent.putExtra("Provider",proveedor.name());

        startActivity(homeIntent);
    }


    //Nos valida si se ha iniciado sesión anteriormente y así pase directamente al menú home
    private void comprobarSesion(){
        String email = prefs.getString("email",null);
        String proveedor = prefs.getString("proveedor",null);

        if (email!= null && proveedor != null) {
            layInicio.setVisibility(View.INVISIBLE);//Si hay sesión iniciada no aparece el formulario
            showHome(email, ProviderType.valueOf(proveedor));//Si hay sesión iniciada pasa a la pestaña de inicio
        }
    }
}