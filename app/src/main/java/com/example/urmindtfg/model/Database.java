package com.example.urmindtfg.model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Database {

    private FirebaseFirestore dB = FirebaseFirestore.getInstance();

    public boolean add(String tabla, String id, HashMap<String,Object> lista){
        try {
            dB.collection(tabla).document(id).set(lista);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Task<DocumentSnapshot> get(String tabla, String id){
        return dB.collection(tabla).document(id).get();
    }
}
