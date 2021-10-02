package com.example.homehelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class activity_view_worker extends AppCompatActivity {

    //botones
    private ImageButton btnClose;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_worker);

        auth = FirebaseAuth.getInstance();
        btnClose = (ImageButton) findViewById(R.id.btnBack);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent( activity_view_worker.this, MainActivity.class));
                finish();
            }
        });

    }
}