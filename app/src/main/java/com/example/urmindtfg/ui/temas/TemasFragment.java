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
            ChangeWindow.cambiarVentana(getActivity().getApplicationContext(), UsersActivity.class,true);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}