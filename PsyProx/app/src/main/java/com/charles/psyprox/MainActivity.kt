package com.charles.psyprox

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.charles.psyprox.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private  val GOOGLE_SIGN_IN = 100;
    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(2000)
        setTheme(R.style.Theme_AppCompat_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //SETUP
        setup()
        session()
    }

    override fun onStart() {
        super.onStart()
        LoginLayout.visibility = View.VISIBLE
    }
    private fun session(){
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email",null)
        val  provider= prefs.getString("provider",null)

        if (email != null && provider != null){
            LoginLayout.visibility = View.INVISIBLE
           showLoginChoice(email,ProviderType.valueOf(provider))
        }
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
        imgBtnGoogle.setOnClickListener {
            //Configuracion
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(this,googleConf)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN )

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN){

        }
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

        try {
            val account = task.getResult(ApiException::class.java)

            if (account != null){
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                    if(it.isSuccessful){
                        showLoginChoice(account.email ?:"",ProviderType.GOOGLE)
                    }else{
                        showAlert()
                    }

                }


            }
        }catch (e: ApiException){
            showAlert()
        }

    }

}