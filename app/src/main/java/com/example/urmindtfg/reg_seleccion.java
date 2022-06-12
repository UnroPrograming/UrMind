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

@EActivity(R.layout.activity_reg_seleccion)
public class reg_seleccion extends AppCompatActivity {

    private String email, provider;

    @AfterViews
    public void onCreate() {

        //Login
        Bundle extras = getIntent().getExtras();
        email = extras.getString(Constantes.KEY_EMAIL_USUARIOS);
        provider = extras.getString(Constantes.KEY_PROVEEDOR_USUARIOS);
    }

    @Click
    public void btn_usuario() {
       ChangeWindow.cambiarVentana(this, email, provider, reg_usuario_.class);
    }

    @Click
    public void btn_psicologo() {
        ChangeWindow.cambiarVentana(this, email, provider, reg_psicologo_.class);
    }

    @Click
    public void btn_empresa() {
        ChangeWindow.cambiarVentana(this, email, provider, reg_empresa_.class);
    }
}