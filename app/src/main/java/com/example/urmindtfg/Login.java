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

<<<<<<< Updated upstream
=======

import com.example.urmindtfg.model.Firebase;
import com.example.urmindtfg.model.Intents;
import com.example.urmindtfg.model.Validaciones;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
>>>>>>> Stashed changes
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener{

<<<<<<< Updated upstream
    //Para mandar un aviso a analitics
    private FirebaseAnalytics mFirebaseAnalytics;

    //Para autentificacion con firebase
    private FirebaseAuth firebaseAuth;

    //Nos permite crear una librería claveValor interna
    private SharedPreferences prefs = getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE);
=======
    //Firebase
    private Firebase firebase;
    private FirebaseAuth firebaseAuth;//Para autentificacion con firebase
    private FirebaseMessaging firebaseMessaging;//Para mandar notificaciones
>>>>>>> Stashed changes

    //Elementos android
    private EditText txtEmail,txtPass;
    private Button btnRegistrarse, btnLogin;
    private LinearLayout layInicio;

    //Clases modelo
    Validaciones validaciones;
    Intents myIntents;


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
<<<<<<< Updated upstream

        //Cada vez que iniciemos la app se mandará un aviso a google analitics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString("Mensaje", "Integración de firebase completa");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        //comprobarSesion();
=======
        firebaseMessaging = FirebaseMessaging.getInstance();
        firebase = new Firebase();

        validaciones = new Validaciones();
        myIntents = new Intents();

        firebase.comprobarSesion(layLogin);
        firebase.nombrarGrupo();
>>>>>>> Stashed changes
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_registrarse:
                if(Validaciones.comprobarEmailPass(txtEmail.getText(),txtPass.getText())){
                    //Llamamos al metodo para crear un nuevo usuario y contraseña
                    firebaseAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(),txtPass.getText().toString())
                            .addOnCompleteListener(l2 -> {
                                //Si el registro es correcto pasamos a la nueva pantalla
                                if(l2.isSuccessful()){
                                    myIntents.showHome(l2.getResult().getUser().getEmail(), ProviderType.BASIC);
                                }else{
                                    validaciones.showAlert("Error","Hay un error al registrarse");
                                }
                            });
                }
                break;
            case R.id.btn_login:
                if(Validaciones.comprobarEmailPass(txtEmail.getText(),txtPass.getText())){
                    //Llamamos al método para iniciar sesion con usuario y contraseña
                    firebaseAuth.signInWithEmailAndPassword(txtEmail.getText().toString(),txtPass.getText().toString())
                            .addOnCompleteListener(l2 -> {
                                //Si el registro es correcto pasamos a la nueva pantalla
                                if(l2.isSuccessful()){
                                    myIntents.showHome(l2.getResult().getUser().getEmail(), ProviderType.BASIC);
                                }else{
                                    validaciones.showAlert("Error","Hay un error al registrarse");
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
<<<<<<< Updated upstream
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
=======
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
                            myIntents.showHome(account.getEmail(), ProviderType.GOOGLE);
                        }else{
                            validaciones.showAlert("Error","Hay un error al registrarse");
                        }
                    });
                }
            }
        }catch (ApiException e){
            validaciones.showAlert("Error","No se ha podido recuperar la cuenta");
        }
    }


>>>>>>> Stashed changes
}