package com.example.homehelp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class SingUp extends AppCompatActivity implements View.OnClickListener {
    //FECHA
    ImageButton btnDate;
    EditText editTextDate;
    private  int dd, mm, yy;
    //CIUDAD
    Spinner comboCity;
    //Usuario
    Spinner comboUser;
    //Oficio
    Spinner comboOficio;
    //descripcion
    MultiAutoCompleteTextView descripcion;

    //datos de usuario
    EditText Username, fName, lName, phone, date, CC, mEmail, mPassword, mPasswordComfirm;
    MultiAutoCompleteTextView  userDescripcion;
    String userType, city, oficio;

    Button btnRegister, btnBback;
    //fireBase
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        //FECHA
        btnDate = (ImageButton)findViewById(R.id.BtnDate);
        editTextDate = (EditText)findViewById(R.id.date);
        btnDate.setOnClickListener(this);

        //CIUDAD
        comboCity = (Spinner) findViewById(R.id.spinnerCiudad);

        ArrayAdapter<CharSequence> adapterCity = ArrayAdapter.createFromResource(this,
                R.array.Ciudad, android.R.layout.simple_spinner_item);
        comboCity.setAdapter(adapterCity);

        //Usuario
        comboUser = (Spinner) findViewById(R.id.spinnerUser);

        ArrayAdapter<CharSequence> adapterUser = ArrayAdapter.createFromResource(this,
                R.array.Usuario, android.R.layout.simple_spinner_item);
        comboUser.setAdapter(adapterUser);


        //Oficio
        comboOficio = (Spinner) findViewById(R.id.spinnerOficio);

        ArrayAdapter<CharSequence> adapterOficio = ArrayAdapter.createFromResource(this,
                R.array.Oficio, android.R.layout.simple_spinner_item);
        comboOficio.setAdapter(adapterOficio);

        //descripcion
        descripcion = (MultiAutoCompleteTextView)findViewById(R.id.editDescripcion);


        //visibilidad
        comboUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 2) {   //2 es operador
                    comboOficio.setVisibility(View.VISIBLE);//visinle
                    descripcion.setVisibility(View.VISIBLE);
                } else {
                    comboOficio.setVisibility(View.GONE);// INVISIBLE
                    descripcion.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //  tu código
            }
        });

        //obtener datos de usuario
        Username = findViewById(R.id.UserName);
        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        phone = findViewById(R.id.phone);
        date = findViewById(R.id.date);
        CC = findViewById(R.id.CC);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.Password);
        mPasswordComfirm = findViewById(R.id.comfirmPassword);
        userDescripcion = findViewById(R.id.editDescripcion);
        userType = comboUser.getSelectedItem().toString();;
        city = comboCity.getSelectedItem().toString();
        oficio = comboOficio.getSelectedItem().toString();

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                
            }
        });



    }

    //FECHA
    @Override
    public void onClick(View v) {
        if (v== btnDate){
            final Calendar c= Calendar.getInstance();

            dd = c.get(Calendar.DAY_OF_MONTH);
            mm = c.get(Calendar.MONTH);
            yy = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    editTextDate.setText(dayOfMonth+"/"+(month)+1 +"/"+year);
                }
            }
            ,dd, mm, yy);
            datePickerDialog.show();
        }


    }
}