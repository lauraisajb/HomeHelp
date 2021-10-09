package com.example.homehelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

public class Search_activity extends AppCompatActivity {

    //botones
    ImageButton btnBack;
    Button btnEmpezar;

    //CIUDAD
    Spinner comboCity2;

    //Oficio
    Spinner comboOficio2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //botones
        btnBack = (ImageButton) findViewById(R.id.btnCloseW);
        btnEmpezar = (Button)findViewById(R.id.btnEmpezar);

        //CIUDAD
        comboCity2 = (Spinner) findViewById(R.id.spinnerCiudad2);

        String [] Ciudades2= {"Ciudad","Tulua","Buga"};
        ArrayAdapter<String> adapterCiudad = new ArrayAdapter<>(this, R.layout.spinner_item,Ciudades2);
        comboCity2.setAdapter(adapterCiudad);

        //Oficio
        comboOficio2 = (Spinner) findViewById(R.id.spinnerOficio2);
        String[] Oficio2 = {"Oficio","Carpintero","Fontanero","Electricista","Pintor"};

        ArrayAdapter<CharSequence> adapterOficio = new ArrayAdapter<>(this,R.layout.spinner_item,Oficio2);
        comboOficio2.setAdapter(adapterOficio);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent( Search_activity.this, activity_view_customer.class));
            }
        });

        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent( Search_activity.this, WorkerList.class));
            }
        });

    }
}