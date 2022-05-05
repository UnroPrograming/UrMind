package com.example.urmindtfg.model;

import com.example.urmindtfg.entitis.Usuario;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Database {

    private FirebaseFirestore dB = FirebaseFirestore.getInstance();
    private String nombreTabla;

    public Database() {
    }

    public Database(String nombreTabla) {
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

    public Object getUsuario(String id){
        return dB.collection(nombreTabla).document(id).get().getResult().toObject(Object.class);
    }
}
