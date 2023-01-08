package com.example.crm;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class LoginActivity extends AppCompatActivity {
    //Barra de proceso, sirve para mostrarle al usuario que se esta realizando algo
    ProgressDialog progressDialog;
    //String para validar campos vacios
    String email,password;
    //sharedPreferences para el inicio de sesion
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    //llave que utiliza el sharedPreferences para el inicio de sesion
    String llave ="sesion";
    //tipo de elementos a utilizar
    //EditText de material para realizar el registro
    TextInputEditText edt_email, edt_password;
    //Botones
    Button btn_iniciar, btn_registro,btn_registro_gmail;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //llamar elementos
        relacionarVistas();
        //Verificar si la cuenta esta guardada o no
        preferences=this.getSharedPreferences(llave, Context.MODE_PRIVATE);
        editor=preferences.edit();
        /*mandar a traer al metodo revisar sesion, si la cuenta esta guardada
        inicia la sig. actividad/pantalla*/
        if(revisarSesion()){startActivity(new Intent(this,HomeActivity.class));
        }else{
            //si no muestra un toast para iniciar sesion
            String mensaje="Inicia sesión";
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT);}
        //inicializamos a requestqueue y el progresdialog
        requestQueue= Volley.newRequestQueue(LoginActivity.this);
        progressDialog=new ProgressDialog(LoginActivity.this);
        //oyentes botones
        //btn_iniciar manda a traer al metodo Login();
        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        //btn_iniciar manda a traer al metodo registro();
        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {registro();}
        });
        //prueba max
        btn_registro_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {logingerente();}
        });
    }
    //metodo que relaciona las vistas
    public void relacionarVistas(){
        edt_email=( TextInputEditText) findViewById(R.id.edt_email);
        edt_password=( TextInputEditText) findViewById(R.id.edt_password);
        btn_iniciar=(Button) findViewById(R.id.btn_iniciar);
        btn_registro=(Button) findViewById(R.id.btn_registro);
        btn_registro_gmail=(Button) findViewById(R.id.btn_registro_gmail);
    }
    //metodo para revisar la sesion
    private boolean revisarSesion(){
        return this.preferences.getBoolean("variable",false);
    }
    //cambia la variable de inicio se sesion para pantenerla en verdadera, ya que se a gurgado la sesion
    private void cambiarvariable(){
        editor.putBoolean("variable",true);
        editor.apply();
    }
    //metodo registro manda a traer la actividad/pantalla CuentaNueva_Activity
    public void registro(){
        Intent intent1 = new Intent(getApplicationContext(), CuentaNueva_Activity.class);
        startActivity(intent1);
        finish();
    }
    //login gerente
    public void logingerente(){
        Intent intent1 = new Intent(getApplicationContext(), L_gerente_Activity.class);
        startActivity(intent1);
        finish();
    }
    //metodo iniciar el Loging
    public void Login() {
        IP ip= new IP();
        //Recuperar el contenido y poder validar campos
        email = edt_email.getText().toString();
        password = edt_password.getText().toString();
        String correoUsuario= edt_email.getText().toString();
        //Si hay campos vacios
        if (email.isEmpty() || password.isEmpty()) {
            //muestra un toast para llenarlos
            Toast.makeText(getApplicationContext(), "Llena los dos campos", Toast.LENGTH_LONG).show();
        }else {
            //mostrar el progressDialog
            progressDialog.setMessage("Iniciando sesion");
            progressDialog.show();
            RequestQueue servicioConsulta = Volley.newRequestQueue(this);
            StringRequest respuestaConsulta = new StringRequest(Request.Method.POST,
                    ip.getIP()+":3977/api/loginUsuario",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            cambiarvariable();
                            String correoUsuario= edt_email.getText().toString();
                            Toast.makeText(getApplicationContext(),"Acceso correcto",
                                    Toast.LENGTH_LONG).show();
                            Intent intent1 = new Intent(getApplicationContext(), HomeActivity.class);
                            intent1.putExtra("correoUsuario",correoUsuario);
                            startActivity(intent1);
                            finish();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Datos incorrectos".toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }
            };
            servicioConsulta.add(respuestaConsulta);
        }
    }
}