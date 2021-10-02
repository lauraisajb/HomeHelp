package com.example.homehelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_view_worker extends AppCompatActivity {

    //botones
    private ImageButton btnClose;

    //textos
    TextView eUserName, eCity, eDescripcion, eJob;

    //FireBase
    private FirebaseAuth auth;
    private DatabaseReference DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_worker);

        //firebase
        auth = FirebaseAuth.getInstance();
        DB = FirebaseDatabase.getInstance().getReference();
        //button
        btnClose = (ImageButton) findViewById(R.id.btnBack);
        //textView
        eUserName = (TextView) findViewById(R.id.textName);
        eJob = (TextView) findViewById(R.id.textJob);
        eCity = (TextView) findViewById(R.id.textCity);
        eDescripcion = (TextView) findViewById(R.id.textDescripcion);


        getInfo();

        /*
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent( activity_view_worker.this, MainActivity.class));
            }
        });
        */
    }

    private void getInfo(){
        String id = auth.getCurrentUser().getUid();
        DB.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String userName = snapshot.child("userName").getValue().toString();
                    String job = snapshot.child("Oficio").getValue().toString();
                    String city = snapshot.child("Ciudad").getValue().toString();
                    String descripcion = snapshot.child("descripcion").getValue().toString();

                    eUserName.setText(userName);
                    eJob.setText(job);
                    eCity.setText(city);
                    eDescripcion.setText(descripcion);
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }
}