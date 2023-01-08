package com.example.crm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Actualizar_cliente_Activity extends AppCompatActivity {
    //String para validar campos vacios
    String nombre_Completo,email_Nuevo,numero_Telefono,posible_Ganancia;
    //EditText de material para realizar el registro
    TextInputEditText edt_nombre_Completo,edt_email,edt_numero_Telefono,edt_posible_Ganancia;
    //Botones
    Button btn_registro_prospecto;
    ImageButton btn_atras;
    //Barra de proceso, sirve para mostrarle al usuario que se esta realizando algo
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    //
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    //Para el accionbar
    Toolbar toolbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_cliente);
        //llamar elementos
        relacionarVistas();
        //contiene todos los elementos elegidos
        ListElement element=(ListElement) getIntent().getSerializableExtra("ListElement");
        //Colocar los elementos
        edt_nombre_Completo.setText(element.getNombre_Completo());
        edt_email.setText(element.getEmail());
        edt_numero_Telefono.setText(element.getNumero_Telefono());
        edt_posible_Ganancia.setText(element.getPosible_Ganancia());
        //Aparecer el accionbar esto para poder ser transparente
        setSupportActionBar(toolbar1);
        //Encontrar la sesion iniciada
        preferences=getSharedPreferences("sesion", Context.MODE_PRIVATE);
        editor=preferences.edit();
        //inicializamos a requestqueue y el progresdialog
        requestQueue= Volley.newRequestQueue(Actualizar_cliente_Activity.this);
        progressDialog=new ProgressDialog(Actualizar_cliente_Activity.this);
        //oyentes botones
        btn_registro_prospecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizar_prospecto();
            }
        });
        //regresa al login
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresarmenu();
            }
        });
    }
    //Metodo que relaciona las vistas
    public void relacionarVistas(){
        edt_nombre_Completo=( TextInputEditText) findViewById(R.id.edt_nombre_Completo);
        edt_email=( TextInputEditText) findViewById(R.id.edt_email);
        edt_numero_Telefono=( TextInputEditText) findViewById(R.id.edt_numero_Telefono);
        edt_posible_Ganancia=( TextInputEditText) findViewById(R.id.edt_cel);
        btn_registro_prospecto=(Button) findViewById(R.id.btn_registro_prospecto);
        btn_atras=(ImageButton)findViewById(R.id.btn_atras);
    }
    //Metodo regresa al menu principal
    public void regresarmenu(){
        Intent intent1 = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent1);
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
    //Metodo para registrar de forma normal, la otra forma es google
    private void actualizar_prospecto() {
        IP ip= new IP();
        //recupero el correo
        Intent recibir = getIntent();
        String datounico = recibir.getStringExtra("correo");
        String email=datounico;
        //Recuperar el contenido y poder validar campos
        nombre_Completo = edt_nombre_Completo.getText().toString();
        email_Nuevo = edt_email.getText().toString();
        numero_Telefono = edt_numero_Telefono.getText().toString();
        posible_Ganancia = edt_numero_Telefono.getText().toString();
        //Si hay campos vacios
        if (nombre_Completo.isEmpty() || email_Nuevo.isEmpty()|| numero_Telefono.isEmpty() || posible_Ganancia.isEmpty())
            //muestra un toast para llenarlos
            Toast.makeText(getApplicationContext(), "Llena los dos campos", Toast.LENGTH_LONG).show();
        else {
            //mostrar el progressDialog
            progressDialog.setMessage("Actualizando Usuario");
            progressDialog.show();
            RequestQueue servicioConsulta = Volley.newRequestQueue(this);
            StringRequest respuestaConsulta = new StringRequest(Request.Method.POST,
                    ip.getIP()+":3977/api/actualizarCliente",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject json = null;
                            nombre_Completo = edt_nombre_Completo.getText().toString();
                            email_Nuevo = edt_email.getText().toString();
                            numero_Telefono = edt_numero_Telefono.getText().toString();
                            posible_Ganancia = edt_numero_Telefono.getText().toString();
                            //que se quite el progressdialog
                            progressDialog.dismiss();

                            try {
                                json = new JSONObject(response);
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = new JSONObject(response);
                                    String mesagge = obj.getString("mesagge");
                                    mesagge.toString();
                                    Toast.makeText(getApplicationContext(), mesagge,
                                            Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                //que se quite el progressdialog
                                progressDialog.dismiss();
                            /*Toast.makeText(getApplicationContext(),
                                    "ERROR JSON",
                                    Toast.LENGTH_SHORT).show();*/
                                System.out.println(e.toString());
                            }
                            //que se quite el progressdialog
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),
                                    response.toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //que se quite el progressdialog
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),
                            "Email no identificado",
                            Toast.LENGTH_SHORT).show();
                    //System.out.println(error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email",email);
                    params.put("nombre_Completo",nombre_Completo);
                    params.put("email_Nuevo",email_Nuevo);
                    params.put("numero_Telefono",numero_Telefono);
                    params.put("posible_Ganancia",posible_Ganancia);
                    return params;
                }
            };
            servicioConsulta.add(respuestaConsulta);
        }
    }
}