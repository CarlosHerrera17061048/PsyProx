package com.charles.psyprox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class LastSteps extends AppCompatActivity {
    EditText edtDireccion, edtInfoExtra;
    Button btnAceptarLS;
    CheckBox  cbInfo;
    String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_steps);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        edtDireccion = findViewById(R.id.edtDireccion);
        edtInfoExtra = findViewById(R.id.edtInfoExtra);
        edtInfoExtra.setEnabled(false);
        btnAceptarLS = findViewById(R.id.btnAceptarLS);
        cbInfo = findViewById(R.id.cbInfo);

        SharedPreferences preferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        Email = preferences.getString("email","asd");
        Log.wtf("Email",Email);

        //Email = getIntent().getExtras().getString("Email","defaultKey");

      /*  cbInfo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cbInfo.isChecked()){
                    cbInfo.setEnabled(true);
                }
            }
        });*/
    }
}