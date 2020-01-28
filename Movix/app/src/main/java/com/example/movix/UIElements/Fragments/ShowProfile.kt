package com.example.movix.UIElements.Fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.movix.AppLogic.Models.User
import com.example.movix.Database.DBContext
import com.example.movix.Database.UsersTable

import com.example.movix.R
import com.example.movix.UIElements.Activities.Registration
import kotlinx.android.synthetic.main.fragment_show_profile.view.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.Inflater

/**
 * A simple [Fragment] subclass.
 */
class ShowProfile : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment

        val root = inflater.inflate(R.layout.fragment_show_profile, container, false)
       // var user: User? = null

        //Get user from bundle
        val bundle = arguments


            //get username to request data from database by the name
           val sharedPreferences = context!!.getSharedPreferences("MyPref",Context.MODE_PRIVATE)
            val username = sharedPreferences.getString("username",null)

            //user from db

            val userFromDB = DBContext(context!!,UsersTable(),User()).findModel(context!!,username) as? User

            //set Data to fields in fragment

            root.etUsername.setText(userFromDB!!.name )
            root.etEmail.setText(userFromDB.email)
            root.etMobile.setText(userFromDB.mobile)
            root.etSex.setText(userFromDB.gender!!.sex)
            val dateAsDate = Date(userFromDB.dateOfBirth!!)
            var formatter = SimpleDateFormat("yyyy/MM/dd")
            formatter.timeZone
            val date = formatter.format(dateAsDate)
            root.etDateOfBirth.setText(date)
            //disable texts
            root.etUsername.isEnabled = false
            root.etEmail.isEnabled = false
            root.etSex.isEnabled = false
            root.etMobile.isEnabled =false
            root.etDateOfBirth.isEnabled = false




        root.btnSignout.setOnClickListener {
            //create alert
            val alert = AlertDialog.Builder(activity!!)
            alert.setTitle("Are you sure to Sign Out")
            alert.setPositiveButton("Yes") { _, _ ->

                val sharedPreferences =
                    activity!!.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                val state = editor.clear().commit()
                if (state) {
                    Toast.makeText(context, "Signed out successfully ", Toast.LENGTH_SHORT).show()

                    val i = Intent(activity, Registration::class.java)
                    startActivity(i)
                }
            }
            alert.setNegativeButton("No") { _, _ ->
            }

            alert.create().show()
        }

        root.btnEditProfile.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.homeActivityContainer, EditProfile()).commit()
        }

        return root
    }
/*
    override fun onCreate(savedInstanceState: Bundle?) {

        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.signOut) {
            val sharedPreferences = activity!!.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val state = editor.clear().commit()
            if (state) {
                Toast.makeText(context, "Signed out successfully ", Toast.LENGTH_SHORT).show()

                val i = Intent(activity, Registration::class.java)
                startActivity(i)
                activity!!.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
