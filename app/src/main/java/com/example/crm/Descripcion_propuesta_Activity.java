package com.example.crm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Descripcion_propuesta_Activity extends AppCompatActivity {
    TextView nombre_Completo,email1,numero_Telefono,posible_Ganancia,estatus;
    ImageButton btn_detalles,btn_atras,btn_actualizar,btn_crear_actividad,btn_prospecto,btn_negociacion,btn_ganado,btn_perdido,btn_eliminar;
    //Para el accionbar
    Toolbar toolbar1;
    //Shared preference para el cambio de variable
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_propuesta);
        relacionarvistas();
        //contiene todos los elementos elegidos
        ListElement element=(ListElement) getIntent().getSerializableExtra("ListElement");
        //Colocar los elementos
        nombre_Completo.setText(element.getNombre_Completo());
        nombre_Completo.setTextColor(Color.parseColor(element.getColor()));
        email1.setText(element.getEmail());
        numero_Telefono.setText(element.getEmail());
        estatus.setText(element.getEstado());
        //color
        estatus.setTextColor(Color.GREEN);
        //Aparecer el accionbar esto para poder ser transparente
        setSupportActionBar(toolbar1);
        //Encontrar la sesion iniciada
        preferences=getSharedPreferences("sesion", Context.MODE_PRIVATE);
        editor=preferences.edit();
        //oyentes
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresarpropuesta();
            }
        });
        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mover_clientes();
            }
        });
        btn_crear_actividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crear_actividad();
            }
        });
        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizar_cliente();
            }
        });
        //mover de categoria oyentes botones
        btn_prospecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mover_prospecto();
            }
        });
        btn_negociacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mover_negociacion();
            }
        });
        btn_ganado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mover_ganado();
            }
        });
        btn_perdido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mover_perdido();
            }
        });
        /*btn_detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detallesact();
            }
        });*/
    }
    public void relacionarvistas(){
        //relacionar elementos
        nombre_Completo=findViewById(R.id.nombre_Completo);
        email1=findViewById(R.id.email);
        numero_Telefono=findViewById(R.id.numero_Telefono);
        posible_Ganancia=findViewById(R.id.posible_Ganancia);
        estatus=findViewById(R.id.estatus);
        btn_actualizar=(ImageButton)findViewById(R.id.btn_actualizar);
        btn_crear_actividad=(ImageButton) findViewById(R.id.btn_crear_actividad);
        btn_prospecto=(ImageButton) findViewById(R.id.btn_prospecto);
        btn_negociacion=(ImageButton) findViewById(R.id.btn_negociacion);
        btn_ganado=(ImageButton) findViewById(R.id.btn_ganando);
        btn_perdido=(ImageButton) findViewById(R.id.btn_perdido);
        btn_eliminar=(ImageButton) findViewById(R.id.btn_eliminar);
        //btn_detalles=(ImageButton) findViewById(R.id.btn_detalles);
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
    //Metodo regresa propuesta
    public void regresarpropuesta(){
        Intent intent1 = new Intent(getApplicationContext(),Propuesta_Activity.class);
        startActivity(intent1);
        finish();
    }
    //mover descripcion para actualizar
    public void moveToDescription(ListElement item){
        Intent intent= new Intent(this,Actualizar_cliente_Activity.class);
        intent.putExtra("ListElement", item);
        startActivity(intent);
    }
    //enviar correos para crear actividad
    public void crear_actividad(){
        //recupero el correo usuario
        Intent recibir = getIntent();
        String datounico = recibir.getStringExtra("correoUsuario");
        String correoUsuario=datounico;
        //envio correo cliente y usuario
        String correoCliente= email1.getText().toString();
        Intent correo = new Intent(getApplicationContext(),RegistroActividad_Activity.class);
        correo.putExtra("correoUsuario",correoUsuario);
        correo.putExtra("correoCliente",correoCliente);
        startActivity(correo);
        finish();
    }
    //metodo manda a actualizar cliente
    public void actualizar_cliente(){
        String correounico= email1.getText().toString();
        Intent correo = new Intent(getApplicationContext(),Actualizar_cliente_Activity.class);
        correo.putExtra("correo",correounico);
        startActivity(correo);
        finish();
    }
    //ver detalles de la actividad
    public void detallesact(){
        //tengo que pasar el correo del usuario y cliente
        //recupero el correo usuario
        Intent recibir = getIntent();
        String datounico = recibir.getStringExtra("correoUsuario");
        String correoUsuario=datounico;
        //correo cliente
        String correounico= email1.getText().toString();
        //envio
        Intent intent = new Intent(getApplicationContext(),Descripcion_actividad_Activity.class);
        intent.putExtra("correoUsuario",correoUsuario);
        startActivity(intent);
        finish();

    }
    //mover de categoria
    public void mover_prospecto(){
        IP ip= new IP();
        //categoria
        String categoria = "Prospecto";
        //recupero el correo
        String email = email1.getText().toString();

        RequestQueue servicioConsulta = Volley.newRequestQueue(this);
        StringRequest respuestaConsulta = new StringRequest(Request.Method.POST,
                ip.getIP()+":3977/api/actualizarCategoria",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject json = null;
                        try {
                            json = new JSONObject(response);
                            JSONArray r = json.getJSONArray("cita");
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = new JSONObject(response);
                                String mesagge = obj.getString("mesagge");
                                mesagge.toString();
                                Toast.makeText(getApplicationContext(), mesagge,
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            System.out.println(e.toString());
                        }
                        Toast.makeText(getApplicationContext(),
                                response.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //que se quite el progressdialog
                Toast.makeText(getApplicationContext(),
                        "Este cliente no pudo ser eliminado",
                        Toast.LENGTH_SHORT).show();
                //System.out.println(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("categorioa",categoria);
                return params;
            }
        };
        servicioConsulta.add(respuestaConsulta);
        Intent intent1 = new Intent(getApplicationContext(), Equipo_venta_Activity.class);
        startActivity(intent1);
        finish();
    }
    public void mover_negociacion(){
        IP ip= new IP();
        //categoria
        String categoria = "Negociacion";
        //recupero el correo
        String email = email1.getText().toString();

        RequestQueue servicioConsulta = Volley.newRequestQueue(this);
        StringRequest respuestaConsulta = new StringRequest(Request.Method.POST,
                ip.getIP()+":3977/api/actualizarCategoria",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject json = null;
                        try {
                            json = new JSONObject(response);
                            JSONArray r = json.getJSONArray("cita");
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = new JSONObject(response);
                                String mesagge = obj.getString("mesagge");
                                mesagge.toString();
                                Toast.makeText(getApplicationContext(), mesagge,
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            System.out.println(e.toString());
                        }
                        Toast.makeText(getApplicationContext(),
                                response.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //que se quite el progressdialog
                Toast.makeText(getApplicationContext(),
                        "Este cliente no pudo ser eliminado",
                        Toast.LENGTH_SHORT).show();
                //System.out.println(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("categorioa",categoria);
                return params;
            }
        };
        servicioConsulta.add(respuestaConsulta);
        Intent intent1 = new Intent(getApplicationContext(), Equipo_venta_Activity.class);
        startActivity(intent1);
        finish();
    }
    public void mover_ganado(){
        IP ip= new IP();
        //categoria
        String categoria = "Ganados";
        //recupero el correo
        String email = email1.getText().toString();

        RequestQueue servicioConsulta = Volley.newRequestQueue(this);
        StringRequest respuestaConsulta = new StringRequest(Request.Method.POST,
                ip.getIP()+":3977/api/actualizarCategoria",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject json = null;
                        try {
                            json = new JSONObject(response);
                            JSONArray r = json.getJSONArray("cita");
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = new JSONObject(response);
                                String mesagge = obj.getString("mesagge");
                                mesagge.toString();
                                Toast.makeText(getApplicationContext(), mesagge,
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            System.out.println(e.toString());
                        }
                        Toast.makeText(getApplicationContext(),
                                response.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //que se quite el progressdialog
                Toast.makeText(getApplicationContext(),
                        "Este cliente no pudo ser eliminado",
                        Toast.LENGTH_SHORT).show();
                //System.out.println(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("categorioa",categoria);
                return params;
            }
        };
        servicioConsulta.add(respuestaConsulta);
        Intent intent1 = new Intent(getApplicationContext(), Equipo_venta_Activity.class);
        startActivity(intent1);
        finish();
    }
    public void mover_perdido(){
        IP ip= new IP();
        //categoria
        String categoria = "Perdidos";
        //recupero el correo
        String email = email1.getText().toString();

        RequestQueue servicioConsulta = Volley.newRequestQueue(this);
        StringRequest respuestaConsulta = new StringRequest(Request.Method.POST,
                ip.getIP()+":3977/api/actualizarCategoria",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject json = null;
                        try {
                            json = new JSONObject(response);
                            JSONArray r = json.getJSONArray("cita");
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = new JSONObject(response);
                                String mesagge = obj.getString("mesagge");
                                mesagge.toString();
                                Toast.makeText(getApplicationContext(), mesagge,
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            System.out.println(e.toString());
                        }
                        Toast.makeText(getApplicationContext(),
                                response.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //que se quite el progressdialog
                Toast.makeText(getApplicationContext(),
                        "Este cliente no pudo ser eliminado",
                        Toast.LENGTH_SHORT).show();
                //System.out.println(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("categorioa",categoria);
                return params;
            }
        };
        servicioConsulta.add(respuestaConsulta);
        Intent intent1 = new Intent(getApplicationContext(), Equipo_venta_Activity.class);
        startActivity(intent1);
        finish();
    }
    public void mover_clientes() {
        IP ip= new IP();
        //categoria
        String estado = "Inactivo";
        //recupero el correo
        String email = email1.getText().toString();

        RequestQueue servicioConsulta = Volley.newRequestQueue(this);
        StringRequest respuestaConsulta = new StringRequest(Request.Method.POST,
                ip.getIP()+":3977/api/actualizarEstado",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject json = null;
                        try {
                            json = new JSONObject(response);
                            JSONArray r = json.getJSONArray("cita");
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = new JSONObject(response);
                                String mesagge = obj.getString("mesagge");
                                mesagge.toString();
                                Toast.makeText(getApplicationContext(), mesagge,
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            System.out.println(e.toString());
                        }
                        Toast.makeText(getApplicationContext(),
                                response.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //que se quite el progressdialog
                Toast.makeText(getApplicationContext(),
                        "Este cliente no pudo ser eliminado",
                        Toast.LENGTH_SHORT).show();
                //System.out.println(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("estado", estado);
                return params;
            }
        };
        servicioConsulta.add(respuestaConsulta);
        Intent intent1 = new Intent(getApplicationContext(), Equipo_venta_Activity.class);
        startActivity(intent1);
        finish();
    }

}