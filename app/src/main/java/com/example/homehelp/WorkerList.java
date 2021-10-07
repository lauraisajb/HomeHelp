package com.example.homehelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class WorkerList extends AppCompatActivity {

    RecyclerView recyclerView;
    OperadoresAdapter operadoresAdapter;

    //botones
    ImageButton btnBackWL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnBackWL = (ImageButton)findViewById(R.id.btnBackWL);

        FirebaseRecyclerOptions<Operadores> options =
                new FirebaseRecyclerOptions.Builder<Operadores>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users"), Operadores.class)
                        .build();


        operadoresAdapter = new OperadoresAdapter(options);
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