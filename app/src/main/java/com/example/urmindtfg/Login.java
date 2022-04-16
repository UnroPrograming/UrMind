package com.example.urmindtfg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener{

    //Para mandar un aviso a analitics
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth firebaseAuth;
    private EditText txtEmail,txtPass;
    private Button btnRegistrarse, btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
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
}