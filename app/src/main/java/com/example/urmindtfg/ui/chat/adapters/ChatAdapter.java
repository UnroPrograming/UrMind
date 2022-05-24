package com.example.urmindtfg.ui.chat.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urmindtfg.databinding.ItemContainerReceivedMessageBinding;
import com.example.urmindtfg.databinding.ItemContainerSentMessageBinding;
import com.example.urmindtfg.model.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<ChatMessage> listaMensajes;
    private Bitmap receiverProfileImage;
    private final String senderId;

    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;

    public ChatAdapter(List<ChatMessage> listaMensajes, Bitmap receiverProfileImage, String senderId) {
        this.listaMensajes = listaMensajes;
        this.receiverProfileImage = receiverProfileImage;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Llamamos a una clase u a otra dependiendo de si recivimos o enviamos un mensaje
        if(viewType == VIEW_TYPE_SENT) {
            return new SentMessageViewHolder(
                    ItemContainerSentMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }else{
            return new ReceivedMessageViewHolder(
                    ItemContainerReceivedMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == VIEW_TYPE_SENT) {
            ((SentMessageViewHolder) holder).setData(listaMensajes.get(position));
        }else {
            ((ReceivedMessageViewHolder) holder).setData(listaMensajes.get(position), receiverProfileImage);
        }
    }

    @Override
    public int getItemCount() {
        return listaMensajes.size();
    }


    //Comprobamos si el mensaje es del que recive o del que manda
    @Override
    public int getItemViewType(int position) {
        if(listaMensajes.get(position).senderId.equals(senderId)){
            return VIEW_TYPE_SENT;
        }else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    //Clase que envia los mensajes
    static class SentMessageViewHolder extends RecyclerView.ViewHolder{

        private final ItemContainerSentMessageBinding binding;

        SentMessageViewHolder(ItemContainerSentMessageBinding itemContainerSentMessageBinding){
            super(itemContainerSentMessageBinding.getRoot());
            binding = itemContainerSentMessageBinding;
        }

        void setData(ChatMessage chatMessage) {
            binding.txtMensaje.setText(chatMessage.getMensaje());
            binding.txtDateTime.setText(chatMessage.getDateTime());
        }
    }

    //Clase que recepciona los mensajes
    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {

        private final ItemContainerReceivedMessageBinding binding;

        ReceivedMessageViewHolder(ItemContainerReceivedMessageBinding itemContainerReceivedMessageBinding){
            super(itemContainerReceivedMessageBinding.getRoot());
            binding = itemContainerReceivedMessageBinding;
        }

        void setData(ChatMessage chatMessage, Bitmap receiverProfileImage){
            binding.txtMensaje.setText(chatMessage.getMensaje());
            binding.txtDateTime.setText(chatMessage.getDateTime());
            binding.imgUsuario.setImageBitmap(receiverProfileImage);
        }
    }
}
