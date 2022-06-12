package com.example.urmindtfg.model;

import com.example.urmindtfg.entitis.Constantes;

import java.util.Date;
import java.util.HashMap;

public class Post {
    private String creadorId;
    private String nombreCreador;
    private String imgCreador;
    private String titulo;
    private String img;
    private String post;
    private String dateTime;
    private Date dateObject;

    public Post() {
    }

    public Post(String creadorId, String nombreCreador, String imgCreador, String titulo, String img, String post, String dateTime, Date dateObject) {
        this.creadorId = creadorId;
        this.nombreCreador = nombreCreador;
        this.imgCreador = imgCreador;
        this.titulo = titulo;
        this.img = img;
        this.post = post;
        this.dateTime = dateTime;
        this.dateObject = dateObject;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Date getDateObject() {
        return dateObject;
    }

    public void setDateObject(Date dateObject) {
        this.dateObject = dateObject;
    }

    public String getNombreCreador() {
        return nombreCreador;
    }

    public void setNombreCreador(String nombreCreador) {
        this.nombreCreador = nombreCreador;
    }

    public String getImgCreador() {
        return imgCreador;
    }

    public void setImgCreador(String imgCreador) {
        this.imgCreador = imgCreador;
    }

    public HashMap<String,Object> toHashMap(){
        HashMap<String,Object> lista = new HashMap<>();

        lista.put(Constantes.KEY_CREADOR_ID_POST, getCreadorId());
        lista.put(Constantes.KEY_TITULO_POST, getTitulo());
        lista.put(Constantes.KEY_IMG_POST, getImg());
        lista.put(Constantes.KEY_POST_POST, getPost());
        lista.put(Constantes.KEY_DATETIME,getDateObject());
        lista.put(Constantes.KEY_CREADOR_NOMBRE_POST,getNombreCreador());
        lista.put(Constantes.KEY_CREADOR_IMG_POST, getImgCreador());

        return lista;
    }
}
