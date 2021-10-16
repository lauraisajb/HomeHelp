package com.example.homehelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

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

        ArrayAdapter<CharSequence> adapterCiudad = ArrayAdapter.createFromResource(this, R.array.Ciudad, android.R.layout.simple_spinner_item);
        comboCity2.setAdapter(adapterCiudad);

        //Oficio
        comboOficio2 = (Spinner) findViewById(R.id.spinnerOficio2);
        ArrayAdapter<CharSequence> adapterOficio = ArrayAdapter.createFromResource(this, R.array.Oficio, android.R.layout.simple_spinner_item);
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
                String city, oficio;
                int calificacion = 0;

                city = comboCity2.getSelectedItem().toString();
                oficio = comboOficio2.getSelectedItem().toString();

                if (city.equals("Ciudad")) {
                    Toast.makeText(Search_activity.this, "Debe elegir una ciudad", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (oficio.equals("Oficio")) {
                    Toast.makeText(Search_activity.this, "Debe elegir un oficio", Toast.LENGTH_SHORT).show();
                    return;
                }


                Intent intent = new Intent( Search_activity.this, WorkerList.class);
                intent.putExtra("oficio",oficio);
                intent.putExtra("city", city);
                //intent.putExtra("calificacion", calificacion);
                startActivity(intent);
            }
        });



    }
}