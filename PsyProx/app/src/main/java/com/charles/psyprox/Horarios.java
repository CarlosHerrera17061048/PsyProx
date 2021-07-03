package com.charles.psyprox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Horarios extends AppCompatActivity {
    TextView txtNombreIns;
    String Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios);
       SharedPreferences preferences=  getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        Email = preferences.getString("email","");
        txtNombreIns = findViewById(R.id.txtNombreIns);

        Log.wtf("as",Email);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Usuarios").document(Email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                txtNombreIns.setText("Bienvenido " + documentSnapshot.getString("Nombre: ")+"!!!");
            }
        });


    }
}