package com.hlc.diurno.servicioconhilossecundarios;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent servicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        servicio = new Intent(this, MyService.class);
    }

    public void arrancarServicio(View v){
        startService(servicio);
    }

    public void detenerServicio(View v){
        stopService(servicio);
    }

}
