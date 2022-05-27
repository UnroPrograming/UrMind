package com.example.urmindtfg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.urmindtfg.entitis.Constantes;
import com.example.urmindtfg.entitis.Psicologo;
import com.example.urmindtfg.entitis.Usuario;
import com.example.urmindtfg.model.ChangeWindow;
import com.example.urmindtfg.model.Img;
import com.example.urmindtfg.model.Validaciones;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makeramen.roundedimageview.RoundedImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

@EActivity(R.layout.activity_reg_psicologo)
public class reg_psicologo extends AppCompatActivity {
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
    public EditText eTxt_NumColegiado;
    @ViewById
    public EditText eTxt_Apellidos;
    @ViewById
    public EditText eTxt_DNI;

    @ViewById
    public Button btn_registrarUsuario;

    @ViewById
    public RoundedImageView img_fotoPerfil;

    //Database
    private Psicologo psicologo;
    private FirebaseFirestore dB;
    private String imagenEncriptada;

    @AfterViews
    public void onCreate() {

        //Login
        Bundle extras = getIntent().getExtras();
        txt_email.setText(extras.getString(Constantes.KEY_EMAIL_USUARIOS));
        txt_provider.setText(extras.getString(Constantes.KEY_PROVEEDOR_USUARIOS));

        //Base de datos
        dB = FirebaseFirestore.getInstance();
    }

    @Click
    public void btn_registrarPsicologo() {
        //Validamos el psicologo
        if(Validaciones.validarPsicologo(eTxt_NumColegiado.getText().toString() ,eTxt_Nombre.getText().toString(), eTxt_Apellidos.getText().toString(), eTxt_telefono.getText().toString(), eTxt_DNI.getText().toString(),imagenEncriptada)){

            //Creamos el psicologo
            psicologo = new Psicologo(txt_email.getText().toString(),
                    eTxt_Nombre.getText().toString(),
                    eTxt_Apellidos.getText().toString(),
                    Integer.parseInt(eTxt_telefono.getText().toString()),
                    eTxt_DNI.getText().toString(),
                    txt_provider.getText().toString(),
                    imagenEncriptada,
                    eTxt_NumColegiado.getText().toString()
            );

            //Pasamos el usuario a HashMap
            HashMap<String,String> psicologosMap = psicologo.toHashMap();

            //Lo subimos a la base de datos
            dB.collection(Constantes.KEY_TABLA_PSICOLOGOS).document(txt_email.getText().toString()).set(psicologosMap);

            //Cambiamos la ventana
            ChangeWindow.cambiarVentana(this, txt_email.getText().toString(), txt_provider.getText().toString(), ControladorNavigation.class);
        }
    }

    @Click
    public void lay_addImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
        pickImage.launch(intent);
    }

    //Metodo que nos permite coger una imagen de la galería
    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK){
                    Uri imageUri = result.getData().getData();

                    try{
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        img_fotoPerfil.setImageBitmap(bitmap);
                        txt_addImagen.setVisibility(View.GONE);
                        imagenEncriptada = Img.setImgEncriptada(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
    );
}