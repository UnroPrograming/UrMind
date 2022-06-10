package com.example.urmindtfg.model;

import java.util.Date;

public class ChatMessage {

    public String senderId, reciverId, mensaje, dateTime;
    public Date dateObject;
    public String conversionId, conversionName, conversionImage;

    public ChatMessage() {
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

    public String getConversionId() {
        return conversionId;
    }

    public void setConversionId(String conversionId) {
        this.conversionId = conversionId;
    }

    public String getConversionName() {
        return conversionName;
    }

    public void setConversionName(String conversionName) {
        this.conversionName = conversionName;
    }

    public String getConversionImage() {
        return conversionImage;
    }

    public void setConversionImage(String conversionImage) {
        this.conversionImage = conversionImage;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "senderId='" + senderId + '\'' +
                ", reciverId='" + reciverId + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", dateObject=" + dateObject +
                ", conversionId='" + conversionId + '\'' +
                ", conversionName='" + conversionName + '\'' +
                ", conversionImage='" + conversionImage + '\'' +
                '}';
    }
}
