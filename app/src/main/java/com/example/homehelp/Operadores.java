package com.example.homehelp;

public class Operadores {

    String userName, Ciudad, Oficio, foto_perfil;
    int Puntaje;

    Operadores(){

    }
    public Operadores(String userName, String Ciudad, String Oficio, String foto_perfil, int puntaje) {
        this.userName = userName;
        this.Ciudad = Ciudad;
        this.Oficio = Oficio;
        this.foto_perfil = foto_perfil;
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

    public String getFoto_perfil() {
        return foto_perfil;
    }

    public int getPuntaje() {
        return Puntaje;
    }
}
