package com.example.urmindtfg.ui.buscadorEmpleados.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urmindtfg.databinding.ItemContainerUserBinding;
import com.example.urmindtfg.entitis.Psicologo;
import com.example.urmindtfg.entitis.Usuario;
import com.example.urmindtfg.model.Img;
import com.example.urmindtfg.ui.buscadorEmpleados.listeners.PsicologoListener;
import com.example.urmindtfg.ui.chat.listeners.UsersListener;

import java.util.List;

public class PsicologoAdapter extends RecyclerView.Adapter<PsicologoAdapter.UserViewHolder>{
    private final List<Psicologo> listaUsuarios;
    private final PsicologoListener usersListener;

    public PsicologoAdapter(List<Psicologo> listaUsuarios, PsicologoListener usersListener){
        this.listaUsuarios = listaUsuarios;
        this.usersListener = usersListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerUserBinding itemContainerUserBinding = ItemContainerUserBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new UserViewHolder(itemContainerUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(listaUsuarios.get(position));
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        ItemContainerUserBinding binding;

        UserViewHolder(ItemContainerUserBinding itemContainerUserBinding){
            super(itemContainerUserBinding.getRoot());
            binding = itemContainerUserBinding;
        }

        void setUserData(Psicologo usuario){
            binding.txtNombreUsuario.setText(usuario.getNombre());
            binding.txtEmailUsuario.setText(usuario.getEmail());
            binding.imgPerfil.setImageBitmap(Img.getImgBitmap(usuario.getImagen()));
            binding.getRoot().setOnClickListener(e-> usersListener.onUserClicked(usuario));
            binding.checkEmpleado.setVisibility(View.VISIBLE);
            System.out.println("AQUI: " +usuario.getEmpleadoActual());
            if(usuario.getEmpleadoActual()!=null){
                binding.checkEmpleado.setChecked(usuario.getEmpleadoActual());
            }
        }
    }
}
