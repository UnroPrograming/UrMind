package com.example.urmindtfg.chat;

import java.util.Map;

public class MensajeRecibir extends Mensaje {

    private Map hora;

    public MensajeRecibir() {
    }

    public MensajeRecibir(Map hora) {
        this.hora = hora;
    }

    public MensajeRecibir(String mensaje, String urlFoto, String nombre, String fotoPerfil, String type_mensaje, Map hora) {
        super(mensaje, urlFoto, nombre, fotoPerfil, type_mensaje);
        this.hora = hora;
    }

    public String getHora() {
        return hora.toString();
    }

    public void setHora(Map hora) {
        this.hora = hora;
    }
}
