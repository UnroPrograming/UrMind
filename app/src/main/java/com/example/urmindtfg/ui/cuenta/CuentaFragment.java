package com.example.urmindtfg.ui.cuenta;

import static android.provider.Settings.System.getString;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.urmindtfg.R;
import com.example.urmindtfg.databinding.FragmentCuentaBinding;
import com.example.urmindtfg.databinding.FragmentHomeBinding;
import com.example.urmindtfg.databinding.FragmentTemasBinding;
import com.example.urmindtfg.entitis.Constantes;
import com.example.urmindtfg.entitis.Usuario;
import com.example.urmindtfg.model.ChangeWindow;
import com.example.urmindtfg.model.Img;

public class CuentaFragment extends Fragment {
    private @NonNull FragmentCuentaBinding binding;
    private Usuario usuario;

    public static CuentaFragment newInstance() {
        return new CuentaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentCuentaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        usuario = new Usuario();

        rellenarDatos();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void rellenarDatos(){
        SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE);
        ChangeWindow.recogerDatosUsuario(usuario, prefs);

        binding.imgusuario.setImageBitmap(Img.getImgDesencriptada(usuario.getImagen()));
        binding.nombre.setText(usuario.getNombre());
        binding.apellidos.setText(usuario.getApellidos());
        binding.telefono.setText(String.valueOf(usuario.getTelefono()));
        binding.correo.setText(usuario.getEmail());
    }
}