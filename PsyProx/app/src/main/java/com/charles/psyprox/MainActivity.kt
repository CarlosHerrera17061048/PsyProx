package com.charles.psyprox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.charles.psyprox.R
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
      

    }
}