package com.example.urmindtfg.ui.post;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.urmindtfg.R;
import com.example.urmindtfg.databinding.ActivityNewPostBinding;
import com.example.urmindtfg.entitis.Constantes;
import com.example.urmindtfg.model.Img;
import com.example.urmindtfg.model.Post;
import com.example.urmindtfg.model.Validaciones;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class NewPost extends AppCompatActivity {

    private ActivityNewPostBinding binding;
    private FirebaseFirestore dB;
    private String imagenEncriptada;
    private SharedPreferences prefs;
    private String currentUserId, nombreUser, imgUser;
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setListeners();
    }
    private void init(){
        dB = FirebaseFirestore.getInstance();
        //Obtenemos el currentId
        prefs = getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE);
        currentUserId = prefs.getString(Constantes.KEY_EMAIL_USUARIOS,null);
        nombreUser = prefs.getString(Constantes.KEY_NOMBRE_USUARIOS,null);
        imgUser = prefs.getString(Constantes.KEY_IMG_USUARIOS,null);
        System.out.println("id:" + currentUserId);
    }

    private void setListeners(){
        binding.btnSubir.setOnClickListener(e -> {
            if(Validaciones.validarPost(binding.eTxtTitulo.getText().toString(),binding.eTxtPost.getText().toString(),imagenEncriptada)){

                //Creamos el post
                post = new Post(
                        currentUserId,
                        nombreUser,
                        imgUser,
                        binding.eTxtTitulo.getText().toString(),
                        imagenEncriptada,
                        binding.eTxtPost.getText().toString(),
                        getModoLecturaDateTime(new Date()),
                        new Date()
                );

                //Pasamos el Post a HashMap
                HashMap<String, Object> postMap = post.toHashMap();
                dB.collection(Constantes.KEY_TABLA_POST).add(postMap);
                onBackPressed();
            }
        });

        binding.imgFotoPost.setOnClickListener(e ->{
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
            pickImage.launch(intent);
        });
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
                        binding.imgFotoPost.setImageBitmap(bitmap);
                        binding.txtAddImagen.setVisibility(View.GONE);
                        imagenEncriptada = Img.getImgString(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    private String getModoLecturaDateTime(Date date){
        return new SimpleDateFormat("MMMM dd, yyyy -hh:mm a", Locale.getDefault()).format(date);
    }
}
