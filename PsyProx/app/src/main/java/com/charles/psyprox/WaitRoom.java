package com.charles.psyprox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class WaitRoom extends AppCompatActivity {
    // Usamos la api de Lottie para crear una variable tipo LootieAnimationView
    LottieAnimationView lottieAnimationView;

    String Email;
    Button btnContinuar;

    //Variable tipo Boleano En falso  el cual eventualmente
    //Cambiara dependiendo de si el servidor manda una respuesta
    //tipo true
    Boolean Acceso = false;

    //Hilo llamado estado;
    Thread estado;

    //Usamos un handlerMessage para cambiar el activity
    //Cambiamos la animacion de waaait a completed
    //Activamos el boton de continuar
    Handler handlerimg = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            lottieAnimationView = findViewById(R.id.animation_view);
            lottieAnimationView.setAnimation(R.raw.completed);
            lottieAnimationView.playAnimation();
            btnContinuar.setEnabled(true);

        }
    };
    //Nos conectamos a la Instancia de FirestoreDatabase
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //Creamos el metodo onDestroy para finalizar el hilo
    @Override
    protected void onDestroy() {
        super.onDestroy();
        estado.interrupt();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_room);


        //Inicializamos el boton y lo deshabilitamos
        btnContinuar = findViewById(R.id.btnContinuar);
        btnContinuar.setEnabled(false);

        //Conseguimos el valor Email
        Email = getIntent().getExtras().getString("Email","defaultKey");

            //Usamos el hilo estado para checar cada segundo si el estado de la clave acceso se ha modificado

            estado = new Thread(){
                @Override
                public void run() {
                    super.run();
                    while(true){
                        try {
                            //Aqui llamamos a la collecion "Usuarios" al documento "Email"
                            db.collection("Usuarios").document(Email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()){
                                        //Aqui cambiamos el estado de la variable Acceso a true si es que en el
                                        // servidor se cambio a true
                                        Acceso = documentSnapshot.getBoolean("Acceso: ");
                                    }
                                }
                            });
                            //Usamos el thread .sleep para que el codigo de verifcacion se ejecute cada segundo
                            Thread.sleep(1000);
                            Log.wtf("a",Acceso.toString());
                            // si el Acceso es True entonces mandamos un mensaje al Handler
                            if(Acceso == true){
                                Message msg = handlerimg.obtainMessage();
                                handlerimg.sendMessage(msg);

                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            //Inicializamos el hilo
            estado.start();



    }





    public void Continuar(View view) {

    }
}