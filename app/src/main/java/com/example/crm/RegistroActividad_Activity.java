package com.example.crm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
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

public class RegistroActividad_Activity extends AppCompatActivity {
    TextView textViewTime, textViewDate,textViewTime1;
    //String para validar campos vacios
    String duracion,descripcion,recordatorio,medio_Contacto;
    //EditText de material para realizar el registro
    TextInputEditText edt_duracion, edt_descripcion, edt_medio_Contacto, edt_recordatorio;
    //Spiner para el tipo de usuario Gerente/Vendedor
    Spinner spinner_tipoACT;
    //Botones
    Button btn_registro_actividad;
    ImageButton btn_atras;
    //Barra de proceso, sirve para mostrarle al usuario que se esta realizando algo
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    interface TimeInterface {
        void onTimeSet(String time);
    }

    interface DateInterface {
        void onDateSet(String date);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_actividad);
        //llamar elementos
        relacionarvistas();
        //inicializamos a requestqueue y el progresdialog
        requestQueue = Volley.newRequestQueue(com.example.crm.RegistroActividad_Activity.this);
        progressDialog = new ProgressDialog(com.example.crm.RegistroActividad_Activity.this);
        //oyentes
        btn_registro_actividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registro_actividad();
            }
        });
        //regresa al descripcion actividad
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresardpropuesta();
            }
        });

    }
    //relacionarvistas
    public void relacionarvistas() {

        textViewTime = findViewById(R.id.textViewTime);
        textViewTime1 = findViewById(R.id.textViewTime1);
        textViewDate = findViewById(R.id.textViewDate);

        edt_duracion = (TextInputEditText) findViewById(R.id.edt_duracion);
        edt_descripcion = (TextInputEditText) findViewById(R.id.edt_descripcion);
        edt_medio_Contacto = (TextInputEditText) findViewById(R.id.edt_medio_Contacto);
        edt_recordatorio = (TextInputEditText) findViewById(R.id.edt_recordatorio);
        btn_registro_actividad = (Button) findViewById(R.id.btn_registro_actividad);
        btn_atras = (ImageButton) findViewById(R.id.btn_atras);
        //spiner
        spinner_tipoACT = (Spinner) findViewById(R.id.spinner_tipo);
        //muestra las opciones del spinner
        String[] tipoACT = {"Llamada", "Videollamada","Correo"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipoACT);
        //
        spinner_tipoACT.setAdapter(adapter);
    }
    //metodos que te dan la fecha y hora
    public void showTimeFragment(View view) {
        DialogFragment timeFragment = new TimePickeFragment(new TimeInterface() {
            @Override
            public void onTimeSet(String time) {
                textViewTime.setText(time);
            }
        });
        timeFragment.show(getSupportFragmentManager(), timeFragment.getClass().getSimpleName());
    }
    public void showTimeFragment1(View view) {
        DialogFragment timeFragment = new TimePickeFragment(new TimeInterface() {
            @Override
            public void onTimeSet(String time) {
                textViewTime1.setText(time);
            }
        });
        timeFragment.show(getSupportFragmentManager(), timeFragment.getClass().getSimpleName());
    }
    public void showDateFragment(View view) {
        DialogFragment timeFragment = new DatePickerFragment(new DateInterface() {
            @Override
            public void onDateSet(String date) {
                textViewDate.setText(date);
            }
        });
        timeFragment.show(getSupportFragmentManager(), timeFragment.getClass().getSimpleName());
    }
    //Metodo regresa propuesta
    public void regresardpropuesta() {
        Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent1);
        finish();
    }

        //Metodo para registrar de forma normal, la otra forma es google
        private void registro_actividad() {
        IP ip =new IP();

            //recupero los correos
            //cliente
            Intent recibir = getIntent();
            String datounico = recibir.getStringExtra("correoCliente");
            String emailCliente=datounico;
            //usuario
            Intent recibir2 = getIntent();
            String datounico2 = recibir2.getStringExtra("correoUsuario");
            String emailUsuario=datounico2;
        //hora y fecha
            String hora_Inicio= textViewTime.getText().toString();
            String hora_Termino= textViewTime1.getText().toString();
            String fecha_Vencimiento = textViewDate.getText().toString();
            //Recuperar el contenido y poder validar campos
            duracion = edt_duracion.getText().toString();
            descripcion = edt_descripcion.getText().toString();
            medio_Contacto = edt_medio_Contacto.getText().toString();
            recordatorio = edt_recordatorio.getText().toString();
            //Si hay campos vacios
            if (hora_Inicio.isEmpty()||hora_Termino.isEmpty()||fecha_Vencimiento.isEmpty()||duracion.isEmpty()||descripcion.isEmpty()||medio_Contacto.isEmpty()||recordatorio.isEmpty()) {
                //muestra un toast para llenarlos
                Toast.makeText(getApplicationContext(), "Llena todos campos", Toast.LENGTH_SHORT).show();
            } else {
                //mostrar el progressDialog
                progressDialog.setMessage("Registrando Nuevo Actividad");
                progressDialog.show();
                RequestQueue servicioConsulta = Volley.newRequestQueue(this);
                StringRequest respuestaConsulta = new StringRequest(Request.Method.POST,
                        ip.getIP()+":3977/api/registroUsuario",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject json = null;
                                duracion = edt_duracion.getText().toString();
                                descripcion = edt_descripcion.getText().toString();
                                medio_Contacto = edt_medio_Contacto.getText().toString();
                                recordatorio = edt_recordatorio.getText().toString();
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
                                "Registo igual",
                                Toast.LENGTH_SHORT).show();
                        //System.out.println(error.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("tipo_Actividad", spinner_tipoACT.getSelectedItem().toString());
                        params.put("fecha_Vencimiento", fecha_Vencimiento);
                        params.put("hora_Inicio", hora_Inicio);
                        params.put("hora_Termino", hora_Termino);
                        params.put("duracion", duracion);
                        params.put("descripcion", descripcion);
                        params.put("recordatorio", recordatorio);
                        params.put("medio_Contacto", medio_Contacto);
                        params.put("emailUsuario", emailUsuario);
                        params.put("emailCliente", emailCliente);
                        return params;
                    }
                };
                servicioConsulta.add(respuestaConsulta);
            }
        }

}