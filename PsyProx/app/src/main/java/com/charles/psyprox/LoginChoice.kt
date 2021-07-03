package com.charles.psyprox

import android.content.Context
import android.content.Intent
import android.content.QuickViewConstants
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Switch
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_choice.*
import kotlinx.android.synthetic.main.activity_main.*

enum class  ProviderType{
    BASIC,
    GOOGLE,
    FACEBOOK
}

class LoginChoice : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_choice)

        session()
        //Setup
        val bundle:Bundle? = intent.extras
        val email:String?=bundle?.getString("email")
        val provider:String?=bundle?.getString("provider")

        setup(email ?:"",provider ?:"");

        //Guardar Datos
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email",email)
        prefs.putString("provider",provider)
        prefs.apply()

        val p =  getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
       val TipoUsuario2 = p.getString("tipoUsuario",null)



        val LoginChoiceIntent = Intent(this , Profile_Creation::class.java).apply {
            putExtra("email",email)
            putExtra("tipoUsuario",TipoUsuario2)
        }

        when(TipoUsuario2){
            "Especialista"-> {startActivity(LoginChoiceIntent)
            }
            "Paciente" -> {startActivity(LoginChoiceIntent)}
        }
        /* if(TipoUsuario2.equals("Especialista")){
            val LoginChoiceIntent = Intent(this , Profile_Creation::class.java).apply {
                putExtra("email",email)
                putExtra("tipoUsuario",TipoUsuario2)
            }
            startActivity(LoginChoiceIntent)
        }

        else(TipoUsuario3.equals("Paciente")) {

        } */




        btnPac.setOnClickListener{
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.putString("tipoUsuario","Paciente")
            prefs.apply()
                val intent = Intent(this, Profile_Creation::class.java).apply {
                    putExtra("tipoUsuario","UsuarioNormal")
                    putExtra("email",email)
                }
                startActivity(intent)

        }

        btnEsp.setOnClickListener{

            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
                prefs.putString("tipoUsuario","Especialista")
                 prefs.apply()
            val intent = Intent(this, Profile_Creation::class.java).apply {
                putExtra("tipoUsuario","Especialista")
                putExtra("email",email)
            }
            startActivity(intent)

        }

    }


    private fun session() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email",null)
        val  provider= prefs.getString("provider",null)
        val  TipoUsuario = prefs.getString("tipoUsuario",null)


    }







    private fun setup(email: String, provider: String){
       // txtEmail.text= email
       // txtContra.text= provider

        btnCerrarSesion.setOnClickListener {

                //Borrar Datos
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            if(provider == ProviderType.FACEBOOK.name){
                LoginManager.getInstance().logOut()
            }
            FirebaseAuth.getInstance().signOut()
            onBackPressed()

        }

    }

  }