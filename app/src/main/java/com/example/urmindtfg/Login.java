package com.example.urmindtfg;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


import com.example.urmindtfg.model.ChangeWindow;
import com.example.urmindtfg.entitis.ProviderType;
import com.example.urmindtfg.entitis.Constantes;
import com.example.urmindtfg.model.Validaciones;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

@EActivity(R.layout.activity_login)
public class Login extends AppCompatActivity{

    //Elementos android
    @ViewById
    public EditText eTxt_email;
    @ViewById
    public EditText eTxt_pass;

    @ViewById
    public Button btn_registrarse;
    @ViewById
    public Button btn_login;
    @ViewById
    public Button btn_google;

    @ViewById
    public LinearLayout lay_login;

    //Firebase
    private FirebaseAuth firebaseAuth;//Para autentificacion con firebase
    private FirebaseMessaging firebaseMessaging;//Para mandar notificaciones
    private FirebaseFirestore dB;
    private Map<String, Object> datosObtenidos;


    //Constante
    private static final int GOOGLE_SIGN_IN = 100;

    @AfterViews
    public void onCreate(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseMessaging = FirebaseMessaging.getInstance();
        dB = FirebaseFirestore.getInstance();

        comprobarSesion();
    }

    @Click
    public void btn_registrarse(){
        if(Validaciones.validacionEmailPass(eTxt_email.getText().toString(), eTxt_pass.getText().toString())) {
            //Llamamos al metodo para crear un nuevo usuario y contraseña
            firebaseAuth.createUserWithEmailAndPassword(eTxt_email.getText().toString(), eTxt_pass.getText().toString())
                    .addOnCompleteListener(l2 -> {
                        //Si el registro es correcto pasamos a la nueva pantalla
                        if (l2.isSuccessful()) {
                            //Cambiamos la ventana
                            HashMap<String, String> lista = new HashMap();
                            lista.put(Constantes.KEY_EMAIL_USUARIOS, l2.getResult().getUser().getEmail());
                            lista.put(Constantes.KEY_PROVEEDOR_USUARIOS, ProviderType.BASIC.toString());

                            //ChangeWindow.cambiarVentana(this, lista, Inicio_.class);
                            ChangeWindow.cambiarVentana(this, lista, reg_usuario_.class);
                        } else {
                            Validaciones.showAlert(this, "Error", "Hay un error al registrarse");
                        }
                    });
        }
    }
    @Click
    public void btn_login() {
        String email = eTxt_email.getText().toString();
        String pass = eTxt_pass.getText().toString();

        if(Validaciones.validacionEmailPass(email, pass)) {
            DocumentReference docRef = dB.collection(Constantes.KEY_TABLA_USUARIOS).document(email);

            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()){
                        datosObtenidos = document.getData();
                        if(((String)datosObtenidos.get(Constantes.KEY_EMAIL_USUARIOS)).equals(email)){

                            //Llamamos al método para iniciar sesion con usuario y contraseña
                            firebaseAuth.signInWithEmailAndPassword(email, pass)
                                    .addOnCompleteListener(l2 -> {
                                        //Si el registro es correcto pasamos a la nueva pantalla
                                        if (l2.isSuccessful()) {
                                            //Cambiamos la ventana
                                            HashMap<String, String> lista = new HashMap();
                                            lista.put(Constantes.KEY_EMAIL_USUARIOS, l2.getResult().getUser().getEmail());
                                            lista.put(Constantes.KEY_PROVEEDOR_USUARIOS, ProviderType.BASIC.toString());

                                            ChangeWindow.cambiarVentana(this, lista, Inicio_.class);
                                        } else {
                                            Validaciones.showAlert(this, "Error", "Hay un error al registrarse");
                                        }
                                    });

                        } else {
                            Validaciones.showAlert(this, "Fallo al iniciar sesión", "Este usuario no está registrado");
                        }

                        }else {
                            Validaciones.showAlert(this, "Fallo al iniciar sesión", "Este usuario no está registrado");
                        }

                    }else {
                        Validaciones.showAlert(this, "Fallo al iniciar sesión", "Este usuario no está registrado");
                    }
                });
        }
    }


    @Click
    public void btn_google(){
        //Solicitamos el id de la web y el gmail del usuario
        GoogleSignInOptions googleConf = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient googleClient = GoogleSignIn.getClient(this, googleConf);
        googleClient.signOut();//Para que en el caso de que ya haya una se cierre la sesión
        startActivityForResult(googleClient.getSignInIntent(),GOOGLE_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Cuando iniciemos la pestaña ponemos el layout visible
        lay_login.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
                super.onActivityResult(requestCode, resultCode, data);
                try {
                    //En el caso de que el número sea el mismo significa que nos hemos autentificado
                    if (requestCode == GOOGLE_SIGN_IN) {

                        //Recuperamos la cuenta de google
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);

                        //Si la cuenta no es nula la introducimos en firebase
                        if (account != null) {
                            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                            firebaseAuth.signInWithCredential(credential).addOnCompleteListener(l -> {

                                //Si funciona
                                if (l.isSuccessful()) {
                                    //Cambiamos la ventana si la validación es correcta
                                    HashMap<String, String> lista = new HashMap();
                                    lista.put(Constantes.KEY_EMAIL_USUARIOS, account.getEmail());
                                    lista.put(Constantes.KEY_PROVEEDOR_USUARIOS, ProviderType.GOOGLE.toString());

                                    //Comprobamos que ha iniciado sesión anteriormente
                                    DocumentReference docRef = dB.collection(Constantes.KEY_TABLA_USUARIOS).document(account.getEmail());

                                    docRef.get().addOnCompleteListener(e -> {
                                        if (e.isSuccessful()) {
                                            DocumentSnapshot document = e.getResult();

                                            if (document.exists()) {
                                                datosObtenidos = document.getData();
                                                if (((String) datosObtenidos.get(Constantes.KEY_EMAIL_USUARIOS)).equals(account.getEmail())) {
                                                    ChangeWindow.cambiarVentana(this, lista, Inicio_.class);
                                                }
                                            } else {
                                                ChangeWindow.cambiarVentana(this, lista, reg_usuario_.class);
                                            }
                                        } else {
                                            ChangeWindow.cambiarVentana(this, lista, reg_usuario_.class);
                                        }
                                    });
                        }
                    });
                }
            }
        }catch (ApiException e) {
                    Validaciones.showAlert(this, "Error", "No se ha podido recuperar la cuenta");
                }
    }

    //Nos valida si se ha iniciado sesión anteriormente y así pase directamente al menú home
    private void comprobarSesion(){
        SharedPreferences prefs = getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE);
        String email = prefs.getString("email",null);
        String proveedor = prefs.getString("proveedor",null);

        if (email!= null && proveedor != null) {
            lay_login.setVisibility(View.INVISIBLE);//Si hay sesión iniciada no aparece el formulario
            //Si hay sesión iniciada pasa a la pestaña de inicio
            HashMap<String,String> lista = new HashMap();
            lista.put(Constantes.KEY_EMAIL_USUARIOS,email);
            lista.put(Constantes.KEY_PROVEEDOR_USUARIOS,ProviderType.valueOf(proveedor).toString());

            ChangeWindow.cambiarVentana(this, lista,Inicio_.class);
        }
    }

}