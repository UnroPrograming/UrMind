package com.example.urmindtfg.ui.temas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.urmindtfg.R;


import com.example.urmindtfg.databinding.FragmentChatBinding;
import com.example.urmindtfg.databinding.FragmentTemasBinding;
import com.example.urmindtfg.model.ChangeWindow;
import com.example.urmindtfg.ui.chat.UsersActivity;
import com.example.urmindtfg.ui.temas.activitys.Adicciones;
import com.example.urmindtfg.ui.temas.activitys.Adicciones_;
import com.example.urmindtfg.ui.temas.activitys.AmorPareja_;
import com.example.urmindtfg.ui.temas.activitys.AnsiedadEstres_;
import com.example.urmindtfg.ui.temas.activitys.TrastornoAlimenticio_;
import com.example.urmindtfg.ui.temas.activitys.TrastornoAnimo_;

public class TemasFragment extends Fragment {

    public static TemasFragment newInstance() {
        return new TemasFragment();
    }
    private FragmentTemasBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentTemasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnAdicciones.setOnClickListener(e->{
            ChangeWindow.cambiarVentana(getActivity().getApplicationContext(), Adicciones_.class,true);
        });
        binding.btnAmorPareja.setOnClickListener(e->{
            ChangeWindow.cambiarVentana(getActivity().getApplicationContext(), AmorPareja_.class,true);
        });
        binding.btnAnsiedadEstres.setOnClickListener(e->{
            ChangeWindow.cambiarVentana(getActivity().getApplicationContext(), AnsiedadEstres_.class,true);
        });
        binding.btnTrastornoAlimentario.setOnClickListener(e->{
            ChangeWindow.cambiarVentana(getActivity().getApplicationContext(), TrastornoAlimenticio_.class,true);
        });
        binding.btnTrastornoAnimo.setOnClickListener(e->{
            ChangeWindow.cambiarVentana(getActivity().getApplicationContext(), TrastornoAnimo_.class,true);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}