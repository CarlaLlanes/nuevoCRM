package com.example.crm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Home_gerente_Activity extends AppCompatActivity {
    //Para el accionbar
    Toolbar toolbar1;
    //Shared preference para el cambio de variable
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    //Tipo de etiqueta para mostrar al usuario
    TextView nombreUsuario;
    //tipo de botones
    ImageButton btn_prospecto,btn_propuesta,btn_negociacion,btn_ganado,btn_perdido,btn_chat,btn_llamada,btn_videollamada,btn_clientes,btn_equipo_venta,btn_actividades;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_gerente);
        //Relacionar elementos visuales con logica
        relacionarvistas();
        //Aparecer el accionbar esto para poder ser transparente
        setSupportActionBar(toolbar1);
        //Encontrar la sesion iniciada y extraer el nombre del usuario
        preferences=getSharedPreferences("sesion", Context.MODE_PRIVATE);
        editor=preferences.edit();
        SharedPreferences sp1 = getSharedPreferences("sesion",Context.MODE_PRIVATE);
        //recupero nombre
        Intent recibir = getIntent();
        String datounico = recibir.getStringExtra("nombre_Completo");
        String nombre_Completo=datounico;
        //muestro el nombre
        nombreUsuario.setText(nombre_Completo);
        //oyentes botones
        //para ir a la parte de videollamadas pendientes
        btn_videollamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videollamada();
            }
        });
        //para ir a la parte de llamadas pendientes
        btn_llamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llamada();
            }
        });
        //para ir a la parte de prospectos
        btn_prospecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prospecto();
            }
        });
        //para ir a la parte de propuesta
        btn_propuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                propuesta();
            }
        });
        //para ir a la parte de negociacion
        btn_negociacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                negociacion();
            }
        });
        //para ir a la parte de ganado
        btn_ganado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ganado();
            }
        });
        //para ir a la parte de perdido
        btn_perdido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perdido();
            }
        });
        //para ir a la parte del catalogo de clientes
        btn_clientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientes();
            }
        });
        //para ir a la parte del chat
        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chat();
            }
        });
        //para ir a la parte del chat
        btn_equipo_venta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equipo_venta();
            }
        });
        btn_actividades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actividades();
            }
        });
    }
    //metodo que relaciona las vistas
    public void relacionarvistas(){
        btn_prospecto=(ImageButton) findViewById(R.id.btn_prospecto);
        btn_propuesta=(ImageButton) findViewById(R.id.btn_propuesta);
        btn_negociacion=(ImageButton) findViewById(R.id.btn_negociacion);
        btn_ganado=(ImageButton) findViewById(R.id.btn_ganando);
        btn_perdido=(ImageButton) findViewById(R.id.btn_perdido);
        btn_chat=(ImageButton) findViewById(R.id.btn_chat);
        btn_videollamada=(ImageButton) findViewById(R.id.btn_videollamada);
        btn_llamada=(ImageButton) findViewById(R.id.btn_llamada);
        btn_clientes=(ImageButton) findViewById(R.id.btn_clientes);
        btn_equipo_venta=(ImageButton)findViewById(R.id.btn_equipo_venta);
        btn_actividades=(ImageButton) findViewById(R.id.btn_actividades);
        nombreUsuario=(TextView) findViewById(R.id.edt);
        toolbar1=findViewById(R.id.toolbar1);
    }

    //menu para cerrar sesion  y salir, primero mandarlo a traer
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
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
        return super.onOptionsItemSelected(item);}
    //metodos que ayudan a redirigirse a otra actividad
    public void equipo_venta(){
        Intent intent1 = new Intent(getApplicationContext(),Equipo_venta_Activity.class);
        startActivity(intent1);
        finish();
    }
    public void prospecto(){
        Intent intent1 = new Intent(getApplicationContext(),Prospecto_Activity.class);
        startActivity(intent1);
    }
    public void propuesta(){
        Intent intent1 = new Intent(getApplicationContext(),Propuesta_Activity.class);
        startActivity(intent1);
        finish();
    }
    public void negociacion(){
        Intent intent1 = new Intent(getApplicationContext(),Negociacion_Activity.class);
        startActivity(intent1);
        finish();
    }
    public void ganado(){
        Intent intent1 = new Intent(getApplicationContext(),Cliente_ganado_Activity.class);
        startActivity(intent1);
        finish();
    }
    public void perdido(){
        Intent intent1 = new Intent(getApplicationContext(),Cliente_perdido_Activity.class);
        startActivity(intent1);
        finish();
    }
    public void chat(){
        /*
        Intent intent1 = new Intent(getApplicationContext(),.class);
        startActivity(intent1);
        finish();*/
    }
    public void videollamada(){
        Intent intent1 = new Intent(getApplicationContext(),VideollamadaActivity.class);
        startActivity(intent1);
        finish();
    }
    public void llamada(){
        Intent intent1 = new Intent(getApplicationContext(),LlamadaActivity.class);
        startActivity(intent1);
        finish();
    }
    public void clientes(){
        Intent intent1 = new Intent(getApplicationContext(),Clientes_Activity.class);
        startActivity(intent1);
        finish();
    }
    public void actividades(){

        Intent intent1 = new Intent(getApplicationContext(),Actividad_Activity.class);
        startActivity(intent1);
        finish();
    }
}