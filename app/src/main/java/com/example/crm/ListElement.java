package com.example.crm;

import java.io.Serializable;

public class ListElement implements Serializable {
    public String color;
    public String nombre_Completo;
    public String email;
    public String numero_Telefono;
    public String posible_Ganancia;
    public String estado;

    public ListElement(String color, String nombre_Completo, String email,String numero_Telefono,String posible_Ganancia,String estado) {
        this.color = color;
        this.nombre_Completo = nombre_Completo;
        this.email = email;
        this.numero_Telefono=numero_Telefono;
        this.posible_Ganancia=posible_Ganancia;
        this.estado = estado;
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
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPosible_Ganancia() {
        return posible_Ganancia;
    }

    public void setPosible_Ganancia(String posible_Ganancia) {
        this.posible_Ganancia = posible_Ganancia;
    }
}
