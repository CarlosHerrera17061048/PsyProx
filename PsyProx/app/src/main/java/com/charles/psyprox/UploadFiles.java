package com.charles.psyprox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadFiles extends AppCompatActivity {
    ImageView imgCedula,imgTitu;
    Button btnCargar1,btnCargar2,btnAceptar;
    //Creamos una varable tipo StorageReference para despues inicializar una instancia
    //Para usar Firebase Storage
    StorageReference mStorage;
    String Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_files2);
        // Obtenemos el valor de Email
        Email = getIntent().getExtras().getString("Email","defaultKey");

        //Creamos una instancia para subir archivos a Firrebase Storage en el cual podemos almacenar Imagenes
      mStorage = FirebaseStorage.getInstance().getReference();
      //Inicializacion de variables
    btnCargar1 = findViewById(R.id.btnCargar1);
    btnCargar2= findViewById(R.id.btnCargar2);
    btnAceptar = findViewById(R.id.btnAcept);
    imgCedula = findViewById(R.id.imgCedu);
    imgTitu = findViewById(R.id.imgTitu);

    //Desactivamos Boton aceptar para que no puedan pasar de layout sin haber subido antes las imagenes
    btnAceptar.setEnabled(false);

    //Evento onClickListener para iniciar un Intent el cual nos permitira abrir la galeria del telefono
    btnCargar1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent =  new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/");
            //Llamamos al activity for result y le pasamos  los parametros Intent titulo y el requestCode para este boton
            startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion"),10);
        }
    });

    btnCargar2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent =  new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/");
            //Llamamos al activity for result y le pasamos  los parametros Intent titulo y el requestCode para este boton

            startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion"),11);
        }
    });
    }




    //Aqui usaremos el OnActivityResult para subir las imagenes al FirebaseStorage
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        //if con el cual sabremos si se abrio con exito la galeria y si es correcto copiaremos
        // el path de la imagen seleccionada y lo pondremos en una variable llamada path de tipo Uri
       if(resultCode==RESULT_OK){
            Uri path=data.getData();

            // if para saber que boton esta pidiendo la imagen
           //11 Boton para cargar la imagen del titulo y 10 para el boton de la imagen Cedula
           if (requestCode == 11) {

               //Agregamos la imagen a el imageview para mostrarla en el activity
                imgTitu.setImageURI(path);

                //Creamos una Referencia a la instancia previamente creada
               // Le agregamos una ubicacion mas abajo para guardar las imagenes estas se llamaran como el email
               //para que sea mas facil buscar las imagenes
                StorageReference storageReference = mStorage.child(Email).child(path.getLastPathSegment());

                storageReference.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //Aqui usamos el evento OnSucces para habilitar el boton en caso de que la imagen se haya subido con exito
                      btnAceptar.setEnabled(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        btnAceptar.setEnabled(false);
                    }
                });

            } else if (requestCode == 10) {
                imgCedula.setImageURI(path);
                StorageReference storageReference = mStorage.child(Email).child(path.getLastPathSegment());
                storageReference.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       btnAceptar.setEnabled(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        btnAceptar.setEnabled(false);
                    }
                });
            }

        }
    }



    //Usamos el evento onClick en el boton de aceptar para lanzar el siguiente activity
    // y un putExtra para enviar el Email
public void Aceptar (View v){
Intent intent;
intent= new Intent(this,WaitRoom.class);
intent.putExtra("Email",Email);

startActivity(intent);

}

}