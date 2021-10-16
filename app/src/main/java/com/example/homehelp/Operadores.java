package com.example.homehelp;

public class Operadores {
    String userName, Ciudad, Oficio, imagen;
    int Puntaje;

    Operadores(){

    }
    public Operadores(String userName, String Ciudad, String Oficio, String imagen, int puntaje) {
        this.userName = userName;
        this.Ciudad = Ciudad;
        this.Oficio = Oficio;
        this.imagen = imagen;
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

    public String getImagen() {
        return imagen;
    }

    public int getPuntaje() {
        return Puntaje;
    }
}

