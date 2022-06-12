package com.example.urmindtfg.ui.cuenta;

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

        binding.btneditarCambios.setOnClickListener(e -> ChangeWindow.cambiarVentana(getContext(),EditarUsuario_.class));

        rellenarDatosUsuario();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void rellenarDatosUsuario(){
        SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE);
        ChangeWindow.recogerDatosUsuario(usuario, prefs);

        binding.imgUsuario.setImageBitmap(Img.getImgBitmap(usuario.getImagen()));
        binding.nombre.setText(usuario.getNombre());
        binding.apellidos.setText(usuario.getApellidos());
        binding.telefono.setText(String.valueOf(usuario.getTelefono()));
        binding.correo.setText(usuario.getEmail());
        binding.dni.setText(usuario.getDNI());
    }
}