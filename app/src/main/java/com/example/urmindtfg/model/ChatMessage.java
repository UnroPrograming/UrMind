package com.example.urmindtfg.model;

import java.util.Date;

public class ChatMessage {

    public String senderId, reciverId, mensaje, dateTime;
    public Date dateObject;

    public ChatMessage() {
    }

    public ChatMessage(String senderId, String reciverId, String mensaje, String dateTime) {
        this.senderId = senderId;
        this.reciverId = reciverId;
        this.mensaje = mensaje;
        this.dateTime = dateTime;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReciverId() {
        return reciverId;
    }

    public void setReciverId(String reciverId) {
        this.reciverId = reciverId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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

    @Override
    public String toString() {
        return "ChatMessage{" +
                "senderId='" + senderId + '\'' +
                ", reciverId='" + reciverId + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }
}
