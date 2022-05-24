package com.example.urmindtfg.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Img {

    public static Bitmap getImgDesencriptada(String encoderImage){
        byte[] bytes = Base64.decode(encoderImage, 0);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }

    //Encripta la imagen y la devuelve en String
    public static String setImgEncriptada(Bitmap bitmap){
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();

        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}
