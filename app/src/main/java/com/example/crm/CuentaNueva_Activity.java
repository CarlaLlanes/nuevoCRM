package com.example.crm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class CuentaNueva_Activity extends AppCompatActivity {
    //String para validar campos vacios
    String nombre_Completo, email, numero_Telefono, password;
    //EditText de material para realizar el registro
    TextInputEditText edt_nombre_Completo, edt_email, edt_password, edt_numero_Telefono;
    //Spiner para el tipo de usuario Gerente/Vendedor
    Spinner spinner_rol;
    //Botones
    Button btn_registro_normal;
    ImageButton btn_atras;
    //Barra de proceso, sirve para mostrarle al usuario que se esta realizando algo
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_nueva);
        //llamar elementos
        relacionarVistas();
        //inicializamos a requestqueue y el progresdialog
        requestQueue = Volley.newRequestQueue(CuentaNueva_Activity.this);
        progressDialog = new ProgressDialog(CuentaNueva_Activity.this);
        //oyentes botones
        btn_registro_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registro_normal();
            }
        });
        //regresa al login
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresarlogin();
            }
        });
    }

    //Metodo que relaciona las vistas
    public void relacionarVistas() {
        edt_nombre_Completo = (TextInputEditText) findViewById(R.id.edt_nombre);
        edt_email = (TextInputEditText) findViewById(R.id.edt_email);
        edt_password = (TextInputEditText) findViewById(R.id.edt_password);
        edt_numero_Telefono = (TextInputEditText) findViewById(R.id.edt_cel);
        btn_registro_normal = (Button) findViewById(R.id.btn_registro_normal);
        btn_atras = (ImageButton) findViewById(R.id.btn_atras);
        //spiner
        spinner_rol = (Spinner) findViewById(R.id.spinner_rol);
        //muestra las opciones del spinner
        String[] rol = {"Gerente", "Vendedor"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rol);
        //
        spinner_rol.setAdapter(adapter);
    }

    //Metodo regresa al login
    public void regresarlogin() {
        Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent1);
        finish();
    }
    //Metodo para registrar de forma normal, la otra forma es google
    private void registro_normal() {
        IP ip= new IP();
        //Recuperar el contenido y poder validar campos
        nombre_Completo = edt_nombre_Completo.getText().toString();
        email = edt_email.getText().toString();
        numero_Telefono = edt_numero_Telefono.getText().toString();
        password = edt_password.getText().toString();
        //Si hay campos vacios
        if (nombre_Completo.isEmpty() || email.isEmpty() || numero_Telefono.isEmpty() || password.isEmpty()) {
            //muestra un toast para llenarlos
            Toast.makeText(getApplicationContext(), "Llena todos campos", Toast.LENGTH_SHORT).show();
        } else {
            //mostrar el progressDialog
            progressDialog.setMessage("Registrando Nuevo Usuario");
            progressDialog.show();
            RequestQueue servicioConsulta = Volley.newRequestQueue(this);
            StringRequest respuestaConsulta = new StringRequest(Request.Method.POST,
                    ip.getIP()+":3977/api/registroUsuario",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject json = null;
                            nombre_Completo = edt_nombre_Completo.getText().toString();
                            spinner_rol.getSelectedItem().toString();
                            email = edt_email.getText().toString();
                            numero_Telefono = edt_numero_Telefono.getText().toString();
                            password = edt_password.getText().toString();
                            //que se quite el progressdialog
                            progressDialog.dismiss();

                            try {
                                json = new JSONObject(response);
                                JSONArray r = json.getJSONArray("cita");
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = new JSONObject(response);
                                    String mesagge = obj.getString("mesagge");
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
                            "Este email ya ha sido registrado",
                            Toast.LENGTH_SHORT).show();
                    //System.out.println(error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("nombre_Completo", nombre_Completo);
                    params.put("rol", spinner_rol.getSelectedItem().toString());
                    params.put("email", email);
                    params.put("numero_Telefono", numero_Telefono);
                    params.put("password", password);
                    return params;
                }
            };
            servicioConsulta.add(respuestaConsulta);
        }
    }


}