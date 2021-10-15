package com.example.homehelp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //CIUDAD
        Spinner comboCity;
        //Usuario
        Spinner comboUser;

        //Usuario
        comboUser = (Spinner) findViewById(R.id.spinnerUserE);

        ArrayAdapter<CharSequence> adapterUser = ArrayAdapter.createFromResource(this,
                R.array.Usuario, android.R.layout.simple_spinner_item);
        comboUser.setEnabled(false);
        comboUser.setAdapter(adapterUser);


    }
}