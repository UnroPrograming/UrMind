package com.example.urmindtfg.ui.cuenta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.urmindtfg.ControladorNavigationUsuario;
import com.example.urmindtfg.Login_;
import com.example.urmindtfg.R;
import com.example.urmindtfg.entitis.Constantes;
import com.example.urmindtfg.entitis.Usuario;
import com.example.urmindtfg.model.ChangeWindow;
import com.example.urmindtfg.model.Img;
import com.example.urmindtfg.model.Validaciones;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makeramen.roundedimageview.RoundedImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

@EActivity(R.layout.activity_editar_usuario)
public class EditarUsuario extends AppCompatActivity {
    //Elementos android
    @ViewById
    public TextView txt_email;
    @ViewById
    public TextView txt_provider;
    @ViewById
    public TextView txt_addImagen;
    @ViewById
    public EditText eTxt_telefono;
    @ViewById
    public EditText eTxt_Nombre;
    @ViewById
    public EditText eTxt_Apellidos;
    @ViewById
    public EditText eTxt_DNI;

    @ViewById
    public Button btn_editarUsuario;

    @ViewById
    public RoundedImageView img_fotoPerfil;

    //Database
    private Usuario usuario;
    private FirebaseFirestore dB;
    private String imagenEncriptada;

    @AfterViews
    public void onCreate() {
        //Base de datos
        usuario = new Usuario();
        dB = FirebaseFirestore.getInstance();

        precargaDatosUsuario();
    }

    @Click
    public void btn_editarUsuario() {
        //Validamos el usuario
        if (Validaciones.validarUsuario(eTxt_Nombre.getText().toString(), eTxt_Apellidos.getText().toString(), eTxt_telefono.getText().toString(), eTxt_DNI.getText().toString(), imagenEncriptada)) {

            //Eliminamos el usuario anterior
            dB.collection("users").document(usuario.getEmail()).delete();

            //Actualizamos los datos del usuario con los del activity
            usuario.actualizarUsuario(imagenEncriptada, eTxt_Nombre, eTxt_Apellidos, eTxt_telefono, eTxt_DNI, txt_email, txt_provider);

            //Pasamos el usuario nuevo a HashMap
            HashMap<String, String> usuarioMap = usuario.toHashMap();

            //Lo subimos a la base de datos
            dB.collection(Constantes.KEY_TABLA_USUARIOS).document(txt_email.getText().toString()).set(usuarioMap);

            //Los actualizamos de la librería interna
            guardarDatosLibreriaInterna();

            ChangeWindow.cambiarVentana(this, usuario.getEmail(), usuario.getProveedor(), ControladorNavigationUsuario.class);

        }else {
            Validaciones.showAlert(this,"Error registro","Rellena los campos");
        }
    }

    @Click
    public void btn_cerrarSesion() {
        //Para deslogearse de firebase
        FirebaseAuth.getInstance().signOut();

        //Para borrar la librería interna
        SharedPreferences.Editor prefsEdit = getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE).edit();
        prefsEdit.clear();
        prefsEdit.apply();

        ChangeWindow.cambiarVentana(this, Login_.class);
    }

    @Click
    public void btn_borrarSesion() {
        FirebaseAuth.getInstance().getCurrentUser().delete();
        dB.collection("users").document(usuario.getEmail()).delete();

        //Para borrar la librería interna
        SharedPreferences.Editor prefsEdit = getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE).edit();
        prefsEdit.clear();
        prefsEdit.apply();
        ChangeWindow.cambiarVentana(this, Login_.class);
    }

    @Click
    public void lay_addImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
        pickImage.launch(intent);
    }

    //Metodo que nos permite coger una imagen de la galería
    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Uri imageUri = result.getData().getData();

                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        img_fotoPerfil.setImageBitmap(bitmap);
                        txt_addImagen.setVisibility(View.GONE);
                        imagenEncriptada = Img.getImgString(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    private void precargaDatosUsuario(){
        SharedPreferences prefs = getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE);
        //Recogemos los datos del usuario
        ChangeWindow.recogerDatosUsuario(usuario, prefs);

        usuario.setUsuarioActivity(img_fotoPerfil, eTxt_Nombre, eTxt_Apellidos, eTxt_telefono, eTxt_DNI, txt_email, txt_provider);

        imagenEncriptada = Img.getImgString(img_fotoPerfil);

        txt_addImagen.setVisibility(View.GONE);
    }

    //Guarda los datos en una librería interna
    private void guardarDatosLibreriaInterna(){
        SharedPreferences.Editor prefsEdit = getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE).edit();
        //Borramos la librería anterior
        prefsEdit.clear();
        prefsEdit.apply();

        //Ponemos la nueva librería
        ChangeWindow.guardarDatosLibreriaInterna(prefsEdit, usuario);
        prefsEdit.apply();
    }
}
