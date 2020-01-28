package com.example.movix.UIElements.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.movix.R
import java.lang.Exception

class SpalshScreen : AppCompatActivity() {

    val SPLASH_TIME_OUT =2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh_screen)

        ///// splash start code
        Handler().postDelayed(object:Runnable{
            public override fun run() {
                val home = Intent (this@SpalshScreen,Registration::class.java)
                startActivity(home)
                finish()
            }
        } , SPLASH_TIME_OUT.toLong())
        ///////// end splatch code

    }

}
