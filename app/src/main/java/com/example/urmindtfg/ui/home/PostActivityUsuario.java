package com.example.urmindtfg.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.urmindtfg.R;
import com.example.urmindtfg.databinding.ActivityPostUsuarioBinding;
import com.example.urmindtfg.entitis.Constantes;
import com.example.urmindtfg.entitis.Usuario;
import com.example.urmindtfg.model.ChangeWindow;
import com.example.urmindtfg.model.Post;
import com.example.urmindtfg.ui.home.adapters.PostAdapter;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class PostActivityUsuario extends AppCompatActivity {

    private ActivityPostUsuarioBinding binding;
    private List<Post> listaPosts;
    private PostAdapter postAdapter;
    private Usuario usuarioRecivido;
    private String currentUserId;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        listenPosts();
    }

    private void init() {
        //Le asignamos el adaptador al RecyclerView
        listaPosts = new ArrayList<>();
        postAdapter = new PostAdapter(listaPosts);
        binding.recyclerViewPost.setAdapter(postAdapter);

        //Cargamos el usuario
        usuarioRecivido = (Usuario) getIntent().getSerializableExtra(Constantes.KEY_USUARIO);
        currentUserId = usuarioRecivido.getEmail();

        //Insatnciamos la base de datos
        database = FirebaseFirestore.getInstance();

        //Volver atrás
        binding.imgBack.setOnClickListener(e-> onBackPressed());
    }

    private void listenPosts(){
        database.collection(Constantes.KEY_TABLA_POST)
                .whereEqualTo(Constantes.KEY_CREADOR_POST, currentUserId)
                .addSnapshotListener(eventListener);
    }
    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            int count = listaPosts.size();
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    String creadorId = documentChange.getDocument().getString(Constantes.KEY_CREADOR_POST);
                    String titulo = documentChange.getDocument().getString(Constantes.KEY_TITULO_POST);
                    String img = documentChange.getDocument().getString(Constantes.KEY_IMG_POST);
                    String contenido = documentChange.getDocument().getString(Constantes.KEY_POST_POST);
                    Date date = documentChange.getDocument().getDate(Constantes.KEY_DATETIME);

                    Post post = new Post();
                    post.setCreadorId(creadorId);
                    post.setTitulo(titulo);
                    post.setImg(img);
                    post.setPost(contenido);
                    post.setDateObject(date);

                    listaPosts.add(post);

                }else if(documentChange.getType() == DocumentChange.Type.MODIFIED){
                    for(int i = 0; i < listaPosts.size(); i++){
                        String creadorId = documentChange.getDocument().getString(Constantes.KEY_CREADOR_POST);

                        if(listaPosts.get(i).getCreadorId().equals(creadorId)){
                            String titulo = documentChange.getDocument().getString(Constantes.KEY_TITULO_POST);
                            String img = documentChange.getDocument().getString(Constantes.KEY_IMG_POST);
                            String contenido = documentChange.getDocument().getString(Constantes.KEY_POST_POST);

                            listaPosts.get(i).setTitulo(titulo);
                            listaPosts.get(i).setImg(img);
                            listaPosts.get(i).setPost(contenido);
                            break;
                        }
                    }
                }
            }
            try {
                Collections.sort(listaPosts,(obj1, obj2) -> obj2.getDateObject().compareTo(obj1.getDateObject()));
                postAdapter.notifyDataSetChanged();
                binding.recyclerViewPost.smoothScrollToPosition(0);
                binding.recyclerViewPost.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
}