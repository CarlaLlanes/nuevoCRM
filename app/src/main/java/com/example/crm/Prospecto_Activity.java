package com.example.crm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crm.databinding.ActivityProspectoBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Prospecto_Activity extends AppCompatActivity {
    RequestQueue requestQueue;
    RecyclerView recyclerView;
    //Componentes a utilizar
    ExtendedFloatingActionButton btn_registrar_prospectos;
    //Para el accionbar
    Toolbar toolbar1;
    //para los datos compartidos
    //con el fin de saber si la sesion esta iniciada
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    //para mostrar la lista
    ArrayList<ListElement> elements;
    //para el boton flotante
    ActivityProspectoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prospecto);
        //Relacionar elementos visuales con logica
        relacionarvistas();
        //insancia array para lista
        elements = new ArrayList<>();
        //inicializamos a requestqueue
        requestQueue = Volley.newRequestQueue(Prospecto_Activity.this);
        //Aparecer el accionbar esto para poder ser transparente
        setSupportActionBar(toolbar1);
        //Encontrar la sesion iniciada
        preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //Metodo de lista para mostrar los prospectos
        lista_Prospectos();
        //oyentes botones
        //oyente de la lista

        recyclerView = (RecyclerView) findViewById(R.id.list_Prospecto);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
        //Boton flotante
        binding = ActivityProspectoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Oyente Boton flotante
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), RegistroProspecto_Activity.class);
                startActivity(intent1);
                finish();
            }
        });
    }

    //Metodo que relaciona las vistas
    public void relacionarvistas() {
        toolbar1 = findViewById(R.id.toolbar1);
    }

    //metodo de prospectos
    private void lista_Prospectos() {
        IP ip=new IP();
        String categoria= "Prospecto";
        String url = ip.getIP()+":3977/api/consultarClientesXCategoria";
        JsonObjectRequest requerimiento = new JsonObjectRequest(Request.Method.POST,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String valor = null;
                            valor = response.get("dateOfClient").toString();
                            JSONArray arreglo = new JSONArray(valor);
                            JSONObject objeto = new JSONObject(arreglo.get(0).toString());

                            //probar este modo
                            String nombre_Completo = objeto.getString("nombre_Completo");
                            String email= objeto.getString("email");
                            String numero_Telefono=objeto.getString("numero_Telefono");
                            String posible_Ganancia=objeto.getString("posible_Ganancia");
                            String estado=objeto.getString("estado");

                            ListElement usuario = new ListElement("#70bdb0", nombre_Completo, email, numero_Telefono, posible_Ganancia, estado);
                            elements.add(usuario);
                            //ListAdapter.notifyItemRangeInserted(elements.size(),1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "ERROR RED",
                                Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("categoria", categoria);
                return params;
            }
        };
        requestQueue.add(requerimiento);
    }
    ListAdapter listAdapter = new ListAdapter(elements, this, new ListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(ListElement item) {
            moveToDescription(item);
        }
    });

    //Descripción cuando se selecciona uno de la lista
    public void moveToDescription(ListElement item) {
        Intent intent = new Intent(this, DescripcionActivity.class);
        intent.putExtra("ListElement", item);
        String ventana="prospecto";
        intent.putExtra("ventana",ventana);
        startActivity(intent);
        finish();
    }

}