package com.example.crm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Descripcion_actividad_Activity extends AppCompatActivity {
    TextView tipo_Actividad,hora_Inicio,hora_Termino,duracion,descripcion_Actividad,medio_Contacto,fecha_vencimiento,recordatorios;
    ImageButton btn_atras,btn_actualizar,btn_eliminar;
    //Para el accionbar
    Toolbar toolbar1;
    //Shared preference para el cambio de variable
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_actividad);
        relacionarvista();
        //contiene todos los elementos elegidos
        ListElement_actividades element=(ListElement_actividades) getIntent().getSerializableExtra("ListElement_actividades");

        //Colocar los elementos
        tipo_Actividad.setText(element.getTipo_Actividad());
        hora_Inicio.setText(element.getHora_Inicio());
        hora_Termino.setText(element.getHora_Termino());
        duracion.setText(element.getDuracion());
        descripcion_Actividad.setText(element.getDescripcion_Actividad());
        medio_Contacto.setText(element.getMedio_contacto());
        recordatorios.setText(element.getRecordatorios());
        fecha_vencimiento.setText(element.getFecha_Vencimiento());
        //color
        fecha_vencimiento.setTextColor(Color.GREEN);
        //Aparecer el accionbar esto para poder ser transparente
        setSupportActionBar(toolbar1);
        //Encontrar la sesion iniciada
        preferences=getSharedPreferences("sesion", Context.MODE_PRIVATE);
        editor=preferences.edit();
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresar();
            }
        });
        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizar_actividad();
            }
        });
        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar_actividad();
            }
        });
    }
    public void relacionarvista(){
        //relacionar elementos
        tipo_Actividad=findViewById(R.id.tipo_Actividad);
        hora_Inicio=findViewById(R.id.hora_Inicio);
        hora_Termino=findViewById(R.id.hora_Termino);
        duracion=findViewById(R.id.duracion);
        descripcion_Actividad=findViewById(R.id.descripcion_Actividad);
        medio_Contacto=findViewById(R.id.medio_Contacto);
        fecha_vencimiento=findViewById(R.id.fecha_vencimiento);
        recordatorios=findViewById(R.id.recordatorios);
        btn_actualizar=findViewById(R.id.btn_actualizar);
        btn_eliminar=findViewById(R.id.btn_eliminar);
    }
    //menu para cerrar sesion  y salir, primero mandarlo a traer
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;}
    @Override
    //Despues mostrar las opciones que tiene en el menu
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            // si cierra sesion la variable cambia a falsa y se guarda
            case R.id.itemCerrarS:
                editor.putBoolean("variable",false);
                editor.apply();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            case R.id.itemSalir:
                //solo sale de la aplicacion
                finishAffinity();}
        return super.onOptionsItemSelected(item);
    }
    //Metodo regresa al menu principal
    public void regresar(){
        Intent recibir = getIntent();
        String datounico = recibir.getStringExtra("ventana");
        String ventana = datounico;

        if(ventana=="actividades"){
            Intent intent1 = new Intent(getApplicationContext(),Actividad_Activity.class);
            startActivity(intent1);
            finish();
        }else{
            Intent intent1 = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent1);
            finish();
        }
    }

    public void actualizar_actividad(){
        //esta asi porque quedo pendiente la API
//        String correounico= email1.getText().toString();
//        Intent correo = new Intent(getApplicationContext(),Actualizar_cliente_Activity.class);
//        correo.putExtra("correo",correounico);
//        startActivity(correo);
//        finish();
    }
    public void eliminar_actividad(){

    }
}