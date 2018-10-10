package com.appclass.appclassassist.db;

public class Usuario {

    private String idControl;
    private String nombre;
    private String aPaterno;
    private String aMaterno;
    private String correo;
    private String btMac;

    public Usuario() {
    }


    public Usuario(String idControl, String nombre, String apellidos, String correo, String btMac) {
        this.idControl = idControl;
        this.nombre = nombre;
        this.correo = correo;
        this.btMac = btMac;

        String[] apellido = apellidos.split(" ");
        this.aPaterno = apellido[0];
        this.aMaterno = apellido.length==2?apellido[1]:"";
    }

    public Usuario(String idControl, String nombre, String aPaterno, String aMaterno, String correo, String btMac) {
        this.idControl = idControl;
        this.nombre = nombre;
        this.aPaterno = aPaterno;
        this.aMaterno = aMaterno;
        this.correo = correo;
        this.btMac = btMac;
    }

    public String getIdControl() {
        return idControl;
    }

    public void setIdControl(String idControl) {
        this.idControl = idControl;
    }

    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getaPaterno() {
        return aPaterno;
    }

    public void setaPaterno(String aPaterno) {
        this.aPaterno = aPaterno;
    }

    public String getaMaterno() {
        return aMaterno;
    }

    public void setaMaterno(String aMaterno) {
        this.aMaterno = aMaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getBtMac() { return btMac; }

    public void setBtMac(String btMac) { this.btMac = btMac; }

}
