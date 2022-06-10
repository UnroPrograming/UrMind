package com.example.urmindtfg.model;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import static com.makeramen.roundedimageview.RoundedDrawable.drawableToBitmap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Img {
    public static String getImgString(ImageView img) {
        Drawable drawable = img.getDrawable();
        Bitmap bitmap = drawableToBitmap(drawable);

        int previewWidth = bitmap.getWidth();
        int previewHeight = bitmap.getHeight();

        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static Bitmap getImgBitmap(String encoderImage){
        byte[] bytes = Base64.decode(encoderImage, 0);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }

    //Encripta la imagen y la devuelve en String
    public static String getImgString(Bitmap bitmap){
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();

        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

}
