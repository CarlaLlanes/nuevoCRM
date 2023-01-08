package com.example.crm;

import java.io.Serializable;

public class ListElement_equipo_ventas implements Serializable {
    public String color;
    public String nombre_Completo;
    public String email;
    public String numero_Telefono;

    public ListElement_equipo_ventas(String color, String nombre_Completo, String email, String numero_Telefono) {
        this.color = color;
        this.nombre_Completo = nombre_Completo;
        this.email = email;
        this.numero_Telefono=numero_Telefono;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNombre_Completo() {
        return nombre_Completo;
    }

    public void setNombre_Completo(String nombre_Completo) {
        this.nombre_Completo = nombre_Completo;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumero_Telefono() {
        return numero_Telefono;
    }

    public void setNumero_Telefono(String numero_Telefono) {
        this.numero_Telefono = numero_Telefono;
    }
}
