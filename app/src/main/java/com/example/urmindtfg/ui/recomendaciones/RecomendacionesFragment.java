package com.example.urmindtfg.ui.recomendaciones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.urmindtfg.R;
import com.example.urmindtfg.databinding.FragmentHomeBinding;
import com.example.urmindtfg.databinding.FragmentRecomendacionesBinding;
import com.example.urmindtfg.databinding.FragmentTemasBinding;

public class RecomendacionesFragment extends Fragment {
    private @NonNull FragmentRecomendacionesBinding binding;

    public static RecomendacionesFragment newInstance() {
        return new RecomendacionesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRecomendacionesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}