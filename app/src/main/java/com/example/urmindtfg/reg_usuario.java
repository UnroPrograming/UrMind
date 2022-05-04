package com.example.urmindtfg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.urmindtfg.entitis.Usuario;
import com.example.urmindtfg.model.Validaciones;
import com.google.firebase.firestore.FirebaseFirestore;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_reg_usuario)
public class reg_usuario extends AppCompatActivity {
    //Elementos android
    @ViewById
    public TextView txt_email;
    @ViewById
    public TextView txt_provider;
    @ViewById
    public EditText eTxt_telefono;
    @ViewById
    public EditText eTxt_Nombre;
    @ViewById
    public EditText eTxt_Apellidos;
    @ViewById
    public EditText eTxt_DNI;

    @ViewById
    public Button btn_registrarUsuario;

    //Database
    private Usuario usuario;
    private FirebaseFirestore dB;
    private final String tableName = "Usuarios";

    @AfterViews
    public void onCreate() {

        //Login
        Bundle extras = getIntent().getExtras();
        txt_email.setText(extras.getString("Email"));
        txt_provider.setText(extras.getString("Provider"));

        //Firebase
        dB = FirebaseFirestore.getInstance();
    }

    @Click
    public void btn_registrarUsuario() {
        //Validamos el usuario
        if(Validaciones.validarUsuario(eTxt_Nombre.getText().toString(), eTxt_Apellidos.getText().toString(), eTxt_telefono.getText().toString(), eTxt_DNI.getText().toString())){

            //Creamos el usuario
            usuario = new Usuario(txt_email.getText().toString(),
                    eTxt_Nombre.getText().toString(),
                    eTxt_Apellidos.getText().toString(),
                    Integer.parseInt(eTxt_telefono.getText().toString()),
                    eTxt_DNI.getText().toString(),
                    txt_provider.getText().toString());

            //Pasamos el usuario a HashMap y lo subimos a la base de datos
            dB.collection(tableName).document(usuario.getEmail()).set(usuario.toHashMap());
        }
    }
}