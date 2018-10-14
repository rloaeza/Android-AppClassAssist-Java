package com.appclass.appclassassist.db;

import java.util.ArrayList;

public class Clase {
    public static String ordenarPorCampo = "nombre";

    private String codigo;
    private String nombre;
    private String correo;

    public Clase(String codigo, String nombre, String correo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.correo = correo;
    }

    public Clase() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
