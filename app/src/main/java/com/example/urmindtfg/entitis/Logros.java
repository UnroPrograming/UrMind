package com.example.urmindtfg.entitis;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Logros implements Serializable {

    private List<Boolean> listaBoolDiarios;
    private List<Boolean>  listaBoolSemanales;

    public Logros(List<Boolean> listaBoolDiarios, List<Boolean> listaBoolSemanales) {
        this.listaBoolDiarios = listaBoolDiarios;
        this.listaBoolSemanales = listaBoolSemanales;
    }

    public List<Boolean> getListaBoolDiarios() {
        return listaBoolDiarios;
    }

    public void setListaBoolDiarios(List<Boolean> listaBoolDiarios) {
        this.listaBoolDiarios = listaBoolDiarios;
    }

    public List<Boolean> getListaBoolSemanales() {
        return listaBoolSemanales;
    }

    public void setListaBoolSemanales(List<Boolean> listaBoolSemanales) {
        this.listaBoolSemanales = listaBoolSemanales;
    }
}
