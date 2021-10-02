package com.example.homehelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //SINGUP
    public void singUp(View view){
        Intent singUp = new Intent(this, Search_activity.class);
        startActivity(singUp);
    }
}