package com.example.urmindtfg.ui.chat.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urmindtfg.databinding.ItemContainerRecentConversionBinding;
import com.example.urmindtfg.entitis.Usuario;
import com.example.urmindtfg.model.ChatMessage;
import com.example.urmindtfg.model.Img;
import com.example.urmindtfg.ui.chat.listeners.ConversacionListener;

import java.util.List;

public class RecentConversationsAdapter extends RecyclerView.Adapter<RecentConversationsAdapter.ConversionViewHolder>{

    private final List<ChatMessage> chatMessages;
    private final ConversacionListener conversacionListener;

    public RecentConversationsAdapter(List<ChatMessage> chatMessages, ConversacionListener conversacionListener) {
        this.chatMessages = chatMessages;
        this.conversacionListener = conversacionListener;
    }

    @NonNull
    @Override
    public ConversionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Retornamos un objeto de tipo RecirclerView.ViewHolder
        return new ConversionViewHolder(
                ItemContainerRecentConversionBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ConversionViewHolder holder, int position) {
        //Llamamos al método que implementa los datos en el ReciclerView
        holder.setData(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    //Clase que funciona como reciclerView
    class ConversionViewHolder extends RecyclerView.ViewHolder {
        ItemContainerRecentConversionBinding binding;

        //Constructor en el que se implementa la vista
        ConversionViewHolder(ItemContainerRecentConversionBinding itemContainerRecentConversionBinding){
            super(itemContainerRecentConversionBinding.getRoot());
            binding = itemContainerRecentConversionBinding;
        }

        //Le asignamos los datos al recyclerView
        void setData(ChatMessage chatMessage){
            binding.imgPerfil.setImageBitmap(Img.getImgBitmap(chatMessage.conversionImage));
            binding.txtNombreUsuario.setText(chatMessage.conversionName);
            binding.txtRecentMessage.setText(chatMessage.getMensaje());

            //Para que cuando hagas click acceda a ese usuario
            binding.getRoot().setOnClickListener(v->{
                Usuario usuario = new Usuario();
                usuario.setEmail(chatMessage.getConversionId());
                usuario.setNombre(chatMessage.getConversionName());
                usuario.setImagen(chatMessage.getConversionImage());
                conversacionListener.onConversionClicked(usuario);
            });
        }
    }
}
