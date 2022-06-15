package com.example.urmindtfg.ui.logros;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.urmindtfg.R;
import com.example.urmindtfg.databinding.FragmentLogrosBinding;
import com.example.urmindtfg.entitis.Constantes;
import com.example.urmindtfg.entitis.Logros;
import com.example.urmindtfg.entitis.Usuario;
import com.example.urmindtfg.model.ChangeWindow;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class LogrosFragment extends Fragment {
    private FragmentLogrosBinding binding;
    private ArrayList<CheckBox> listaCheckDiarios, listaCheckSemanales;
    private int puntuacion;
    private FirebaseFirestore dB;

    private List<Boolean> listaBoolDiarios, listaBoolSemanales;

    private Usuario usuario;
    private Logros logros;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLogrosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void init(){
        puntuacion = 0;
        listaCheckDiarios = new ArrayList<>();
        listaCheckSemanales = new ArrayList<>();
        listaBoolDiarios = new ArrayList<>();
        listaBoolSemanales = new ArrayList<>();

        //Intanciamos los datos necesarios
        dB = FirebaseFirestore.getInstance();
        usuario = new Usuario();
        SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE);
        ChangeWindow.recogerDatosUsuario(usuario, prefs);

        //Instanciamos los logros
        listaCheckDiarios.add(binding.checkDiaria1);
        listaCheckDiarios.add(binding.checkDiaria2);
        listaCheckDiarios.add(binding.checkDiaria3);
        listaCheckDiarios.add(binding.checkDiaria4);

        listaCheckSemanales.add(binding.checkSemanal1);
        listaCheckSemanales.add(binding.checkSemanal2);
        listaCheckSemanales.add(binding.checkSemanal3);
        listaCheckSemanales.add(binding.checkSemanal4);
        listaCheckSemanales.add(binding.checkSemanal5);
        listaCheckSemanales.add(binding.checkSemanal6);
        listaCheckSemanales.add(binding.checkSemanal7);
        listaCheckSemanales.add(binding.checkSemanal8);
        listaCheckSemanales.add(binding.checkSemanal9);
        listaCheckSemanales.add(binding.checkSemanal10);
        listaCheckSemanales.add(binding.checkSemanal11);
        listaCheckSemanales.add(binding.checkSemanal12);

        getTablaLogros();

        //Añadimos los escuchadores a los logros
        listaCheckDiarios.forEach(e -> {
            e.setOnClickListener(l -> {
                updatePuntosDiarios(e);
                updateDatos();
            });
            e.setChecked(false);
                });
        listaCheckSemanales.forEach(e -> {
            e.setOnClickListener(l -> {
                updatePuntosSemanales(e);
                updateDatos();
            });
            e.setChecked(false);
        });
    }

    private void updateDatos(){
        updateDiarios();
        updateSemanales();
        logros = new Logros(listaBoolDiarios, listaBoolSemanales);
        updateTablaLogros(logros);
    }

    private void updateDiarios(){
        listaBoolDiarios = new ArrayList<>();

        listaCheckDiarios.forEach(e -> {
            if (e.isChecked()) {
                listaBoolDiarios.add(true);
            }else {
                listaBoolDiarios.add(false);
            }
        });
    }

    private void updateSemanales(){
        listaBoolSemanales = new ArrayList<>();

        listaCheckSemanales.forEach(e ->{
            if (e.isChecked()) {
                listaBoolSemanales.add(true);
            }else {
                listaBoolSemanales.add(false);
            }
        });
        binding.txtPuntuacion.setText(String.valueOf(puntuacion));
    }

    private void updatePuntosDiarios(CheckBox checkBox){
        if(checkBox.isChecked()){
            puntuacion += 10;
        }else {
            puntuacion -= 10;
        }
        binding.txtPuntuacion.setText(String.valueOf(puntuacion));
    }
    private void updatePuntosSemanales(CheckBox checkBox){
        if(checkBox.isChecked()){
            puntuacion += 30;
        }else {
            puntuacion -= 30;
        }
        binding.txtPuntuacion.setText(String.valueOf(puntuacion));
    }
    private void updateTablaLogros(Logros logros){
        dB.collection(Constantes.KEY_TABLA_LOGROS).document(usuario.getEmail()).set(logros);
    }

    private void getTablaLogros(){
        try {
            dB.collection(Constantes.KEY_TABLA_LOGROS).document(usuario.getEmail()).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                listaBoolDiarios = (List<Boolean>) document.get(Constantes.KEY_LISTADIARIO_LOGROS);
                                listaBoolSemanales = (List<Boolean>) document.get(Constantes.KEY_LISTASEMANAL_LOGROS);
                                boolean bool;

                                for(int i = 0; i < listaCheckDiarios.size(); i++){
                                    bool = listaBoolDiarios.get(i);
                                    listaCheckDiarios.get(i).setChecked(bool);
                                    if(bool){
                                        puntuacion+=10;
                                    }
                                }
                                for(int i = 0; i < listaCheckSemanales.size(); i++){
                                    bool = listaBoolSemanales.get(i);
                                    listaCheckSemanales.get(i).setChecked(bool);
                                    if(bool){
                                        puntuacion+=30;
                                    }
                                }
                                binding.txtPuntuacion.setText(String.valueOf(puntuacion));
                            }
                        }
                    });
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

}