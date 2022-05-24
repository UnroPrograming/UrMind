package com.example.urmindtfg.ui.chat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urmindtfg.databinding.ItemContainerUserBinding;
import com.example.urmindtfg.entitis.Usuario;

import java.util.Base64;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder>{
    private final List<Usuario> listaUsuarios;

    public UsersAdapter(List<Usuario> listaUsuarios){
        this.listaUsuarios = listaUsuarios;
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
            //binding.imgPerfil.setImageBitmap(getUserImage(usuario.imagen));
        }
    }

//    private Bitmap getUserImage(String encoderImage){
//        byte[] bytes = Base64.decode(encoderImage, 0);
//        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//    }
}
