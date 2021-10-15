package com.example.homehelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class config extends AppCompatActivity {

    //referenciar datos de usuario
    EditText eUserName, eFname, eLname, eEmail, eDir, ePassword, eComfPass, ePhone, descrip;

    //variables de usuario
    String userName, fName, lName, dir, password, comfPass, phone, descripc, userType, city, ofice, imagen, id, oficio;

    //botones
    Button btnRegister, btnBack;
    ImageButton btnImgUser;

    //CIUDAD
    Spinner comboCity;

    Button btnActualice, btnBackE;

    //FireBase
    private FirebaseAuth auth;
    private DatabaseReference DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);


        id = getIntent().getExtras().get("id").toString();
        oficio = getIntent().getExtras().get("Oficio").toString();

        //firebase
        auth = FirebaseAuth.getInstance();
        DB = FirebaseDatabase.getInstance().getReference();

        comboCity = (Spinner) findViewById(R.id.spinnerCiudadE);
        eUserName = (EditText) findViewById(R.id.eUserNameE);
        eFname = (EditText) findViewById(R.id.eFnameE);
        eLname = (EditText) findViewById(R.id.eLnameE);
        eEmail = (EditText) findViewById(R.id.eEmailE);
        eDir = (EditText) findViewById(R.id.eDirE);
        ePassword = (EditText) findViewById(R.id.ePasswordE);
        eComfPass = (EditText) findViewById(R.id.eConfirmPassword);
        ePhone = (EditText) findViewById(R.id.ePhonoE);
        descrip = (EditText) findViewById(R.id.editDescripcion);
        btnRegister = (Button) findViewById(R.id.btnActualizar);
        btnBack = (Button) findViewById(R.id.btnCancelE);
        btnImgUser = (ImageButton) findViewById(R.id.btnImgUserE);


        getInfo();



        eUserName.setText(userName);
        eFname.setText(fName);
        eLname.setText(lName);
        eDir.setText(dir);
        ePhone.setText(phone);




        DB.child(oficio).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    Map<String, Object> map = new HashMap<>();
                    map.put("userName", userName);
                    map.put("nombre", fName);
                    map.put("apellido", lName);
                    map.put("direccion", dir);
                    map.put("phone", phone);
                    map.put("Ciudad", city);
                    map.put("imagen", imagen);

                    if (!userType.equals("Cliente")) {
                        map.put("Oficio", ofice);
                        map.put("descripcion", descripc);
                    }

                    DB.child(oficio).child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task3) {
                            if (task3.isSuccessful()) {
                                Toast.makeText(config.this, "registro exitoso", Toast.LENGTH_SHORT).show();

                                if (oficio.equals("Operador")) {
                                    startActivity(new Intent(config.this, activity_view_worker.class));
                                }
                                if (oficio.equals("Cliente")) {
                                    startActivity(new Intent(config.this, activity_view_customer.class));
                                }
                            }
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void  getInfo(){
        //id = auth.getCurrentUser().getUid();
        //String Oficio;



        DB.child(oficio).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    userName = snapshot.child("userName").getValue().toString();
                    fName= snapshot.child("nombre").getValue().toString();
                    lName = snapshot.child("apellido").getValue().toString();
                    dir= snapshot.child("direccion").getValue().toString();
                    imagen = snapshot.child("imagen").getValue().toString();
                    city = snapshot.child("city").getValue().toString();
                    phone = snapshot.child("phone").getValue().toString();

                    if (!userType.equals("Cliente")) {
                        ofice = snapshot.child("Oficio").getValue().toString();
                        descripc = snapshot.child("descripcion").getValue().toString();
                    }

                    eUserName.setText(userName);
                    Glide.with(config.this)
                            .load(imagen)
                            .load(imagen)
                            .fitCenter()
                            .centerCrop()
                            .into(btnImgUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}