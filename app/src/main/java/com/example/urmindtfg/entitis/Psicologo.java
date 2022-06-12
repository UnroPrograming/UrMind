package com.example.urmindtfg.entitis;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.urmindtfg.model.Img;

import java.util.HashMap;

public class Psicologo extends Persona{

    private String numColegiado;
    private String empresa;
    private Boolean empleadoActual;

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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Boolean getEmpleadoActual() {
        return empleadoActual;
    }

    public void setEmpleadoActual(Boolean empleadoActual) {
        this.empleadoActual = empleadoActual;
    }

    public HashMap<String, Object> toHashMap(){
        HashMap<String, Object> lista = new HashMap<>();

        lista.put(Constantes.KEY_EMAIL_USUARIOS,getEmail());
        lista.put(Constantes.KEY_NOMBRE_USUARIOS,getNombre());
        lista.put(Constantes.KEY_APELLIDO_USUARIOS,getApellidos());
        lista.put(Constantes.KEY_TELEFONO_USUARIOS, String.valueOf(getTelefono()));
        lista.put(Constantes.KEY_DNI_USUARIOS,getDNI());
        lista.put(Constantes.KEY_PROVEEDOR_USUARIOS ,getProveedor());
        lista.put(Constantes.KEY_IMG_USUARIOS,getImagen());
        lista.put(Constantes.KEY_NUM_COLEGIADO_PSICOLOGO,getNumColegiado());
        lista.put(Constantes.KEY_EMPRESA_PSICOLOGO,getEmpresa());

        return lista;
    }
}
