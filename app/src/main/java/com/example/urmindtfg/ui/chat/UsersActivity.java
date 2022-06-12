package com.example.urmindtfg.ui.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.urmindtfg.R;
import com.example.urmindtfg.databinding.ActivityUsersBinding;
import com.example.urmindtfg.entitis.Constantes;
import com.example.urmindtfg.entitis.Usuario;
import com.example.urmindtfg.model.ChangeWindow;
import com.example.urmindtfg.ui.chat.adapters.UsersAdapter;
import com.example.urmindtfg.ui.chat.listeners.UsersListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersActivity extends AppCompatActivity implements UsersListener {

    private ActivityUsersBinding binding;
    private SharedPreferences prefs;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        prefs = getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE);
        currentUserId = prefs.getString("email",null);
        setContentView(binding.getRoot());

        setListeners();
        getUsers();
    }

    private void setListeners(){
        binding.imgBack.setOnClickListener(e -> onBackPressed());
        binding.btnBuscador.setOnClickListener(e-> getUsersBuscador());
    }

    private void getUsers(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        //Obtenemos todos los usuarios de la base de datos
        database.collection(Constantes.KEY_TABLA_USUARIOS)
                .get()
                .addOnCompleteListener(task ->{
                    loading(false);

                    if (task.isSuccessful()){
                        List<Usuario> listaUsuarios = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){

                            //No pilla nuestro usuario
                            if(currentUserId.equals(queryDocumentSnapshot.getId())){
                                continue;
                            }

                            Usuario usuario = new Usuario();
                            usuario.setNombre(queryDocumentSnapshot.getString(Constantes.KEY_NOMBRE_USUARIOS));
                            usuario.setApellidos(queryDocumentSnapshot.getString(Constantes.KEY_APELLIDO_USUARIOS));
                            usuario.setEmail(queryDocumentSnapshot.getString(Constantes.KEY_EMAIL_USUARIOS));
                            usuario.setDNI(queryDocumentSnapshot.getString(Constantes.KEY_DNI_USUARIOS));
                            usuario.setTelefono(Integer.parseInt(queryDocumentSnapshot.getString(Constantes.KEY_TELEFONO_USUARIOS)));
                            usuario.setProveedor(queryDocumentSnapshot.getString(Constantes.KEY_PROVEEDOR_USUARIOS));
                            usuario.setImagen(queryDocumentSnapshot.getString(Constantes.KEY_IMG_USUARIOS));

                            listaUsuarios.add(usuario);

                            if(listaUsuarios.size() >0){
                                UsersAdapter usersAdapter = new UsersAdapter(listaUsuarios, this);
                                binding.recyclerViewUsuarios.setAdapter(usersAdapter);
                                binding.recyclerViewUsuarios.setVisibility(View.VISIBLE);
                            }else {
                                showErrorMessage();
                            }
                        }
                    }else{
                        showErrorMessage();
                    }
                });
    }
    private void getUsersBuscador(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        //Obtenemos todos los usuarios de la base de datos
        database.collection(Constantes.KEY_TABLA_USUARIOS)
                .get()
                .addOnCompleteListener(task ->{
                    loading(false);

                    if (task.isSuccessful()){
                        List<Usuario> listaUsuarios = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){

                            //No pilla nuestro usuario
                            if(currentUserId.equals(queryDocumentSnapshot.getId())){
                                continue;
                            }else if(!(binding.eTxtBuscador.getText().toString().equalsIgnoreCase(queryDocumentSnapshot.getString(Constantes.KEY_NOMBRE_USUARIOS)))){
                                if (binding.eTxtBuscador.getText().toString().length()>0 && binding.eTxtBuscador.getText() != null){
                                    continue;
                                }
                            }

                            Usuario usuario = new Usuario();
                            usuario.setNombre(queryDocumentSnapshot.getString(Constantes.KEY_NOMBRE_USUARIOS));
                            usuario.setApellidos(queryDocumentSnapshot.getString(Constantes.KEY_APELLIDO_USUARIOS));
                            usuario.setEmail(queryDocumentSnapshot.getString(Constantes.KEY_EMAIL_USUARIOS));
                            usuario.setDNI(queryDocumentSnapshot.getString(Constantes.KEY_DNI_USUARIOS));
                            usuario.setTelefono(Integer.parseInt(queryDocumentSnapshot.getString(Constantes.KEY_TELEFONO_USUARIOS)));
                            usuario.setProveedor(queryDocumentSnapshot.getString(Constantes.KEY_PROVEEDOR_USUARIOS));
                            usuario.setImagen(queryDocumentSnapshot.getString(Constantes.KEY_IMG_USUARIOS));

                            listaUsuarios.add(usuario);

                            if(listaUsuarios.size() >0){
                                UsersAdapter usersAdapter = new UsersAdapter(listaUsuarios, this);
                                binding.recyclerViewUsuarios.setAdapter(usersAdapter);
                                binding.recyclerViewUsuarios.setVisibility(View.VISIBLE);
                            }else {
                                showErrorMessage();
                            }
                        }
                    }else{
                        showErrorMessage();
                    }
                });
    }
    private void showErrorMessage(){
        binding.txtMensajeError.setText(String.format("%s","No user avalible"));
        binding.txtMensajeError.setVisibility(View.VISIBLE);
    }
    private void loading(Boolean isLoading){
        if(isLoading){
            binding.progressBar.setVisibility(View.VISIBLE);
        }else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onUserClicked(Usuario usuario) {
        ChangeWindow.cambiarVentana(this,usuario,ChatActivity.class);
        finish();
    }
}