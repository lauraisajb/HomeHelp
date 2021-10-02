package com.example.homehelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText eCorreo, ePassword;
    private Button eLogin;
    private String email ="";
    private String password ="";

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eCorreo = (EditText) findViewById(R.id.eCorreo);
        ePassword = (EditText) findViewById(R.id.ePassword);
        eLogin = (Button) findViewById(R.id.btnLogin);

        auth = FirebaseAuth.getInstance();

        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = eCorreo.getText().toString();
                password = ePassword.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()){
                    loginUser();
                }else {
                    if (email.isEmpty()){
                        Toast.makeText(MainActivity.this, "Ingrese su correo", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(MainActivity.this, "Ingrese la contraseña", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void loginUser(){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, activity_view_worker.class));

                }   
                else {
                    Toast.makeText(MainActivity.this, "No se pudo iniciar sesión. Compruebe los datos ingresados", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //SINGUP
    public void singUp(View view){
        Intent singUp = new Intent(this, SingUp.class);
        startActivity(singUp);
        finish();
    }
}