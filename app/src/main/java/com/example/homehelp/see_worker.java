package com.example.homehelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class see_worker extends AppCompatActivity {


    //buttons
    ImageButton btnBack;
    ImageButton sendMsj;

    //text
    TextView eUserName, eCity, eDescripcion, eJob, efecha, eNombreAp;
    ImageView eImagen;
    //datos
    String oficio, city, telefono;

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
        eImagen  = (ImageView)findViewById(R.id.imgUserSW);
        sendMsj = (ImageButton)findViewById(R.id.mensaje);
        eNombreAp = (TextView)findViewById(R.id.nombreApellidoSW);

        //contactar whathsapp

        sendMsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = "Hola, vi tu servicio en HomeHelp y me gustaria contratarte";

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_VIEW);
                String uri = "whatsapp://send?phone=57"+telefono+"&text="+mensaje;
                sendIntent.setData(Uri.parse(uri));
                startActivity(sendIntent);
            }
        });

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
                                String imagen = snapshot2.child("imagen").getValue().toString();
                                String nombre = snapshot2.child("nombre").getValue().toString();
                                String apellido = snapshot2.child("apellido").getValue().toString();
                                telefono = snapshot2.child("phone").getValue().toString();

                                eUserName.setText(userName);
                                eJob.setText(job);
                                eCity.setText(city);
                                eDescripcion.setText(descripcion);
                                efecha.setText(fecha);
                                eNombreAp.setText(nombre+" "+apellido);
                                Glide.with(see_worker.this)
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
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}