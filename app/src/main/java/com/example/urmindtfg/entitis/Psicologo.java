package com.example.urmindtfg.entitis;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.urmindtfg.model.Img;

import java.util.HashMap;

public class Psicologo extends Persona{

    private String numColegiado;

    public Psicologo() { }

    public Psicologo(String email, String nombre, String apellidos, int telefono, String DNI, String proveedor, String imagen, String numColegiado) {
        super(email, nombre, apellidos, telefono, DNI, proveedor, imagen);
        this.numColegiado = numColegiado;
    }

    public String getNumColegiado() {
        return numColegiado;
    }

    public void setNumColegiado(String numColegiado) {
        this.numColegiado = numColegiado;
    }

    public HashMap<String, String> toHashMap(){
        HashMap<String, String> lista = new HashMap<String, String>();

        lista.put(Constantes.KEY_EMAIL_USUARIOS,getEmail());
        lista.put(Constantes.KEY_NOMBRE_USUARIOS,getNombre());
        lista.put(Constantes.KEY_APELLIDO_USUARIOS,getApellidos());
        lista.put(Constantes.KEY_TELEFONO_USUARIOS, String.valueOf(getTelefono()));
        lista.put(Constantes.KEY_DNI_USUARIOS,getDNI());
        lista.put(Constantes.KEY_PROVEEDOR_USUARIOS ,getProveedor());
        lista.put(Constantes.KEY_IMG_USUARIOS,getImagen());
        lista.put(Constantes.KEY_NUM_COLEGIADO_PSICOLOGO,getNumColegiado());

        return lista;
    }

    public void setPsicologoActivity(ImageView img_fotoPerfil, EditText eTxt_Nombre, EditText eTxt_Apellidos, EditText eTxt_telefono , EditText eTxt_DNI, TextView txt_email, TextView txt_provider, EditText eTxt_numColegiado){
        img_fotoPerfil.setImageBitmap(Img.getImgDesencriptada(getImagen()));
        eTxt_Nombre.setText(getNombre());
        eTxt_Apellidos.setText(getApellidos());
        eTxt_telefono.setText(String.valueOf(getTelefono()));
        eTxt_DNI.setText(getDNI());
        txt_email.setText(getEmail());
        txt_provider.setText(getProveedor());
        eTxt_numColegiado.setText(getNumColegiado());
    }

    public void actualizarPsicologo(String img_fotoPerfil, EditText eTxt_Nombre, EditText eTxt_Apellidos, EditText eTxt_telefono , EditText eTxt_DNI, TextView txt_email, TextView txt_provider, EditText eTxt_numColegiado){
        setImagen(img_fotoPerfil);
        setNombre(eTxt_Nombre.getText().toString());
        setApellidos(eTxt_Apellidos.getText().toString());
        setTelefono(Integer.parseInt(eTxt_telefono.getText().toString()));
        setDNI(eTxt_DNI.getText().toString());
        setEmail(txt_email.getText().toString());
        setProveedor(txt_provider.getText().toString());
        setNumColegiado(eTxt_numColegiado.getText().toString());
    }
}
