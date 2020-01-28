package com.example.movix.UIElements.Fragments


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
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_edit_profile.etEmail
import kotlinx.android.synthetic.main.fragment_edit_profile.etMobile
import kotlinx.android.synthetic.main.fragment_edit_profile.view.*


/**
 * A simple [Fragment] subclass.
 */
class EditProfile : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        //get username to request data from database by the name
        val sharedPreferences = context!!.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", null)

        //user from db

        val userFromDB =
            DBContext(context!!, UsersTable(), User()).findModel(context!!, username) as? User
        root.btnUpdateProfile.setOnClickListener {
            var ischanged = false
            if (etEmail.text.isNotEmpty()) {
                val result =
                    UsersTable().updateEmail(context!!, userFromDB!!, etEmail.text.toString())
                if (result) {
                    Toast.makeText(
                        context!!,
                        "updated email successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    ischanged = true
                }
            }
            if (etMobile.text.isNotEmpty()) {
                val result =
                    UsersTable().updateMobile(context!!, userFromDB!!, etMobile.text.toString())
                if (result){
                    Toast.makeText(
                        context!!,
                        "updated mobile successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    ischanged = true
                }
            }
            if (rbMaleU.isChecked || rbFemaleU.isChecked) {
                val sex: Sex
                if (rbMaleU.isChecked) {
                    sex = Sex.male
                } else {
                    sex = Sex.female
                }
                val result = UsersTable().updateSex(context!!, userFromDB!!, sex)
                if (result){
                    Toast.makeText(
                        context!!,
                        "updated sex successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    ischanged = true
                }
            }
if (ischanged){
    val transaction = activity!!.supportFragmentManager.beginTransaction()
    transaction.replace(R.id.homeActivityContainer, ShowProfile()).commit()
}
        }

        return root
    }


}
