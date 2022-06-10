package com.example.urmindtfg.ui.chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.urmindtfg.R;
import com.example.urmindtfg.databinding.FragmentChatBinding;
import com.example.urmindtfg.entitis.Constantes;
import com.example.urmindtfg.entitis.Usuario;
import com.example.urmindtfg.model.ChangeWindow;
import com.example.urmindtfg.model.ChatMessage;
import com.example.urmindtfg.ui.chat.adapters.RecentConversationsAdapter;
import com.example.urmindtfg.ui.chat.listeners.ConversacionListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatFragment extends Fragment implements ConversacionListener {

    private FragmentChatBinding binding;
    private List<ChatMessage> listaConversaciones;
    private RecentConversationsAdapter conversationsAdapter;
    private SharedPreferences prefs;
    private String currentUserId;
    private FirebaseFirestore database;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        init();
        setListeners();
        listenConversations();
        return root;
    }

    private void init() {
        //Le asignamos el adaptador al RecyclerView
        listaConversaciones = new ArrayList<>();
        conversationsAdapter = new RecentConversationsAdapter(listaConversaciones, this);
        binding.recyclerViewConversaciones.setAdapter(conversationsAdapter);

        //Obtenemos el currentId
        prefs = getActivity().getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE);
        currentUserId = prefs.getString(Constantes.KEY_EMAIL_USUARIOS,null);

        //Insatnciamos la base de datos
        database = FirebaseFirestore.getInstance();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setListeners() {
        binding.btnNewChat.setOnClickListener(e->{
            ChangeWindow.cambiarVentana(getActivity().getApplicationContext(), UsersActivity.class,true);
        });
    }

    private void listenConversations(){
        database.collection(Constantes.KEY_TABLA_CONVERSACIONES)
                .whereEqualTo(Constantes.KEY_SENDER_ID, currentUserId)
                .addSnapshotListener(eventListener);
        database.collection(Constantes.KEY_TABLA_CONVERSACIONES)
                .whereEqualTo(Constantes.KEY_RECEIVER_ID, currentUserId)
                .addSnapshotListener(eventListener);
    }
    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            int count = listaConversaciones.size();
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    String senderId = documentChange.getDocument().getString(Constantes.KEY_SENDER_ID);
                    String receiverId = documentChange.getDocument().getString(Constantes.KEY_RECEIVER_ID);

                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setSenderId(senderId);
                    chatMessage.setReciverId(receiverId);

                    if(currentUserId.equals(senderId)){
                        chatMessage.conversionId = documentChange.getDocument().getString(Constantes.KEY_RECEIVER_ID);
                        chatMessage.conversionName = documentChange.getDocument().getString(Constantes.KEY_RECEIVER_NOMBRE);
                        chatMessage.conversionImage = documentChange.getDocument().getString(Constantes.KEY_RECEIVER_IMG);
                    }else {
                        chatMessage.conversionId = documentChange.getDocument().getString(Constantes.KEY_SENDER_ID);
                        chatMessage.conversionName = documentChange.getDocument().getString(Constantes.KEY_SENDER_NOMBRE);
                        chatMessage.conversionImage = documentChange.getDocument().getString(Constantes.KEY_SENDER_IMG);
                    }

                    chatMessage.mensaje = documentChange.getDocument().getString(Constantes.KEY_LAST_MENSAJE);
                    chatMessage.dateObject = documentChange.getDocument().getDate(Constantes.KEY_DATETIME);

                    listaConversaciones.add(chatMessage);
                }else if(documentChange.getType() == DocumentChange.Type.MODIFIED){
                    for(int i =0; i < listaConversaciones.size(); i++){
                        String senderId = documentChange.getDocument().getString(Constantes.KEY_SENDER_ID);
                        String receiverId = documentChange.getDocument().getString(Constantes.KEY_RECEIVER_ID);
                        if(listaConversaciones.get(i).getSenderId().equals(senderId) && listaConversaciones.get(i).getReciverId().equals(receiverId)){
                            listaConversaciones.get(i).setMensaje(documentChange.getDocument().getString(Constantes.KEY_LAST_MENSAJE));
                            listaConversaciones.get(i).setDateObject(documentChange.getDocument().getDate(Constantes.KEY_DATETIME));
                            break;
                        }
                    }
                }
            }
            try {
                Collections.sort(listaConversaciones, (obj1, obj2) -> obj2.dateObject.compareTo(obj1.dateObject));
                conversationsAdapter.notifyDataSetChanged();
                binding.recyclerViewConversaciones.smoothScrollToPosition(0);
                binding.recyclerViewConversaciones.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onConversionClicked(Usuario usuario) {
        ChangeWindow.cambiarVentana(getActivity().getApplicationContext(), usuario, ChatActivity.class, true);
    }
}