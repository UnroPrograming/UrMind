package com.example.urmindtfg.entitis;

import java.util.HashMap;

public class Usuario extends Persona{

    public Usuario() {
    }

    public Usuario(String email, String nombre, String apellidos, int telefono, String DNI, String proveedor) {
        super(email, nombre, apellidos, telefono, DNI, proveedor);
    }

    public HashMap<String,Object> toHashMap(){
        HashMap<String,Object> lista = new HashMap<String,Object>();

        lista.put("email",getEmail());
        lista.put("nombre",getNombre());
        lista.put("apellidos",getApellidos());
        lista.put("telefono",getTelefono());
        lista.put("dni",getDNI());
        lista.put("proveedor",getProveedor());

        return lista;
    }
}
