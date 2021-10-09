package com.example.homehelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class see_worker extends AppCompatActivity {

    //buttons
    ImageButton btnBack;

    //text
    TextView eUserName, eCity, eDescripcion, eJob;

    //id
    private  String seeWorkerId;

    //DB
    private DatabaseReference DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_worker);

        seeWorkerId = getIntent().getExtras().get("seeWorkerId").toString();
        Toast.makeText(this, "Visualizando "+seeWorkerId, Toast.LENGTH_SHORT).show();

        //DB
        DB = FirebaseDatabase.getInstance().getReference();
        //button
        btnBack = (ImageButton) findViewById(R.id.btnBackSW);
        //textView
        eUserName = (TextView) findViewById(R.id.UserNameSW);
        eJob = (TextView) findViewById(R.id.JobSW);
        eCity = (TextView) findViewById(R.id.CitySW);
        eDescripcion = (TextView) findViewById(R.id.textDescripcionSW);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(see_worker.this, WorkerList.class));
            }
        });
        getInfo();
    }

    private void getInfo(){
        String id = seeWorkerId;
        DB.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}