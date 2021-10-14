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

import org.jetbrains.annotations.NotNull;

import static java.lang.Integer.parseInt;

public class see_worker extends AppCompatActivity {

    //buttons
    ImageButton btnBack;

    //text
    TextView eUserName, eCity, eDescripcion, eJob, efecha;

    //datos
    String oficio, city;
    int calificacion;

    //id
    private  String seeWorkerId;

    //DB
    private DatabaseReference DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_worker);

        city = getIntent().getExtras().get("city").toString();
        oficio = getIntent().getExtras().get("oficio").toString();
        seeWorkerId = getIntent().getExtras().get("seeWorkerId").toString();

        //calificacion = parseInt( getIntent().getExtras().get("calificacion").toString());

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
        efecha = (TextView) findViewById(R.id.seeCreacionW);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( see_worker.this, WorkerList.class);
                intent.putExtra("oficio",oficio);
                intent.putExtra("city", city);
                //intent.putExtra("calificacion", calificacion);
                startActivity(intent);
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
                    String oficio = snapshot.child("Oficio").getValue().toString();
                    DB.child(oficio).child(id).addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull @NotNull DataSnapshot snapshot2) {
                             if(snapshot2.exists()){
                                 String userName = snapshot2.child("userName").getValue().toString();
                                 String job = snapshot2.child("Oficio").getValue().toString();
                                 String city = snapshot2.child("Ciudad").getValue().toString();
                                 String descripcion = snapshot2.child("descripcion").getValue().toString();
                                 String fecha = snapshot2.child("FechaCre").getValue().toString();

                                 eUserName.setText(userName);
                                 eJob.setText(job);
                                 eCity.setText(city);
                                 eDescripcion.setText(descripcion);
                                 efecha.setText(fecha);
                             }
                         }

                         @Override
                         public void onCancelled(@NonNull @NotNull DatabaseError error) {

                         }
                     });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}