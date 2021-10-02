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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText eCorreo, ePassword;
    private Button eLogin;
    private String email = "";
    private String password = "";
    private String tipo="";

    //firebase
    private FirebaseAuth auth;
    private DatabaseReference DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eCorreo = (EditText) findViewById(R.id.eCorreo);
        ePassword = (EditText) findViewById(R.id.ePassword);
        eLogin = (Button) findViewById(R.id.btnLogin);

        //firebase
        auth = FirebaseAuth.getInstance();
        DB = FirebaseDatabase.getInstance().getReference();


        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = eCorreo.getText().toString();
                password = ePassword.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    login();
                } else {
                    if (email.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Ingrese su correo", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MainActivity.this, "Ingrese la contraseña", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void login() {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override

            public void onComplete(@NonNull Task<AuthResult> task) {

                System.out.println("------------------------"+tipo);
                if (task.isSuccessful()) {
                    getInfo();
                    System.out.println("------------------"+tipo);
                    if(tipo.equals("Cliente")){

                        Toast.makeText(MainActivity.this, "Iniciando sesión de Cliente", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, activity_view_customer.class));
                        finish();
                    }

                    if(tipo.equals("Operador")){
                        Toast.makeText(MainActivity.this, "Iniciando sesión de Operador", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, activity_view_worker.class));
                        finish();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "No se pudo iniciar sesión. Compruebe los datos ingresados", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    /*
     public void loginUOperador() {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(MainActivity.this, activity_view_worker.class));
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "No se pudo iniciar sesión. Compruebe los datos ingresados", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

     */
    private void getInfo(){
        String id = auth.getCurrentUser().getUid();
        DB.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("------------------"+tipo);
                if(snapshot.exists()){
                    tipo = snapshot.child("Tipo").getValue().toString();
                    System.out.println("------------------"+tipo);
                    /*if(tipo.equals("Cliente")){
                        loginCliente();
                        Toast.makeText(MainActivity.this, "Iniciando sesión de Cliente", Toast.LENGTH_SHORT).show();
                    }else {
                        loginUOperador();
                        Toast.makeText(MainActivity.this, "Iniciando sesión de Operador", Toast.LENGTH_SHORT).show();
                    }*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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