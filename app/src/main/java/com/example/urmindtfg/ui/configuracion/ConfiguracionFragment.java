package com.example.urmindtfg.ui.configuracion;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.urmindtfg.Login_;
import com.example.urmindtfg.R;
import com.example.urmindtfg.databinding.FragmentConfiguracionBinding;
import com.example.urmindtfg.databinding.FragmentHomeBinding;
import com.example.urmindtfg.databinding.FragmentTemasBinding;
import com.example.urmindtfg.entitis.Constantes;
import com.example.urmindtfg.model.ChangeWindow;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.androidannotations.annotations.Click;

public class ConfiguracionFragment extends Fragment {
    private FragmentConfiguracionBinding binding;
    private String email, nombre;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentConfiguracionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE);
        email = prefs.getString(Constantes.KEY_EMAIL_USUARIOS,null);
        nombre = prefs.getString(Constantes.KEY_NOMBRE_USUARIOS,null);

        binding.txtEmail.setText(email);
        binding.txtNombre.setText(nombre);

        binding.btnCerrarSesion.setOnClickListener(e -> {
            //Para deslogearse de firebase
            FirebaseAuth.getInstance().signOut();

            //Para borrar la librería interna
            SharedPreferences.Editor prefsEdit = getActivity().getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE).edit();
            prefsEdit.clear();
            prefsEdit.apply();

            ChangeWindow.cambiarVentana(getContext(), Login_.class,true);
        });

        binding.btnBorrarCuenta.setOnClickListener(e -> {
            FirebaseAuth.getInstance().getCurrentUser().delete();

            //Para borrar la librería interna
            FirebaseFirestore.getInstance().collection("users").document(email);

            SharedPreferences.Editor prefsEdit = getActivity().getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE).edit();
            prefsEdit.clear();
            prefsEdit.apply();
            ChangeWindow.cambiarVentana(getContext(), Login_.class);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}