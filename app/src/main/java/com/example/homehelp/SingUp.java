package com.example.homehelp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;

public class SingUp extends AppCompatActivity implements View.OnClickListener {
    ImageButton btnDate;
    EditText editTextDate;
    private  int dd, mm, yy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        btnDate = (ImageButton)findViewById(R.id.BtnDate);
        editTextDate = (EditText)findViewById(R.id.editTextDate);
        btnDate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v== btnDate){
            final Calendar c= Calendar.getInstance();

            dd = c.get(Calendar.DAY_OF_MONTH);
            mm = c.get(Calendar.MONTH);
            yy = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    editTextDate.setText(dayOfMonth+"/"+(month)+1 +"/"+year);
                }
            }
            ,dd, mm, yy);
            datePickerDialog.show();
        }
    }
}