package com.example.homehelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class activity_view_worker extends AppCompatActivity {

    //botones
    private ImageButton btnClose;

    //textos
    TextView eUserName, eCity, eDescripcion, eJob, eFcreacion;

    ImageView eImagen;

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
        btnClose = (ImageButton) findViewById(R.id.btnCloseW);
        //textView
        eUserName = (TextView) findViewById(R.id.textName);
        eJob = (TextView) findViewById(R.id.textJob);
        eCity = (TextView) findViewById(R.id.CitySW);
        eDescripcion = (TextView) findViewById(R.id.textDescripcionSW);
        eFcreacion = (TextView)findViewById(R.id.seeCreacionW);
        eImagen  = (ImageView)findViewById(R.id.imgUserW);


        getInfo();

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(activity_view_worker.this, MainActivity.class));
            }
        });


    }

    private void getInfo(){
        String id = auth.getCurrentUser().getUid();
        DB.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String Oficio = snapshot.child("Oficio").getValue().toString();
                    DB.child(Oficio).child(id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot2) {
                           if(snapshot2.exists()){
                               String userName = snapshot2.child("userName").getValue().toString();
                               String job = snapshot2.child("Oficio").getValue().toString();
                               String city = snapshot2.child("Ciudad").getValue().toString();
                               String descripcion = snapshot2.child("descripcion").getValue().toString();
                               String fechCrea = snapshot2.child("FechaCre").getValue().toString();
                               String imagen = snapshot2.child("imagen").getValue().toString();

                               eUserName.setText(userName);
                               eJob.setText(job);
                               eCity.setText(city);
                               eDescripcion.setText(descripcion);
                               eFcreacion.setText(fechCrea);
                               Glide.with(activity_view_worker.this)
                                       .load(imagen)
                                       .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                                       .circleCrop()
                                       .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                                       .into(eImagen); 
                           }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }
}