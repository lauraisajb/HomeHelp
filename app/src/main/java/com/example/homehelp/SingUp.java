package com.example.homehelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.Struct;
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
    EditText mUsername, mfName, mlName, mPhone,  mDate, mCC, mEmail, mPassword, mPasswordComfirm;
    MultiAutoCompleteTextView  mUserDescripcion;
    String userType, city, oficio;

    Button btnRegistrar, btnBback;
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
        mUsername = findViewById(R.id.UserName);
        mfName = findViewById(R.id.fName);
        mlName = findViewById(R.id.lName);
        mPhone = findViewById(R.id.phone);
        mCC = findViewById(R.id.CC);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.Password);
        mPasswordComfirm = findViewById(R.id.comfirmPassword);
        mUserDescripcion = findViewById(R.id.editDescripcion);

        userType = comboUser.getSelectedItem().toString();;
        city = comboCity.getSelectedItem().toString();
        oficio = comboOficio.getSelectedItem().toString();

        //botones
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnBback = findViewById(R.id.btnBack);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);


        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        btnRegistrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String Username = mUsername.getText().toString().trim();
                String fName = mfName.getText().toString().trim();
                String lName = mlName.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();
                String CC = mCC.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String passwordComfirm = mPasswordComfirm.getText().toString().trim();
                String userDescription = mUserDescripcion.getText().toString().trim();
                mDate = editTextDate;
                //validaciones iniciales

                if(TextUtils.isEmpty(Username)){
                    mUsername.setError("¡Nombre de Usuario Requerido!");
                    return;
                }
                if(TextUtils.isEmpty(fName)){
                    mfName.setError("¡Primer Nombre Requerido!");
                    return;
                }
                if(TextUtils.isEmpty(lName)){
                    mlName.setError("¡Primer Apellido Requerido!");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    mPhone.setError("¡Telefono Requerido!");
                    return;
                }
                if(TextUtils.isEmpty(CC)){
                    mCC.setError("¡C.C Requerida!");
                    return;
                }
                if(TextUtils.isEmpty(userDescription)){
                    mUserDescripcion.setError("¡Descripción Requerida!");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("¡Correo Requerido!");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("¡Contraseña Requerida!");
                    return;
                }
                if(TextUtils.isEmpty(passwordComfirm)){
                    mPassword.setError("¡Comfirmación de Contraseña Requerida!");
                    return;
                }

                if (!passwordComfirm.equals(passwordComfirm)){
                    mPasswordComfirm.setError("¡LAS CONSTRASEÑAS NO COINCIDEN!");
                    return;
                }

                if(password.length() < 8){
                    mPassword.setError("¡Contraseña debe contar con almenos 8 caracteres!");
                    return;
                }

                if (TextUtils.isEmpty(mDate.toString())){
                    ///debemos averiguar que sea mayor de edad
                    editTextDate.setError("¡Fecha Requerida!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //registrando usuario en la DB firebase
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull  Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SingUp.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            Toast.makeText(SingUp.this, "Error "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

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