package com.example.urmindtfg.ui.buscadorEmpleados;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.urmindtfg.R;
import com.example.urmindtfg.databinding.FragmentBuscadorEmpleadosBinding;
import com.example.urmindtfg.databinding.FragmentUsersUsuarioBinding;
import com.example.urmindtfg.entitis.Constantes;
import com.example.urmindtfg.entitis.Psicologo;
import com.example.urmindtfg.entitis.UserType;
import com.example.urmindtfg.entitis.Usuario;
import com.example.urmindtfg.model.ChangeWindow;
import com.example.urmindtfg.ui.buscadorEmpleados.adapters.PsicologoAdapter;
import com.example.urmindtfg.ui.buscadorEmpleados.listeners.PsicologoListener;
import com.example.urmindtfg.ui.chat.adapters.UsersAdapter;
import com.example.urmindtfg.ui.chat.listeners.UsersListener;
import com.example.urmindtfg.ui.post.PostActivityUsuario;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class BuscadorEmpleadosFragment extends Fragment implements PsicologoListener {

    private FragmentBuscadorEmpleadosBinding binding;
    private SharedPreferences prefs;
    private String currentUserId;
    private String currentEmpresa;
    private FirebaseFirestore database;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBuscadorEmpleadosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        prefs = getActivity().getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE);
        currentUserId = prefs.getString("email",null);

        database = FirebaseFirestore.getInstance();

        setListeners();
        getEmpresa();
        getUsers();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setListeners(){
        binding.btnBuscador.setOnClickListener(e-> getUsersBuscador());
    }

    private void getEmpresa(){
        database.collection(Constantes.KEY_TABLA_EMPRESA).document(currentUserId).get()
                .addOnCompleteListener(task2 -> {
                    if (task2.isSuccessful()) {
                        DocumentSnapshot document = task2.getResult();
                        if (document.exists()) {
                            currentEmpresa = document.getString(Constantes.KEY_NOMBRE_EMPRESA);
                        }

                    }
                });
    }

    private void getUsers(){
        loading(true);

        //Obtenemos todos los usuarios de la base de datos
        database.collection(Constantes.KEY_TABLA_USUARIOS).get()
                .addOnCompleteListener(task ->{
                    loading(false);

                    if (task.isSuccessful()){
                        List<Psicologo> listaPsicologo = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshotUser : task.getResult()){

                            //No pilla nuestro usuario
                            if(currentUserId.equals(queryDocumentSnapshotUser.getId())){
                                continue;
                            }
//                            if (!(Objects.requireNonNull(queryDocumentSnapshotUser.getString(Constantes.KEY_TIPO_USUARIO)).equalsIgnoreCase(UserType.PSICOLOGO.toString()))){
//                                continue;
//                            }

                            Psicologo psicologo = new Psicologo();
                            psicologo.setNombre(queryDocumentSnapshotUser.getString(Constantes.KEY_NOMBRE_USUARIOS));
                            psicologo.setApellidos(queryDocumentSnapshotUser.getString(Constantes.KEY_APELLIDO_USUARIOS));
                            psicologo.setEmail(queryDocumentSnapshotUser.getString(Constantes.KEY_EMAIL_USUARIOS));
                            psicologo.setDNI(queryDocumentSnapshotUser.getString(Constantes.KEY_DNI_USUARIOS));
                            psicologo.setTelefono(Integer.parseInt(queryDocumentSnapshotUser.getString(Constantes.KEY_TELEFONO_USUARIOS)));
                            psicologo.setProveedor(queryDocumentSnapshotUser.getString(Constantes.KEY_PROVEEDOR_USUARIOS));
                            psicologo.setImagen(queryDocumentSnapshotUser.getString(Constantes.KEY_IMG_USUARIOS));

                            database.collection(Constantes.KEY_TABLA_PSICOLOGOS).document(psicologo.getEmail()).get()
                                    .addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            DocumentSnapshot document = task2.getResult();
                                            if (document.exists()) {
                                                String empresaPsicologo = document.getString(Constantes.KEY_EMPRESA_PSICOLOGO);
                                                if(empresaPsicologo != null && empresaPsicologo.equalsIgnoreCase(currentEmpresa)){
                                                    System.out.println("entra");
                                                    psicologo.setEmpleadoActual(true);
                                                    listaPsicologo.add(psicologo);

                                                }else {
                                                    listaPsicologo.add(psicologo);
                                                }
                                                if(listaPsicologo.size() >0){
                                                    PsicologoAdapter usersAdapter = new PsicologoAdapter(listaPsicologo, this);
                                                    binding.recyclerViewUsuarios.setAdapter(usersAdapter);
                                                    binding.recyclerViewUsuarios.setVisibility(View.VISIBLE);
                                                }else {
                                                    showErrorMessage();
                                                }
                                            }
                                        }

                                    });
                        }
                    }else{
                        showErrorMessage();
                    }
                });
    }

    private void getUsersBuscador(){
        loading(true);

        //Obtenemos todos los usuarios de la base de datos
        database.collection(Constantes.KEY_TABLA_USUARIOS).get()
                .addOnCompleteListener(task ->{
                    loading(false);

                    if (task.isSuccessful()){
                        List<Psicologo> listaPsicologo = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshotUser : task.getResult()){

                            //No pilla nuestro usuario
                            if(currentUserId.equals(queryDocumentSnapshotUser.getId())){
                                continue;
                                //Buscador
                            }else if(!(binding.eTxtBuscador.getText().toString().equalsIgnoreCase(queryDocumentSnapshotUser.getString(Constantes.KEY_NOMBRE_USUARIOS)))){
                                if (binding.eTxtBuscador.getText().toString().length()>0 && binding.eTxtBuscador.getText() != null){
                                    continue;
                                }
                            }
                            //Pilla solo los psicologos
                            if (!(queryDocumentSnapshotUser.getString(Constantes.KEY_TIPO_USUARIO).equalsIgnoreCase(UserType.PSICOLOGO.toString()))) {
                                continue;
                            }
                            Psicologo psicologo = new Psicologo();
                            psicologo.setNombre(queryDocumentSnapshotUser.getString(Constantes.KEY_NOMBRE_USUARIOS));
                            psicologo.setApellidos(queryDocumentSnapshotUser.getString(Constantes.KEY_APELLIDO_USUARIOS));
                            psicologo.setEmail(queryDocumentSnapshotUser.getString(Constantes.KEY_EMAIL_USUARIOS));
                            psicologo.setDNI(queryDocumentSnapshotUser.getString(Constantes.KEY_DNI_USUARIOS));
                            psicologo.setTelefono(Integer.parseInt(queryDocumentSnapshotUser.getString(Constantes.KEY_TELEFONO_USUARIOS)));
                            psicologo.setProveedor(queryDocumentSnapshotUser.getString(Constantes.KEY_PROVEEDOR_USUARIOS));
                            psicologo.setImagen(queryDocumentSnapshotUser.getString(Constantes.KEY_IMG_USUARIOS));

                            database.collection(Constantes.KEY_TABLA_PSICOLOGOS).document(psicologo.getEmail()).get()
                                    .addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            DocumentSnapshot document = task2.getResult();
                                            if (document.exists()) {
                                                String empresaPsicologo = document.getString(Constantes.KEY_EMPRESA_PSICOLOGO);
                                                if(empresaPsicologo != null && empresaPsicologo.equalsIgnoreCase(currentEmpresa)){
                                                    psicologo.setEmpleadoActual(true);
                                                    listaPsicologo.add(psicologo);
                                                }else {
                                                    listaPsicologo.add(psicologo);
                                                }
                                            }
                                        }
                                    });

                            listaPsicologo.add(psicologo);

                            if(listaPsicologo.size() >0){
                                PsicologoAdapter usersAdapter = new PsicologoAdapter(listaPsicologo, this);
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
    public void onUserClicked(Psicologo usuario) {
        if(usuario.getEmpleadoActual() == null){
            usuario.setEmpresa(currentEmpresa);
            usuario.setEmpleadoActual(true);
            HashMap<String, Object> lista = usuario.toHashMap();
            database.collection(Constantes.KEY_TABLA_PSICOLOGOS).document(usuario.getEmail()).update(lista);
            getUsers();
        }else if(!usuario.getEmpleadoActual()){
            usuario.setEmpresa(currentEmpresa);
            usuario.setEmpleadoActual(true);
            HashMap<String, Object> lista = usuario.toHashMap();
            database.collection(Constantes.KEY_TABLA_PSICOLOGOS).document(usuario.getEmail()).update(lista);
            getUsers();
        }else if(usuario.getEmpleadoActual()){
            usuario.setEmpresa("");
            usuario.setEmpleadoActual(false);
            HashMap<String, Object> lista = usuario.toHashMap();
            database.collection(Constantes.KEY_TABLA_PSICOLOGOS).document(usuario.getEmail()).update(lista);
            getUsers();
        }
    }
}