package com.example.urmindtfg.model;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.urmindtfg.entitis.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {

    private  FirebaseFirestore dB;
    private String nombreTabla;
    private Map<String, Object> datosObtenidos;

    public Database() {
        dB = FirebaseFirestore.getInstance();
    }

    public Database(String nombreTabla) {
        dB = FirebaseFirestore.getInstance();
        this.nombreTabla = nombreTabla;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public boolean add(String id, HashMap<String, String> lista){
        try {
            dB.collection(nombreTabla).document(id).set(lista);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

//    public Usuario getUsuario(String id){
//        Usuario usuario = dB.collection(nombreTabla).document(id).get().getResult().toObject(Usuario.class);
//        System.out.println("                                      aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaah"+ usuario.getEmail());
//        return usuario;
//    }

//    public Usuario getUsuario(String id){
//        Usuario usuario = new Usuario();
//
//        dB.collection("usuarios").document(id).get().addOnSuccessListener(e ->{
//            if (e.exists()) {
//                System.out.println("firebase bien");
//
//                String email = e.get("email").toString();
//                System.out.println("usuario email    "+email);
//                usuario.setEmail(email);
//                usuario.setProveedor(e.get("proveedor").toString());
//                usuario.setNombre(e.get("nombre").toString());
//                usuario.setApellidos(e.get("apellidos").toString());
//                usuario.setTelefono(Integer.parseInt(e.get("telefono").toString()));
//                usuario.setDNI(e.get("dni").toString());
//
//                usuario.toString();
//            }else {
//                System.out.println("        "+ "firebase gilipollas");
//            }
//        });
//
//        return usuario;
//    }
    public Usuario getUsuario(Usuario usuario, String id){
        DocumentReference docRef = dB.collection("usuarios").document(id);
        try{
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()){
                        datosObtenidos = document.getData();
                        usuario.setEmail(id);
                        usuario.setProveedor(datosObtenidos.get("proveedor").toString());
                        usuario.setNombre(datosObtenidos.get("nombre").toString());
                        usuario.setApellidos(datosObtenidos.get("apellidos").toString());
                        usuario.setTelefono(Integer.parseInt(datosObtenidos.get("telefono").toString()));
                        usuario.setDNI(datosObtenidos.get("dni").toString());
                        Log.e(TAG, usuario.toString());

                    }else {
                        Log.e(TAG, "No such document");
                    }
                }else {
                    Log.e(TAG, "get failed with ", task.getException());
                }
            });
        }catch (NullPointerException e){
            e.printStackTrace();
            Log.e(TAG, "usuario: "+id );
        }

        return usuario;
    }

    public void getUsuario(String id, datosCallBack callback) {
       Usuario usuario = new Usuario();
        try{
            dB.collection("usuarios").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        if (document.getData().get("correo").equals(id)){
                            usuario.setEmail((String) document.getData().get("email"));
                            if (callback != null) {
                                callback.usuarioRecibido(usuario);
                            }
                            System.out.println("dato encontrado" + usuario.toString());
                        }
                    }
                }
            });
        }catch (NullPointerException e){
            e.printStackTrace();
            Log.e(TAG, "usuario: "+id );
        }
    }

//    public LiveData<List<String>> getUsuarios(String id){
//        MutableLiveData liveData = new MutableLiveData<String>();
//
//        dB.collection("usuarios").get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()){
//                List<Usuario> listaUsuario = new ArrayList<>();
//                for(QueryDocumentSnapshot document : task.getResult()){
//                    if(document.getId().equals(id)){
//                        Usuario usuario = new Usuario();
//                        usuario.setEmail(document.getString("email").toString());
//                        usuario.setProveedor(document.get("proveedor").toString());
//                        usuario.setNombre(document.get("nombre").toString());
//                        usuario.setApellidos(document.get("apellidos").toString());
//                        usuario.setTelefono(Integer.parseInt(document.get("telefono").toString()));
//                        usuario.setDNI(document.get("dni").toString());
//
//                        listaUsuario.add(usuario);
//                    }
//                }
//                liveData.setValue(listaUsuario);
//            }else {
//                System.out.println("                                                      ERROOOOOORRRRRRRRRRRRRRRRR en el registro");
//            }
//        });
//
//        return liveData;
//    }
}
