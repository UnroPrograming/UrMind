package com.example.urmindtfg.ui.logros;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.urmindtfg.R;
import com.example.urmindtfg.databinding.FragmentHomeBinding;
import com.example.urmindtfg.databinding.FragmentLogrosBinding;
import com.example.urmindtfg.databinding.FragmentTemasBinding;

import java.util.ArrayList;
import java.util.List;

public class LogrosFragment extends Fragment {
    private FragmentLogrosBinding binding;
    private ArrayList<CheckBox> listaCheckDiarios, listaCheckSemanales;
    private int puntuacion;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
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

        listaCheckDiarios.forEach(e -> e.setOnClickListener(l -> updateDiarios()));
        listaCheckSemanales.forEach(e -> e.setOnClickListener(l -> updateSemanales()));
    }

    private void updateDiarios(){
        listaCheckDiarios.forEach(e -> {
            if (e.isChecked()) puntuacion += 10;
        });
        binding.txtPuntuacion.setText(String.valueOf(puntuacion));
    }

    private void updateSemanales(){
        listaCheckSemanales.forEach(e ->{
            if (e.isChecked()) puntuacion += 30;
        });
        binding.txtPuntuacion.setText(String.valueOf(puntuacion));
    }
}