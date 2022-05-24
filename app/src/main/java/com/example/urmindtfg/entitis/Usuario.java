package com.example.urmindtfg.entitis;

import java.util.HashMap;

public class Usuario extends Persona{

    public Usuario() {
    }

    public Usuario(String email, String nombre, String apellidos, int telefono, String DNI, String proveedor) {
        super(email, nombre, apellidos, telefono, DNI, proveedor);
    }

    public Usuario(String email, String nombre, String apellidos, int telefono, String DNI, String proveedor, String imagen) {
        super(email, nombre, apellidos, telefono, DNI, proveedor, imagen);
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

        return lista;
    }
}
