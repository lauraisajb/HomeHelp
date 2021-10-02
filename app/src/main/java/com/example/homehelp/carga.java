package com.example.homehelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class carga extends AppCompatActivity {

    //FireBase
    private FirebaseAuth auth;
    private DatabaseReference DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga);

        auth = FirebaseAuth.getInstance();
        DB = FirebaseDatabase.getInstance().getReference();
        getInfo();
    }

    private void getInfo(){
        String id = auth.getCurrentUser().getUid();
        DB.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String tipo = snapshot.child("Tipo").getValue().toString();

                    if(tipo.equals("Cliente")){
                        Toast.makeText(carga.this, "Iniciando sesión de Cliente", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(carga.this, activity_view_customer.class));
                        finish();
                    }else {
                        Toast.makeText(carga.this, "Iniciando sesión de Operador\"", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(carga.this, activity_view_worker.class));
                        finish();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}