package com.example.urmindtfg.ui.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.urmindtfg.R;
import com.example.urmindtfg.databinding.ActivityChatBinding;
import com.example.urmindtfg.entitis.Constantes;
import com.example.urmindtfg.entitis.Usuario;
import com.example.urmindtfg.model.ChatMessage;
import com.example.urmindtfg.model.Img;
import com.example.urmindtfg.ui.chat.adapters.ChatAdapter;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private Usuario usuarioRecivido;
    private List<ChatMessage> listaMensajes;
    private ChatAdapter chatAdapter;
    private FirebaseFirestore database;
    private SharedPreferences prefs;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setListeners();
        cargarRecividos();
        init();
        listenMessages();
    }

    private void init(){
        listaMensajes = new ArrayList<>();

        chatAdapter = new ChatAdapter(
                listaMensajes,
                Img.getImgBitmap(usuarioRecivido.getImagen()),
                currentUserId
        );

        binding.recycleViewChat.setAdapter(chatAdapter);
        database = FirebaseFirestore.getInstance();
    }

    //Subimos el mensaje a la base de datos
    private void sendMensaje(){
        HashMap<String, Object> mensaje = new HashMap<>();
        mensaje.put(Constantes.KEY_SENDER_ID, currentUserId);
        mensaje.put(Constantes.KEY_RECEIVER_ID,usuarioRecivido.getEmail());
        mensaje.put(Constantes.KEY_MENSAJE, binding.eTxtMensaje.getText().toString());
        mensaje.put(Constantes.KEY_DATETIME, new Date());

        database.collection(Constantes.KEY_TABLA_CHAT).add(mensaje);
        binding.eTxtMensaje.setText(null);
    }

    //Espera hasta que hay un cambio en la base de datos
    private void listenMessages(){
        database.collection(Constantes.KEY_TABLA_CHAT)
                .whereEqualTo(Constantes.KEY_SENDER_ID, currentUserId)
                .whereEqualTo(Constantes.KEY_RECEIVER_ID, usuarioRecivido.getEmail())
                .addSnapshotListener(eventListener);

        database.collection(Constantes.KEY_TABLA_CHAT)
                .whereEqualTo(Constantes.KEY_SENDER_ID, usuarioRecivido.getEmail())
                .whereEqualTo(Constantes.KEY_RECEIVER_ID, currentUserId)
                .addSnapshotListener(eventListener);
    }

    //Mostrar los mensajes por pantalla
    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
      if(error != null) {
          return;
      }
      if(value != null){
          int count = listaMensajes.size();
          for(DocumentChange documentChange : value.getDocumentChanges()){
                if(documentChange.getType() == DocumentChange.Type.ADDED){
                    //Introducimos los mensajes en una lista de mensajes
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setSenderId(documentChange.getDocument().getString(Constantes.KEY_SENDER_ID));
                    chatMessage.setReciverId(documentChange.getDocument().getString(Constantes.KEY_RECEIVER_ID));
                    chatMessage.setMensaje(documentChange.getDocument().getString(Constantes.KEY_MENSAJE));
                    chatMessage.setReciverId(documentChange.getDocument().getString(Constantes.KEY_RECEIVER_ID));
                    chatMessage.setDateTime(getModoLecturaDateTime(documentChange.getDocument().getDate(Constantes.KEY_DATETIME)));
                    chatMessage.setDateObject(documentChange.getDocument().getDate(Constantes.KEY_DATETIME));

                    listaMensajes.add(chatMessage);
                }
          }
          Collections.sort(listaMensajes,(obj1, obj2) -> obj1.getDateObject().compareTo(obj2.dateObject));
          if(count == 0){
              chatAdapter.notifyDataSetChanged();
          }else{
              chatAdapter.notifyItemRangeInserted(listaMensajes.size(), listaMensajes.size());
              binding.recycleViewChat.smoothScrollToPosition(listaMensajes.size()-1);
          }
          binding.recycleViewChat.setVisibility(View.VISIBLE);
      }
        binding.progressBar.setVisibility(View.GONE);
    };

    private void cargarRecividos(){
        //Cargamos el usuario
        usuarioRecivido = (Usuario) getIntent().getSerializableExtra(Constantes.KEY_USUARIO);
        binding.txtNombreUsuario.setText(usuarioRecivido.getNombre());

        prefs = getSharedPreferences(getString(R.string.libreria_clave_valor), Context.MODE_PRIVATE);
        currentUserId = prefs.getString("email",null);
    }

    private void setListeners(){
        //Volver atrás
        binding.imgBack.setOnClickListener(e-> onBackPressed());

        //Enviar mensaje
        binding.layoutSend.setOnClickListener(e->sendMensaje());
    }

    private String getModoLecturaDateTime(Date date){
        return new SimpleDateFormat("MMMM dd, yyyy -hh:mm a", Locale.getDefault()).format(date);
    }
}