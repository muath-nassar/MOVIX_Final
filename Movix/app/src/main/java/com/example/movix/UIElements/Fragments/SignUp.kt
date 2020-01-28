package com.example.movix.UIElements.Fragments


import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.movix.AppLogic.Enums.Sex
import com.example.movix.AppLogic.Models.User
import com.example.movix.Database.DBContext
import com.example.movix.Database.UsersTable

import com.example.movix.R
import com.example.movix.UIElements.Activities.HomeActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class SignUp : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_sign_up, container, false)

        root.btnSignIn.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, SignIn()).addToBackStack(null)
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_right).commit()
        }
        //date picker
        root.etDateOfBirth.setOnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr.get(Calendar.DAY_OF_MONTH)
            val month = cldr.get(Calendar.MONTH)
            val year = cldr.get(Calendar.YEAR)

            val picker = DatePickerDialog(
                activity!!,
                DatePickerDialog.OnDateSetListener { _, yyear, monthOfYear, dayOfMonth ->
                    etDateOfBirth.setText(yyear.toString() + "/" + (monthOfYear + 1).toString() + "/" + dayOfMonth.toString())
                },
                year,
                month,
                day
            )

            picker.show()

            // signing up a new user
            btnSignUp.setOnClickListener {
                val name = etUsername.text.toString()
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val sex: Sex
                if (rbMale.isChecked) {
                    sex = Sex.male
                } else {
                    sex = Sex.female
                }
                val mobile = etMobile.text.toString()
                val dobAsString = (etDateOfBirth.text.toString())


                //date
                val yearr = dobAsString.substringBefore("/").toInt()
                val dayy = dobAsString.substringAfterLast("/").toInt()
                val monthh = dobAsString.substringAfter("/").substringBefore("/").toInt() -1
                val c = Calendar.getInstance()
                c.set(yearr, monthh, dayy)
                val dob = c.timeInMillis


                //ensure all fields are not empty
                if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && mobile.isNotEmpty() && etDateOfBirth.text.toString().isNotEmpty()) {

                    val sharedPref = context!!.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    val currentUser = User(name, sex, dob, mobile, email, password)
                    val DBop = DBContext(context!!, UsersTable(), currentUser)
                    val result = DBop.add()
                    if (result) {
                        // firstly save the name
                        editor.putString("username", name)
                        editor.putBoolean("isLogged", true)
                        val state = editor.commit()
                        if (state) {// Now we are sure that singing up is okay
                            Toast.makeText(context, "Hello Me/Msc $name", Toast.LENGTH_SHORT).show()
                            val i = Intent(context, HomeActivity::class.java)
                            context!!.startActivity(i)
                            activity!!.finish()
                        } else Toast.makeText(
                            activity!!,
                            "You should enter all fields",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Snackbar.make(view!!, "Something went wrong ", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show()
                    }


                }

            }


        }
        return root
    }


}
