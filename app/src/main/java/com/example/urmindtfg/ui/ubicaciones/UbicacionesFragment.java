package com.example.urmindtfg.ui.ubicaciones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.urmindtfg.R;
import com.example.urmindtfg.databinding.FragmentHomeBinding;
import com.example.urmindtfg.databinding.FragmentTemasBinding;
import com.example.urmindtfg.databinding.FragmentUbicacionesBinding;

public class UbicacionesFragment extends Fragment {
    private FragmentUbicacionesBinding binding;

    public static UbicacionesFragment newInstance() {
        return new UbicacionesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUbicacionesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}