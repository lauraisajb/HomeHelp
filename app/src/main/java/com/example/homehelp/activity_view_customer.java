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

public class activity_view_customer extends AppCompatActivity {

    //botones
    private ImageButton btnClose, btnSearch;
    ImageView eImagen;

    //FireBase
    private FirebaseAuth auth;
    private DatabaseReference DB;
    //textView
    private TextView eUserName, eCity, eFcreacion;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);



        //firebase
        auth = FirebaseAuth.getInstance();
        DB = FirebaseDatabase.getInstance().getReference();
        //button
        btnClose = (ImageButton) findViewById(R.id.close);
        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        //foto
        eImagen = (ImageView) findViewById(R.id.imgUserC);
        //textView
        eUserName = (TextView) findViewById(R.id.textUserName);
        eCity = (TextView) findViewById(R.id.textCityb);
        eFcreacion = (TextView)findViewById(R.id.TVcreacionC);



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( activity_view_customer.this, Search_activity.class));
            }
        });


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(activity_view_customer.this, MainActivity.class));
                finish();
            }
        });



        getInfo();
    }

    private void getInfo(){
        id = auth.getCurrentUser().getUid();
        DB.child("Cliente").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String userName = snapshot.child("userName").getValue().toString();
                    String city = snapshot.child("Ciudad").getValue().toString();
                    String fechCrea = snapshot.child("FechaCre").getValue().toString();
                    String imagen = snapshot.child("imagen").getValue().toString();

                    eUserName.setText(userName);
                    eCity.setText(city);
                    eFcreacion.setText(fechCrea);
                    Glide.with(activity_view_customer.this)
                            .load(imagen)
                            .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                            .circleCrop()
                            .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                            .into(eImagen);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}