package com.example.urmindtfg.entitis;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.urmindtfg.model.Img;

import java.util.HashMap;

public class Usuario extends Persona{
    private String tipo;
    public Usuario() {
    }

    public Usuario(String email, String nombre, String apellidos, int telefono, String DNI, String proveedor) {
        super(email, nombre, apellidos, telefono, DNI, proveedor);
    }

    public Usuario(String email, String nombre, String apellidos, int telefono, String DNI, String proveedor, String imagen) {
        super(email, nombre, apellidos, telefono, DNI, proveedor, imagen);
    }

    public Usuario(String email, String nombre, String apellidos, int telefono, String DNI, String proveedor, String imagen, String tipo) {
        super(email, nombre, apellidos, telefono, DNI, proveedor, imagen);
        this.tipo = tipo;
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
        lista.put(Constantes.KEY_TIPO_USUARIO, getTipo());

        return lista;
    }

    public void setUsuarioActivity(ImageView img_fotoPerfil, EditText eTxt_Nombre, EditText eTxt_Apellidos, EditText eTxt_telefono , EditText eTxt_DNI, TextView txt_email, TextView txt_provider){
        img_fotoPerfil.setImageBitmap(Img.getImgBitmap(getImagen()));
        eTxt_Nombre.setText(getNombre());
        eTxt_Apellidos.setText(getApellidos());
        eTxt_telefono.setText(String.valueOf(getTelefono()));
        eTxt_DNI.setText(getDNI());
        txt_email.setText(getEmail());
        txt_provider.setText(getProveedor());
    }

    public void actualizarUsuario(String img_fotoPerfil, EditText eTxt_Nombre, EditText eTxt_Apellidos, EditText eTxt_telefono , EditText eTxt_DNI, TextView txt_email, TextView txt_provider){
        setImagen(img_fotoPerfil);
        setNombre(eTxt_Nombre.getText().toString());
        setApellidos(eTxt_Apellidos.getText().toString());
        setTelefono(Integer.parseInt(eTxt_telefono.getText().toString()));
        setDNI(eTxt_DNI.getText().toString());
        setEmail(txt_email.getText().toString());
        setProveedor(txt_provider.getText().toString());
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
