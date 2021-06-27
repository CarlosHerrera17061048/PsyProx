    package com.charles.psyprox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
    StorageReference mStorage;
    ImageView fotoPerfil;
    //Usaremos Cloud firebase como base de datos para guardar los datos de cada usuario
   // Creamos la instancia para usar la base de datos de Firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__creation);

        mStorage = FirebaseStorage .getInstance().getReference();

        fotoPerfil = findViewById(R.id.fotoPerfil);
        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent1 = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent1.setType("image/");
                startActivityForResult(intent1.createChooser(intent1,"Seleccione la Aplicacion"),10);
            }

        });



        //Aqui recibimos los extras que traemos de la clase LoginChoice.kt
        Email = getIntent().getExtras().getString("email","defaultKey");
        Log.wtf("Email",Email);
        Seleccion = getIntent().getExtras().getString("tipoUsuario","UsuarioNormal");
        Log.wtf("TipoUsuario",Seleccion);

        // Inicializamos componentes
        rdgSex = findViewById(R.id.rdgSex);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellidos);
        edtTelefono = findViewById(R.id.edtTelefono);
        edtFecha = findViewById(R.id.edtFecha);

        intent = new Intent(this,UploadFiles.class);
        // Usamos el siguiente evento para saber cual radiobutton ha sido seleccionado
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
// Evento on click listener que usaremos para subir la informacios a la base de datos usando un objeto Map ya que estos asocian una clave con un valor
// y asi será mas facil identificarlos.
btnAceptar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //Creacion  del objeto Map
        // Agregamos los datos obtenidos conviertiendolos a String
        // Excepto la clave Acceso la cual es tipo Boleano
        Map<String, Object> Usuario = new HashMap<>();
        Usuario.put("Nombre: ",txtNombre.getText().toString());
        Usuario.put("Apellido: ",txtApellido.getText().toString());
        Usuario.put("Telefono: ",edtTelefono.getText().toString());
        Usuario.put("Edad: ",edtFecha.getText().toString());
        Usuario.put("Sexo: ",Sexo);
        Usuario.put("Acceso: ", false);

        //Creacion de la Coleccion Usuarios
        db.collection("Usuarios")
                //Creación Documento Email el cual servira para identificar al usuario
                .document(Email).set(Usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
            //Si antes en el activity LoginChoice escogieron Especialista los mandara a un activity
           //  en el cual tendrá que subir ciertos archivos para corroborar que es
          //un especialista de verdad ademas enviamos un extra con la informacion del Email
        // y en caso de haber seleccionado Paciente los enviara a el activity principal
        if(Seleccion.equals("Especialista")){
            intent.putExtra("Email", Email);
        startActivity(intent);
                    }else {

        }


    }
});



    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(resultCode==RESULT_OK){
                Uri path=data.getData();
                if(requestCode==10){
                    fotoPerfil.setImageURI(path);
                    StorageReference storageReference = mStorage.child(Email).child(path.getLastPathSegment());
                    storageReference.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(),"Imagen subida exitosamente",Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Ha habido un error intentelo de nuevo",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }
    }

