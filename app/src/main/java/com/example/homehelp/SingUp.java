package com.example.homehelp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SingUp extends AppCompatActivity implements View.OnClickListener {
    //FECHA
    ImageButton btnDate;
    EditText editTextDate;
    private int dd, mm, yy;
    //CIUDAD
    Spinner comboCity;
    //Usuario
    Spinner comboUser;
    //Oficio
    Spinner comboOficio;
    //descripcion
    MultiAutoCompleteTextView descripcion;

    //referenciar datos de usuario
    EditText eUserName, eFname, eLname, eEmail, eDir, ePassword, eComfPass, ePhone, descrip, efechNac;

    //variables de usuario
    String userName, fName, lName, email, dir, password, comfPass, phone, descripc, userType, city, ofice, imagen, fechNac, fechCrea;

    //botones
    Button btnRegister, btnBack;
    ImageButton btnImgUser;

    //coneccion
    FirebaseAuth auth;
    DatabaseReference database;

    //Oficio
    String Oficio = "Cliente";

    //fotos
    private static final int GALLERY_INTENT = 1;
    private  StorageReference storage;

    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        //coneccion
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance().getReference();
        //FECHA
        btnDate = (ImageButton) findViewById(R.id.BtnDate);
        editTextDate = (EditText) findViewById(R.id.eDateE);
        btnDate.setOnClickListener(this);

        //CIUDAD
        comboCity = (Spinner) findViewById(R.id.spinnerCiudadE);

        ArrayAdapter<CharSequence> adapterCity = ArrayAdapter.createFromResource(this,
                R.array.Ciudad, android.R.layout.simple_spinner_item);
        comboCity.setAdapter(adapterCity);

        //Usuario
        comboUser = (Spinner) findViewById(R.id.spinnerUserE);

        ArrayAdapter<CharSequence> adapterUser = ArrayAdapter.createFromResource(this,
                R.array.Usuario, android.R.layout.simple_spinner_item);
        comboUser.setAdapter(adapterUser);


        //Oficio
        comboOficio = (Spinner) findViewById(R.id.spinnerOficio);

        ArrayAdapter<CharSequence> adapterOficio = ArrayAdapter.createFromResource(this,
                R.array.Oficio, android.R.layout.simple_spinner_item);
        comboOficio.setAdapter(adapterOficio);

        //descripcion
        descripcion = (MultiAutoCompleteTextView) findViewById(R.id.editDescripcion);


        //visibilidad
        comboUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 2) {   //2 es operador
                    comboOficio.setVisibility(View.VISIBLE);//visinle
                    descripcion.setVisibility(View.VISIBLE);
                } else {
                    comboOficio.setVisibility(View.GONE);// INVISIBLE
                    descripcion.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //  tu código
            }
        });

        //referenciar usuarios
        eUserName = (EditText) findViewById(R.id.eUserNameE);
        eFname = (EditText) findViewById(R.id.eFnameE);
        eLname = (EditText) findViewById(R.id.eLnameE);
        eEmail = (EditText) findViewById(R.id.eEmailE);
        eDir = (EditText) findViewById(R.id.eDirE);
        ePassword = (EditText) findViewById(R.id.ePasswordE);
        eComfPass = (EditText) findViewById(R.id.eConfirmPassword);
        ePhone = (EditText) findViewById(R.id.ePhonoE);
        descrip = (EditText) findViewById(R.id.editDescripcion);
        efechNac = (EditText) findViewById(R.id.eDateE);
        //botones
        btnRegister = (Button) findViewById(R.id.btnActualizar);
        btnBack = (Button) findViewById(R.id.btnCancelE);
        btnImgUser = (ImageButton) findViewById(R.id.btnImgUserE);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingUp.this, MainActivity.class));
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                //recibir datos
                userName = eUserName.getText().toString();
                fName = eFname.getText().toString();
                lName = eLname.getText().toString();
                email = eEmail.getText().toString();
                password = ePassword.getText().toString();
                comfPass = eComfPass.getText().toString();
                dir = eDir.getText().toString();
                phone = ePhone.getText().toString();
                descripc = descrip.getText().toString();
                //fecha
                fechNac = efechNac.getText().toString();
                Date getfechCrea = new Date(System.currentTimeMillis());
                fechCrea = getfechCrea.toString();

                //combodatos
                userType = comboUser.getSelectedItem().toString();
                city = comboCity.getSelectedItem().toString();
                ofice = comboOficio.getSelectedItem().toString();

                //Validar datos

                //validar que esten llenos los campos
                if (fechNac.isEmpty()) {
                    Toast.makeText(SingUp.this, "Debe ingresar su fecha de nacimiento", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userName.isEmpty()) {
                    Toast.makeText(SingUp.this, "Debe asignar un nombre de usuario", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fName.isEmpty()) {
                    Toast.makeText(SingUp.this, "Debe ingresar su nombre", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (lName.isEmpty()) {
                    Toast.makeText(SingUp.this, "Debe  ingresar su apellido", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.isEmpty()) {
                    Toast.makeText(SingUp.this, "Debe ingresar su correo", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(SingUp.this, "Debe ingresar una contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 7) {
                    Toast.makeText(SingUp.this, "La contraseña debe tener 8 caracteres o mas", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(comfPass)) {
                    Toast.makeText(SingUp.this, "Las constraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (dir.isEmpty()) {
                    Toast.makeText(SingUp.this, "Debe ingresar su direccion", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.isEmpty()) {
                    Toast.makeText(SingUp.this, "Debe ingresar su telefono", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userType.equals("Tipo de Usuario")) {
                    Toast.makeText(SingUp.this, "Elija el tipo de Usuario", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (userType.equals("Operador")) {
                    if (ofice.equals("Oficio")) {
                        Toast.makeText(SingUp.this, "Debe elegir un oficio", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (descripc.isEmpty()) {
                        Toast.makeText(SingUp.this, "Debe ingresar la descripcion", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //si el tipo de usuario de usuario no es cliente, la variable Oficio cambia al tipo elegido
                    Oficio = ofice;
                }

                if (city.equals("Ciudad")) {
                    Toast.makeText(SingUp.this, "Debe elegir una ciudad", Toast.LENGTH_SHORT).show();
                    return;
                }

                registerUser();

            }
        });


        //subir foto
        btnImgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);//seleccionar una imagen de la galeria
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
                System.out.println("--------------btnIMG--------------------");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            StorageReference ref = storage.child("fotos").child(uri.getLastPathSegment());
            //StorageReference ref = storage.child("fotos");

            System.out.println("--------------if--------------------");


            ref.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                         imagen = downloadUri.toString();

                        Glide.with(SingUp.this)
                                .load(imagen)
                                .fitCenter()
                                .centerCrop()
                                .into(btnImgUser);
                        //FriendlyMessage friendlyMessage = new FriendlyMessage(null, mUsername, downloadUri.toString());
                        //mMessagesDatabaseReference.push().setValue(friendlyMessage);
                    } else {
                        Toast.makeText(SingUp.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            /*
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final StorageReference ref = storage.child("fotos");
                    taskSnapshot = ref.putFile(imagen);

                    System.out.println("--------------onSuccess--------------------");
                   // Uri descargarFoto = taskSnapshot.getStorage().child("fotos").getDownloadUrl().getResult();
                    Task<Uri> descargarFoto = taskSnapshot.getStorage().getDownloadUrl();
                    System.out.println(descargarFoto);
                    System.out.println("--------------descargarFoto--------------------");


                    imagen = descargarFoto.toString();

                    Glide.with(SingUp.this)
                            .load(descargarFoto)
                            .fitCenter()
                            .centerCrop()
                            .into(btnImgUser);
                    Toast.makeText(SingUp.this, "Foto agregada exitosamente", Toast.LENGTH_LONG).show();
                }

            });*/
        }
    }

    ///
    public void registerUser() {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    // fechNac = (Date) editTextDate.getText();
                    Map<String, Object> users = new HashMap<>();
                    users.put("Oficio", Oficio);

                    Map<String, Object> map = new HashMap<>();
                    map.put("userName", userName);
                    map.put("nombre", fName);
                    map.put("apellido", lName);
                    map.put("email", email);
                    map.put("direccion", dir);
                    map.put("phone", phone);
                    map.put("Ciudad", city);
                    map.put("FechaNac", fechNac);
                    map.put("FechaCre", fechCrea);
                     map.put("imagen", imagen);

                    if (userType.equals("Operador")) {
                        map.put("Tipo", userType);
                        map.put("Oficio", ofice);
                        map.put("descripcion", descripc);
                    }

                    String id = auth.getCurrentUser().getUid();

                    database.child("Users").child(id).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {

                                database.child(Oficio).child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task3) {
                                        if (task3.isSuccessful()) {
                                            Toast.makeText(SingUp.this, "registro exitoso", Toast.LENGTH_SHORT).show();

                                            if (userType.equals("Operador")) {
                                                startActivity(new Intent(SingUp.this, activity_view_worker.class));
                                            }
                                            if (userType.equals("Cliente")) {
                                                startActivity(new Intent(SingUp.this, activity_view_customer.class));
                                            }
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(SingUp.this, "No se pudo completar el registro del User", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(SingUp.this, "no se pudo realizar el registro", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //FECHA
    @Override
    public void onClick(View v) {
        if (v == btnDate) {
            final Calendar c = Calendar.getInstance();
            dd = c.get(Calendar.DAY_OF_MONTH);
            mm = c.get(Calendar.MONTH);
            yy = c.get(Calendar.YEAR);
            ;

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    editTextDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                }
            }
                    , dd, mm, yy);
            datePickerDialog.show();
        }


        btnImgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SubirFoto();
            }
        });

    }

}