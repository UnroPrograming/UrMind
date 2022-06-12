package com.example.urmindtfg.entitis;

import java.util.HashMap;

public class Empresa {

    private String email,
    nombre,
    telefono,
    CIF,
    coordenadas,
    img;

    public Empresa(String email, String nombre, String telefono, String CIF, String coordenadas, String img) {
        this.email = email;
        this.nombre = nombre;
        this.telefono = telefono;
        this.CIF = CIF;
        this.coordenadas = coordenadas;
        this.img = img;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCIF() {
        return CIF;
    }

    public void setCIF(String CIF) {
        this.CIF = CIF;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public HashMap<String, String> toHashMap(){
        HashMap<String,String> lista = new HashMap<>();
        lista.put(Constantes.KEY_NOMBRE_EMPRESA,nombre);
        lista.put(Constantes.KEY_EMAIL_EMPRESA, email);
        lista.put(Constantes.KEY_TELEFONO_EMPRESA,telefono);
        lista.put(Constantes.KEY_CIF_EMPRESA, CIF);
        lista.put(Constantes.KEY_COORDENADAS_EMPRESA, coordenadas);
        lista.put(Constantes.KEY_IMG_EMPRESA, img);
        return lista;
    }
}
