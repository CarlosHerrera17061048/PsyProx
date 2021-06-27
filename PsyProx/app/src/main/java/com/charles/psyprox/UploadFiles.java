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
    Button btnCargar1,btnCargar2;
    StorageReference mStorage;
    String Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_files2);

        Email = getIntent().getExtras().getString("Email","defaultKey");

      mStorage = FirebaseStorage.getInstance().getReference();
    btnCargar1 = findViewById(R.id.btnCargar1);
    btnCargar2= findViewById(R.id.btnCargar2);
    imgCedula = findViewById(R.id.imgCedu);
    imgTitu = findViewById(R.id.imgTitu);

    btnCargar1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent =  new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/");
            startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion"),10);
        }
    });

    btnCargar2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent =  new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/");
            startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion"),11);
        }
    });
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);










       if(resultCode==RESULT_OK){
            Uri path=data.getData();

           if (requestCode == 11) {

                imgTitu.setImageURI(path);
                StorageReference storageReference = mStorage.child(Email).child(path.getLastPathSegment());

                storageReference.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(UploadFiles.this,"Carga Exitosa",Toast.LENGTH_LONG);
                    }
                });
            } else if (requestCode == 10) {
                imgCedula.setImageURI(path);
                StorageReference storageReference = mStorage.child(Email).child(path.getLastPathSegment());
                storageReference.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(UploadFiles.this,"Carga Exitosa",Toast.LENGTH_LONG);
                    }
                });
            }

        }
    }






}