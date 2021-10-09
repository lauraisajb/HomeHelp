package com.example.homehelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class WorkerList extends AppCompatActivity {

    RecyclerView recyclerView;
    OperadoresAdapter operadoresAdapter;

    //String
    String ciudad, oficio;
    int calificacion;

    //botones
    ImageButton btnBackWL;



    //Db
    private DatabaseReference DB;

    private CollectionReference cr;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnBackWL = (ImageButton)findViewById(R.id.btnBackWL);

        ciudad = getIntent().getExtras().get("city").toString();
        oficio = getIntent().getExtras().get("oficio").toString();
        calificacion = parseInt( getIntent().getExtras().get("calificacion").toString());

        System.out.println("oficion: "+ oficio+ " Ciudad"+ ciudad);
        DB = FirebaseDatabase.getInstance().getReference().child("User");

        //Query filtro= DB;
        //filtro.equalTo("Ciudad", ciudad);
        //filtro.equalTo("Oficio",oficio);

        //DB.equalTo("Ciudad", ""+ciudad+"").equalTo("Oficio",""+oficio+"");

        FirebaseRecyclerOptions<Operadores> options =
                new FirebaseRecyclerOptions.Builder<Operadores>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users"), Operadores.class)
                        .build(); //consulta


        operadoresAdapter = new OperadoresAdapter( options,this);
        recyclerView.setAdapter(operadoresAdapter);





        btnBackWL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent( WorkerList.this, Search_activity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        operadoresAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        operadoresAdapter.startListening();
    }
}