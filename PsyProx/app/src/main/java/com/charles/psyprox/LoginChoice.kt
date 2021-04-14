package com.charles.psyprox

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_choice.*

enum class  ProviderType{
    BASIC,
    GOOGLE
}
class LoginChoice : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_choice)

        //Setup
        val bundle:Bundle? = intent.extras
        val email:String?=bundle?.getString("email")
        val provider:String?=bundle?.getString("provider")
        setup(email ?:"",provider ?:"")

        //Guardar Datos
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email",email)
        prefs.putString("provider",provider)
        prefs.apply()

    }
    private fun setup(email: String, provider: String){
        txtEmail.text= email
        txtContra.text= provider

        btnCerrarSesion.setOnClickListener {

            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            FirebaseAuth.getInstance().signOut()
            onBackPressed()

        }

    }
}