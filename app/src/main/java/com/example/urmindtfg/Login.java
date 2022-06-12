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


import com.example.urmindtfg.entitis.UserType;
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

import java.util.Locale;
import java.util.Map;

@EActivity(R.layout.activity_login)
public class Login extends AppCompatActivity {

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
    public void onCreate() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseMessaging = FirebaseMessaging.getInstance();
        dB = FirebaseFirestore.getInstance();

        comprobarSesion();
    }

    @Click
    public void btn_registrarse() {
        if (Validaciones.validacionEmailPass(eTxt_email.getText().toString(), eTxt_pass.getText().toString())) {
            //Llamamos al metodo para crear un nuevo usuario y contraseña
            firebaseAuth.createUserWithEmailAndPassword(eTxt_email.getText().toString(), eTxt_pass.getText().toString())
                    .addOnCompleteListener(l2 -> {
                        //Si el registro es correcto pasamos a la nueva pantalla
                        if (l2.isSuccessful()) {
                            //Cambiamos la ventana
                            ChangeWindow.cambiarVentana(this, l2.getResult().getUser().getEmail(), ProviderType.BASIC.toString(), reg_seleccion_.class);
                        } else {
                            Validaciones.showAlert(this, "Error", "El usuario ya existe");
                        }
                    });
        }
    }

    @Click
    public void btn_login() {
        String email = eTxt_email.getText().toString();
        String pass = eTxt_pass.getText().toString();

        if (Validaciones.validacionEmailPass(email, pass)) {
            DocumentReference docRefUsuario = dB.collection(Constantes.KEY_TABLA_USUARIOS).document(email);

            docRefUsuario.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        datosObtenidos = document.getData();
                        if (((String) datosObtenidos.get(Constantes.KEY_EMAIL_USUARIOS)).equalsIgnoreCase(email)) {

                            //Llamamos al método para iniciar sesion con usuario y contraseña
                            firebaseAuth.signInWithEmailAndPassword(email, pass)
                                    .addOnCompleteListener(l2 -> {
                                        //Si el registro es correcto pasamos a la nueva pantalla
                                        if (l2.isSuccessful()) {
                                            //Cambiamos la ventana
                                            if(datosObtenidos.get(Constantes.KEY_TIPO_USUARIO).toString().equalsIgnoreCase(UserType.USUARIO.toString())){
                                                ChangeWindow.cambiarVentana(this, email, ProviderType.BASIC.toString(), ControladorNavigationUsuario.class);
                                            }else if(datosObtenidos.get(Constantes.KEY_TIPO_USUARIO).toString().equalsIgnoreCase(UserType.PSICOLOGO.toString())){
                                                ChangeWindow.cambiarVentana(this, email, ProviderType.BASIC.toString(), ControladorNavigationPsicologo.class);
                                            }else if(datosObtenidos.get(Constantes.KEY_TIPO_USUARIO).toString().equalsIgnoreCase(UserType.EMPRESA.toString())){
                                                ChangeWindow.cambiarVentana(this, email, ProviderType.BASIC.toString(), ControladorNavigationPsicologo.class);
                                            }
                                        } else {
                                            Validaciones.showAlert(this, "Error", "Hay un error al loguearse");
                                        }
                                    });
                        } else {
                            Validaciones.showAlert(this, "Fallo al iniciar sesión", "Este usuario no está registrado");
                        }
                    }
                }else {
                    Validaciones.showAlert(this, "Error", "Hay un error al loguearse");
                }
            });
        }
    }


    @Click
    public void btn_google() {
        //Solicitamos el id de la web y el gmail del usuario
        GoogleSignInOptions googleConf = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient googleClient = GoogleSignIn.getClient(this, googleConf);
        googleClient.signOut();//Para que en el caso de que ya haya una se cierre la sesión
        startActivityForResult(googleClient.getSignInIntent(), GOOGLE_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Cuando iniciemos la pestaña ponemos el layout visible
        lay_login.setVisibility(View.VISIBLE);
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
                if (account != null) {
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                    firebaseAuth.signInWithCredential(credential).addOnCompleteListener(l -> {

                        //Si funciona
                        if (l.isSuccessful()) {
                            String email = account.getEmail();
                            String proveedor = ProviderType.GOOGLE.toString();

                            //Comprobamos que ha iniciado sesión anteriormente
                            DocumentReference docRefUsuario = dB.collection(Constantes.KEY_TABLA_USUARIOS).document(email);
                            DocumentReference docRefPsicologo = dB.collection(Constantes.KEY_TABLA_PSICOLOGOS).document(email);

                            docRefUsuario.get().addOnCompleteListener(e -> {
                                if (e.isSuccessful()) {
                                    DocumentSnapshot document = e.getResult();

                                    if (document.exists()) {
                                        datosObtenidos = document.getData();
                                        if (((String) datosObtenidos.get(Constantes.KEY_EMAIL_USUARIOS)).equalsIgnoreCase(email)) {
                                            //Cambiamos la ventana
                                            if(datosObtenidos.get(Constantes.KEY_TIPO_USUARIO).toString().equalsIgnoreCase(UserType.USUARIO.toString())){
                                                ChangeWindow.cambiarVentana(this, email, proveedor, ControladorNavigationUsuario.class);
                                            }else if(datosObtenidos.get(Constantes.KEY_TIPO_USUARIO).toString().equalsIgnoreCase(UserType.PSICOLOGO.toString())){
                                                ChangeWindow.cambiarVentana(this, email, proveedor, ControladorNavigationPsicologo.class);
                                            }else if(datosObtenidos.get(Constantes.KEY_TIPO_USUARIO).toString().equalsIgnoreCase(UserType.EMPRESA.toString())){
                                                ChangeWindow.cambiarVentana(this, email, proveedor, ControladorNavigationPsicologo.class);
                                            }
                                        }
                                    } else {
                                        ChangeWindow.cambiarVentana(this, email, proveedor, reg_seleccion_.class);
                                    }
                                }
                            });
                        }
                    });
                }
            }
        } catch (ApiException e) {
            Validaciones.showAlert(this, "Error", "No se ha podido recuperar la cuenta");
        }
    }

    //Nos valida si se ha iniciado sesión anteriormente y así pase directamente al menú home
    private void comprobarSesion() {
        SharedPreferences prefs = getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE);
        String email = prefs.getString(Constantes.KEY_EMAIL_USUARIOS, null);
        String proveedor = prefs.getString(Constantes.KEY_PROVEEDOR_USUARIOS, null);

        //Si hay sesión iniciada
        if (email != null && proveedor != null) {

            //No aparece el formulario
            lay_login.setVisibility(View.INVISIBLE);

            //Pasa a la pestaña de inicio
            ChangeWindow.cambiarVentana(this, email, ProviderType.valueOf(proveedor).toString(), ControladorNavigationUsuario.class);
        }
    }

}