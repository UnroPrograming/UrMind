package com.example.urmindtfg;

import androidx.annotation.Nullable;
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


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.remoteconfig.RemoteConfigComponent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener{

    //Firebase
    private FirebaseAnalytics mFirebaseAnalytics;//Para mandar un aviso a analitics
    private FirebaseAuth firebaseAuth;//Para autentificacion con firebase
    private FirebaseMessaging firebaseMessaging;//Para mandar notificaciones

    //Elementos android
    private EditText txtEmail,txtPass;
    private Button btnRegistrarse, btnLogin, btnGoogle;
    private LinearLayout layLogin;

    //Constante
    private static final int GOOGLE_SIGN_IN = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //UwU
        setContentView(R.layout.activity_login);
        layLogin = findViewById(R.id.lay_login);
        txtEmail = findViewById(R.id.eTxt_email);
        txtPass = findViewById(R.id.eTxt_pass);
        btnRegistrarse = findViewById(R.id.btn_registrarse);
        btnRegistrarse.setOnClickListener(this);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        btnGoogle = findViewById(R.id.btn_google);
        btnGoogle.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseMessaging = FirebaseMessaging.getInstance();


        //Cada vez que iniciemos la app se mandará un aviso a google analitics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString("Mensaje", "Integración de firebase completa");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        comprobarSesion();
        nombrarGrupo();
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
            case R.id.btn_google:
                //Solicitamos el id de la web y el gmail del usuario
                GoogleSignInOptions googleConf = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                GoogleSignInClient googleClient = GoogleSignIn.getClient(this, googleConf);
                googleClient.signOut();//Para que en el caso de que ya haya unca
                startActivityForResult(googleClient.getSignInIntent(),GOOGLE_SIGN_IN);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Cuando iniciemos la pestaña ponemos el layout visible
        layLogin.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            //En el caso de que el número sea el mismo significa que nos hemos autentificado
            if (requestCode == GOOGLE_SIGN_IN) {

                //Recuperamos la cuenta de google
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //Si la cuenta no es nula la introducimos en firebase
                if(account != null){
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                    firebaseAuth.signInWithCredential(credential).addOnCompleteListener( l ->{
                        if(l.isSuccessful()){
                            showHome(account.getEmail(), ProviderType.GOOGLE);
                        }else{
                            showAlert("Error","Hay un error al registrarse");
                        }
                    });
                }
            }
        }catch (ApiException e){
            showAlert("Error","No se ha podido recuperar la cuenta");
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


    //Nos valida si se ha iniciado sesión anteriormente y así pase directamente al menú home
    private void comprobarSesion(){
        SharedPreferences prefs = getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE);
        String email = prefs.getString("email",null);
        String proveedor = prefs.getString("proveedor",null);

        if (email!= null && proveedor != null) {
            layLogin.setVisibility(View.INVISIBLE);//Si hay sesión iniciada no aparece el formulario
            showHome(email, ProviderType.valueOf(proveedor));//Si hay sesión iniciada pasa a la pestaña de inicio
        }
    }

    //Cuando se ejecute este método este usuario pertenecerá a un grupo
    private void nombrarGrupo(){
        //A los grupos les llamaremos Temas(Topics)
        firebaseMessaging.subscribeToTopic("topic1");
    }


}