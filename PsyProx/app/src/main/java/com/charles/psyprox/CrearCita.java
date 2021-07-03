package com.charles.psyprox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CrearCita extends AppCompatActivity {

    TextView txtDatosA,txtDatps2,txtdatos4,txtdatos5;
    ListView listView;
    String Especialistas ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cita);



        txtDatosA = findViewById(R.id.txtDatosA);
        txtDatps2 = findViewById(R.id.txtdatos2);
        txtdatos4 = findViewById(R.id.txtdatos4);
        txtdatos5 = findViewById(R.id.txtdatos5);
        FirebaseFirestore db =FirebaseFirestore.getInstance();



        db.collection("Usuarios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                  //  for(int j=0;j<=3;j++ ){
                        for (QueryDocumentSnapshot document : task.getResult()) {



                            Especialistas = document.getId() + " " + document.getData();
                            if (document.getId().equals("carlosherrera99.cih@gmail.com")){
                                txtDatosA.setText("Correo:" + document.getId()+ "\n" + "Nombre: "+ document.get("Nombre: ")+ " "
                                + document.get("Apellido: ") +"\n"+  "Telefono: "+
                                        document.get("Telefono: ")+ " \n" +     "Edad: "+
                                        document.get("Edad: "));
                            }else if(document.getId().equals("correoprueba@gmail.com")){
                                txtDatps2.setText("Correo:" + document.getId()+ "\n" + "Nombre: "+ document.get("Nombre: ")+ " "
                                        + document.get("Apellido: ") +"\n"+  "Telefono: "+
                                        document.get("Telefono: ")+ " \n" +     "Edad: "+
                                        document.get("Edad: "));

                            }else if(document.getId().equals("hiramquintero@gmail.com")){
                                txtdatos4.setText("Correo:" + document.getId()+ "\n" + "Nombre: "+ document.get("Nombre: ")+ " "
                                        + document.get("Apellido: ") +"\n"+  "Telefono: "+
                                        document.get("Telefono: ")+ " \n" +     "Edad: "+
                                        document.get("Edad: "));

                            }else if(document.getId().equals("miguelvaldez@gmail.com")){
                                txtdatos5.setText("Correo:" + document.getId()+ "\n" + "Nombre: "+ document.get("Nombre: ")+ " "
                                        + document.get("Apellido: ") +"\n"+  "Telefono: "+
                                        document.get("Telefono: ")+ " \n" +     "Edad: "+
                                        document.get("Edad: " ));

                            }

                            Log.wtf("s",Especialistas);


                        }
                   // }

                }
            }
        });


    }
}