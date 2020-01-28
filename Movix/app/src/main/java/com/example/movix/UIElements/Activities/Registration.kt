package com.example.movix.UIElements.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movix.R
import com.example.movix.UIElements.Fragments.MovieFragment
import com.example.movix.UIElements.Fragments.SignIn
import com.example.movix.UIElements.Fragments.SignUp

class Registration : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val sharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE)

        val isLogged = sharedPreferences.getBoolean("isLogged",false)
        if(isLogged){
            val i = Intent(this,HomeActivity::class.java)
            startActivity(i)
            finish()

        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, SignIn()).commit()
    }

    override fun onResume() {
        super.onResume()

        supportFragmentManager.beginTransaction().replace(R.id.mainContainer, SignIn()).commit()
    }

    fun goSignUp() {
        supportFragmentManager.beginTransaction().replace(R.id.mainContainer, SignUp()).commit()
    }
}
