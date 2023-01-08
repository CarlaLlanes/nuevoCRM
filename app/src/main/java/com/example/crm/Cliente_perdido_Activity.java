package com.example.crm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Cliente_perdido_Activity extends AppCompatActivity {
    RequestQueue requestQueue;
    //Componentes a utilizar
    ImageButton btn_atras;
    //Para el accionbar
    Toolbar toolbar1;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ArrayList<ListElement> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_perdido);
        //Relacionar elementos visuales con logica
        relacionarvistas();
        //insancia array para lista
        elements=new ArrayList<>();
        //inicializamos a requestqueue
        requestQueue= Volley.newRequestQueue(Cliente_perdido_Activity.this);
        //Aparecer el accionbar esto para poder ser transparente
        setSupportActionBar(toolbar1);
        //Encontrar la sesion iniciada y extraer el nombre del usuario
        preferences=getSharedPreferences("sesion", Context.MODE_PRIVATE);
        editor=preferences.edit();
        //Metodo de lista para mostrar los prospectos
        lista_Clientes_Perdidos();
        //oyentes botones
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresarmenu();
            }
        });
        //oyente de la lista
        ListAdapter listAdapter=new ListAdapter(elements, this, new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ListElement item) {
                moveToDescription(item);
            }
        });
        RecyclerView recyclerView=findViewById(R.id.list_Clientes_Perdidos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }
    //Metodo que relaciona las vistas
    public void relacionarvistas(){
        btn_atras=(ImageButton)findViewById(R.id.btn_atras);
        toolbar1=findViewById(R.id.toolbar1);
    }
    //Metodo regresa al menu principal
    public void regresarmenu(){
        Intent intent1 = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent1);
    }
    //metodo de lista Clientes_Perdidos
    private void lista_Clientes_Perdidos(){
        String url="https://randomuser.me/api/";
        JsonObjectRequest requerimiento = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String valor= null;
                        try {
                            valor = response.get("result").toString();
                            JSONArray arreglo = new JSONArray(valor);
                            JSONObject objeto = new JSONObject(arreglo.get(0).toString());
                            String nombre_Completo = objeto.getJSONObject("nombre_Completo").getString("nombre_Completo");
                            String email = objeto.getJSONObject("email").getString("email");
                            String numero_Telefono = objeto.getJSONObject("numero_Telefono").getString("numero_Telefono");
                            String posible_Ganancia= objeto.getJSONObject("posible_Ganancia").getString("posible_Ganancia");
                            String estado = objeto.getJSONObject("estado").getString("estado");
                            //
                            ListElement usuario=new ListElement("#F60505",nombre_Completo, email, numero_Telefono,posible_Ganancia,estado);
                            elements.add(usuario);
                            // ListAdapter.notifyItemRangeInserted(elements.size(),1);
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),
                                    "ERROR JSON",
                                    Toast.LENGTH_SHORT).show();
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
                }
        );
        requestQueue.add(requerimiento);
    }

    //Descripción cuando se selecciona uno de la lista
    public void moveToDescription(ListElement item){
        Intent intent= new Intent(this,DescripcionActivity.class);
        intent.putExtra("ListElement", item);
        startActivity(intent);
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
}