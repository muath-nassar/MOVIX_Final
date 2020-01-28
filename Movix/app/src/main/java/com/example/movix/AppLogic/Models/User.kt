package com.example.movix.AppLogic.Models

import android.content.ContentValues
import android.content.Context
import com.example.movix.AppLogic.Enums.Sex
import com.example.movix.Database.DBContext
import com.example.movix.Database.UsersTable
import java.io.Serializable
import java.util.*

class User : Model, Serializable {

    var userId: Int? = -1
    var name: String? = null
    var password: String? = null
    var gender: Sex? = null
    var dateOfBirth: Long? = null
    var mobile: String? = null
    var email: String? = null

    companion object{
        fun signIn(context: Context,username: String , password: String) : Boolean {
            val dpContext = DBContext(context,UsersTable(),User())
            val user = dpContext.findModel(context,username)
            if (user == null){
                return false
            }else{
                val passFromDb = (user as User).password
                if (password == passFromDb){

                    return true
                }
            }
            return false
        }
    }
    constructor()

    constructor(

        name: String?,
        gender: Sex?,
        dateOfBirth: Long?,
        mobile: String?,
        email: String?,
        password: String?
    ) {
        this.userId = userId
        this.name = name
        this.gender = gender
        this.dateOfBirth = dateOfBirth
        this.mobile = mobile
        this.email = email
        this.password = password
    }

    constructor(userId: Int?, name: String?, mobile: String?, email: String?) {
        this.userId = userId
        this.name = name
        this.mobile = mobile
        this.email = email
    }

    constructor(
        userId: Int?,
        name: String?,
        password: String?,
        gender: Sex?,
        dateOfBirth: Long?,
        mobile: String?,
        email: String?
    ) {
        this.userId = userId
        this.name = name
        this.password = password
        this.gender = gender
        this.dateOfBirth = dateOfBirth
        this.mobile = mobile
        this.email = email
    }




}









