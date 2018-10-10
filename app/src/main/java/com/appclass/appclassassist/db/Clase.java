package com.appclass.appclassassist.db;

import java.util.ArrayList;

public class Clase {
    private String codigo;
    private String nombre;

    private ArrayList<Usuario> alumnos;
    private String correo;

    public String getCorreo() {
        return correo;
    }

    public Clase(String codigo, String nombre,  String correo, ArrayList<Usuario> alumnos) {
        this.codigo = codigo;
        this.nombre = nombre;

        this.correo = correo;
        this.alumnos = alumnos;
    }

    public ArrayList<Usuario> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(ArrayList<Usuario> alumnos) {
        this.alumnos = alumnos;
    }

    public void setCorreo(String correo) {
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


}
