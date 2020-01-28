package com.example.movix.UIElements.Fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.movix.AppLogic.Models.User
import com.example.movix.Database.DBContext
import com.example.movix.Database.UsersTable

import com.example.movix.R
import com.example.movix.UIElements.Activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.view.*

/**
 * A simple [Fragment] subclass.
 */
class SignIn : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_sign_in, container, false)

        root.btnSignUp.setOnClickListener {
            Toast.makeText(activity,"All data are required",Toast.LENGTH_SHORT).show()

                activity!!.supportFragmentManager.beginTransaction().replace(R.id.mainContainer,SignUp()).setCustomAnimations(R.anim.enter_from_right,R.anim.exit_from_right).commit()


        }

       root.btnSignIn.setOnClickListener {

           val sharedPref = context!!.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
           val editor = sharedPref.edit()
           val checkLoginFlag = sharedPref.getBoolean("isLogged",false)
           val userNameIfLogged = sharedPref.getString("username",null)


           if (checkLoginFlag){
               val userIfLogged =  DBContext(context!!,UsersTable(),User()).findModel(context!!,userNameIfLogged) as? User
               val intent = Intent(context,HomeActivity()::class.java)
               intent.putExtra("currentUser",userIfLogged)
               activity!!.startActivity(intent)
               activity!!.finish()
           }

            val username = root.etUsername.text.toString()
            val password = root.etPassword.text.toString()
            val validate = User.signIn(context!!,username,password)
            if (validate){
                val user = DBContext(context!!,UsersTable(),User()).findModel(context!!,username) as? User
                val i = Intent(context,HomeActivity::class.java)
                i.putExtra("currentUser",user)
                editor.clear()
                // firstly save the name
                editor.putString("username", username)
                editor.putBoolean("isLogged", true)
                editor.apply()

                activity!!.startActivity(i)
                activity!!.finish()

            }else{
                Toast.makeText(context,"Wrong name or password" , Toast.LENGTH_LONG).show()
            }
        }
        return root
    }


}
