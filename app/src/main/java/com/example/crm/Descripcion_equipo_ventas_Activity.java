package com.example.crm;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class Descripcion_equipo_ventas_Activity extends AppCompatActivity {
    TextView nombre_Completo,email1,numero_Telefono;
    ImageButton btn_atras,btn_eliminar;
    //Para el accionbar
    Toolbar toolbar1;
    //Shared preference para el cambio de variable
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);
        relacionarvistas();
        //contiene todos los elementos elegidos
        ListElement_equipo_ventas element=(ListElement_equipo_ventas) getIntent().getSerializableExtra("ListElement_equipo_ventas");
        //Colocar los elementos
        nombre_Completo.setText(element.getNombre_Completo());
        nombre_Completo.setTextColor(Color.parseColor(element.getColor()));
        email1.setText(element.getEmail());
        numero_Telefono.setText(element.getNumero_Telefono());
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
        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar_vendedor();
            }
        });
    }
    public void relacionarvistas(){
        //relacionar elementos
        nombre_Completo=findViewById(R.id.nombre_Completo);
        email1=findViewById(R.id.email);
        numero_Telefono=findViewById(R.id.numero_Telefono);
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
    //Metodo regresa a equipo de ventas
    public void regresar(){
        Intent intent1 = new Intent(getApplicationContext(),Equipo_venta_Activity.class);
        startActivity(intent1);
        finish();
    }
    public void eliminar_vendedor(){
        IP ip= new IP();
      String email= email1.getText().toString();
        RequestQueue servicioConsulta = Volley.newRequestQueue(this);
        StringRequest respuestaConsulta = new StringRequest(Request.Method.POST,
                ip.getIP()+":3977/api/eliminarUsuario",
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
                        "Este vendor no pudo ser eliminado",
                        Toast.LENGTH_SHORT).show();
                //System.out.println(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",email);
                return params;
            }
        };
        servicioConsulta.add(respuestaConsulta);
        Intent intent1 = new Intent(getApplicationContext(),Equipo_venta_Activity.class);
        startActivity(intent1);
        finish();
    }


}