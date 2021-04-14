package com.charles.psyprox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.charles.psyprox.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(2000)
        setTheme(R.style.Theme_AppCompat_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //SETUP
        setup()
    }
    private fun setup(){
      btnRegistro.setOnClickListener {
          if (edtTextCorreo.text.isNotEmpty() && edtTextPass.text.isNotEmpty()){
              FirebaseAuth.getInstance()
                  .createUserWithEmailAndPassword(edtTextCorreo.text.toString(),
              edtTextPass.text.toString()).addOnCompleteListener {
                  if(it.isSuccessful){
                      showLoginChoice(it.result?.user?.email?:"",ProviderType.BASIC)
                  }else{
                    showAlert()
                  }
              }
          }

      }
            btnIniciarSesion.setOnClickListener  {
                if (edtTextCorreo.text.isNotEmpty() && edtTextPass.text.isNotEmpty()){

                    FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(edtTextCorreo.text.toString(),
                        edtTextPass.text.toString()).addOnCompleteListener {
                        if(it.isSuccessful){
                            showLoginChoice(it.result?.user?.email?:"",ProviderType.BASIC)
                        }else{
                            showAlert()
                        }
                    }
                }

            }
    }

    private fun showAlert(){

        val builder = AlertDialog.Builder(this )
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptrar",null )
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun showLoginChoice(email:String, provider:ProviderType){
        val LoginChoiceIntent = Intent(this , LoginChoice::class.java).apply {
        putExtra("email",email)
            putExtra("provider",provider.name  )
        }
        startActivity(LoginChoiceIntent)


    }
}