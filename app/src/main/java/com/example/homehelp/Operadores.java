package com.example.homehelp;

public class Operadores {

    String userName, Ciudad, Oficio, surl;
    int Puntaje;

    Operadores(){

    }
    public Operadores(String userName, String Ciudad, String Oficio, String surl, int puntaje) {
        this.userName = userName;
        this.Ciudad = Ciudad;
        this.Oficio = Oficio;
        this.surl = surl;
        Puntaje = puntaje;
    }

    public String getUserName() {
        return userName;
    }

    public String getCiudad() {
        return Ciudad;
    }

    public String getOficio() {
        return Oficio;
    }

    public String getSurl() {
        return surl;
    }

    public int getPuntaje() {
        return Puntaje;
    }
}
