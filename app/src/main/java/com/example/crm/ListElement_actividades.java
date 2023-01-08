package com.example.crm;

import java.io.Serializable;

public class ListElement_actividades implements Serializable {
    public String color;
    public String tipo_Actividad;
    public String fecha_Vencimiento;
    public String hora_Inicio;
    public String hora_Termino;
    public String duracion;
    public String descripcion_Actividad;
    public String recordatorios;
    public String medio_Contacto;

    public ListElement_actividades(String color, String tipo_Actividad, String fecha_Vencimiento, String hora_Inicio, String hora_Termino, String duracion, String descripcion_Actividad, String recordatorios, String medio_Contacto) {
        this.color = color;
        this.tipo_Actividad = tipo_Actividad;
        this.fecha_Vencimiento = fecha_Vencimiento;
        this.hora_Inicio = hora_Inicio;
        this.hora_Termino = hora_Termino;
        this.duracion = duracion;
        this.descripcion_Actividad = descripcion_Actividad;
        this.recordatorios = recordatorios;
        this.medio_Contacto = medio_Contacto;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTipo_Actividad() {
        return tipo_Actividad;
    }

    public void setTipo_Actividad(String tipo_Actividad) {
        this.tipo_Actividad = tipo_Actividad;
    }

    public String getFecha_Vencimiento() {
        return fecha_Vencimiento;
    }

    public void setFecha_Vencimiento(String fecha_Vencimiento) {
        this.fecha_Vencimiento = fecha_Vencimiento;
    }

    public String getHora_Inicio() {
        return hora_Inicio;
    }

    public void setHora_Inicio(String hora_Inicio) {
        this.hora_Inicio = hora_Inicio;
    }

    public String getHora_Termino() {
        return hora_Termino;
    }

    public void setHora_Termino(String hora_Termino) {
        this.hora_Termino = hora_Termino;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getDescripcion_Actividad() {
        return descripcion_Actividad;
    }

    public void setDescripcion_Actividad(String descripcion_Actividad) {
        this.descripcion_Actividad = descripcion_Actividad;
    }

    public String getRecordatorios() {
        return recordatorios;
    }

    public void setRecordatorios(String recordatorios) {
        this.recordatorios = recordatorios;
    }

    public String getMedio_contacto() {
        return medio_Contacto;
    }

    public void setMedio_contacto(String medio_contacto) {
        this.medio_Contacto = medio_contacto;
    }
}
