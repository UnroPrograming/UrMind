package com.example.urmindtfg.ui.post.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urmindtfg.databinding.ItemContainerPostImageBinding;
import com.example.urmindtfg.model.Img;
import com.example.urmindtfg.model.Post;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{

    private final List<Post> listaPost;

    public PostAdapter(List<Post> listaPost) {
        this.listaPost = listaPost;
    }

    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Retornamos un objeto de tipo RecirclerView.ViewHolder
        return new PostAdapter.PostViewHolder(
                ItemContainerPostImageBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {
        //Llamamos al método que implementa los datos en el ReciclerView
        holder.setData(listaPost.get(position));
    }

    @Override
    public int getItemCount() {
        return listaPost.size();
    }

    //Clase que funciona como reciclerView
    class PostViewHolder extends RecyclerView.ViewHolder {
        ItemContainerPostImageBinding binding;

        //Constructor en el que se implementa la vista
        PostViewHolder(ItemContainerPostImageBinding itemContainerPostImageBinding){
            super(itemContainerPostImageBinding.getRoot());
            binding = itemContainerPostImageBinding;
        }

        //Le asignamos los datos al recyclerView
        void setData(Post post){
            binding.imgPerfil.setImageBitmap(Img.getImgBitmap(post.getImgCreador()));
            binding.txtEmailUsuario.setText(post.getCreadorId());
            binding.txtNombreUsuario.setText(post.getNombreCreador());
            binding.imgPost.setImageBitmap(Img.getImgBitmap(post.getImg()));
            binding.txtTitulo.setText(post.getTitulo());
            binding.txtPost.setText(post.getPost());
            binding.txtFecha.setText(getModoLecturaDateTime(post.getDateObject()));
        }
    }

    private String getModoLecturaDateTime(Date date){
        return new SimpleDateFormat("MMMM dd, yyyy -hh:mm a", Locale.getDefault()).format(date);
    }
}
