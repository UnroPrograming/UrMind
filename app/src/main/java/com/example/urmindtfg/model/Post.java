package com.example.urmindtfg.model;

import com.example.urmindtfg.entitis.Constantes;

import java.util.HashMap;

public class Post {
    private String creadorId;
    private String titulo;
    private String img;
    private String post;

    public Post() {
    }

    public Post(String creadorId, String titulo, String img, String post) {
        this.creadorId = creadorId;
        this.titulo = titulo;
        this.img = img;
        this.post = post;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getCreadorId() {
        return creadorId;
    }

    public void setCreadorId(String creadorId) {
        this.creadorId = creadorId;
    }

    public HashMap<String,String> toHashMap(){
        HashMap<String,String> lista = new HashMap<>();

        lista.put(Constantes.KEY_CREADOR_POST, getCreadorId());
        lista.put(Constantes.KEY_TITULO_POST, getPost());
        lista.put(Constantes.KEY_IMG_POST, getImg());
        lista.put(Constantes.KEY_POST_POST, getPost());

        return lista;
    }
}
