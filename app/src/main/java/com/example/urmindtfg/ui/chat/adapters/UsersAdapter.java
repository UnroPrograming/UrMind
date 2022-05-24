package com.example.urmindtfg.ui.chat.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urmindtfg.databinding.ItemContainerUserBinding;
import com.example.urmindtfg.entitis.Usuario;
import com.example.urmindtfg.model.Img;
import com.example.urmindtfg.ui.chat.listeners.UsersListener;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder>{
    private final List<Usuario> listaUsuarios;
    private final UsersListener usersListener;

    public UsersAdapter(List<Usuario> listaUsuarios, UsersListener usersListener){
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

        void setUserData(Usuario usuario){
            binding.txtNombreUsuario.setText(usuario.getNombre());
            binding.txtEmailUsuario.setText(usuario.getEmail());
            binding.imgPerfil.setImageBitmap(Img.getImgDesencriptada(usuario.getImagen()));
            binding.getRoot().setOnClickListener(e-> usersListener.onUserClicked(usuario));
        }
    }
}
