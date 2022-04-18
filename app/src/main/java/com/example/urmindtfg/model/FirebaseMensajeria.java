package com.example.urmindtfg.model;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMensajeria extends FirebaseMessagingService {

    //Cuando recibimos una notificación y tenemos la app abierta aparece en primer plano
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        //Hilo que nos permite crear una notificación
        Looper.prepare();

        Handler handler = new Handler();
        handler.post(() -> showToast(message.getNotification().getTitle()));

        Looper.loop();
    }

    private void showToast(String contenido){
        Toast.makeText(getBaseContext(), contenido, Toast.LENGTH_LONG).show();
    }
}
