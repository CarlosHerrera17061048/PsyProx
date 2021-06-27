    package com.charles.psyprox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

    public class Profile_Creation extends AppCompatActivity {
    EditText txtNombre,txtApellido,edtTelefono,edtFecha;
    Button btnAceptar;
    String Email,Sexo,Seleccion;
    RadioGroup rdgSex;
    Intent intent;


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__creation);

        Email = getIntent().getExtras().getString("email","defaultKey");
        Log.wtf("Email",Email);
        Seleccion = getIntent().getExtras().getString("tipoUsuario","UsuarioNormal");
        Log.wtf("TipoUsuario",Seleccion);


        rdgSex = findViewById(R.id.rdgSex);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellidos);
        edtTelefono = findViewById(R.id.edtTelefono);
        edtFecha = findViewById(R.id.edtFecha);

        intent = new Intent(this,UploadFiles.class);
        rdgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radBtnFem:
                        Sexo = "Femenino";
                        break;
                    case R.id.radBtnMasc:
                        Sexo = "Masculino";
                        break;
                    default:
                }
            }
        });


btnAceptar =  findViewById(R.id.btnAceptar);
btnAceptar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Map<String, Object> Usuario = new HashMap<>();
        Usuario.put("Nombre: ",txtNombre.getText().toString());
        Usuario.put("Apellido: ",txtApellido.getText().toString());
        Usuario.put("Telefono: ",edtTelefono.getText().toString());
        Usuario.put("Fecha de Nacimiento: ",edtFecha.getText().toString());
        Usuario.put("Sexo: ",Sexo);
        Usuario.put("Acceso: ", false);


        db.collection("Usuarios").document(Email).set(Usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        if(Seleccion.equals("Especialista")){
            intent.putExtra("Email", Email);
        startActivity(intent);
                    }else {

        }


    }
});



    }


}

