package com.example.crm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txt1;
    ImageView logo;
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        this.window=getWindow();
        window.setStatusBarColor(Color.TRANSPARENT);
        //Agregar animaciones
        Animation animacion1= AnimationUtils.loadAnimation(this,R.anim.desplazamiento_arriba);
        Animation animacion2= AnimationUtils.loadAnimation(this,R.anim.desplazamiento_abajo);
       txt1=findViewById(R.id.txt1);
       logo =findViewById(R.id.logo);
       txt1.setAnimation(animacion2);
       logo.setAnimation(animacion1);
       //Irse a otra actividad despues de cierto tiempo
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent intent1 = new Intent(MainActivity.this,LoginActivity.class);
               Pair[] pairs= new Pair[2];
               pairs[0]=new Pair<View,String>(logo,"logoImageTrans");
               pairs[1]=new Pair<View,String>(txt1,"textTrans");
               //corroborar tipo de version
               if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                   ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                   startActivity(intent1,options.toBundle());
                   finish();
               }else{
                   startActivity(intent1);
                   finish();
               }


           }
       },4000);
    }
}