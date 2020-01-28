package com.example.movix.Database

import android.content.ContentValues
import android.content.Context
import com.example.movix.AppLogic.Enums.Sex
import com.example.movix.AppLogic.Models.Model
import com.example.movix.AppLogic.Models.User


class UsersTable : Database {
    companion object {
        private val tableName = MyDBHandler.TABLE_USERS
        private val userId = MyDBHandler.USER_ID
        private val username = MyDBHandler.USER_NAME
        private val email = MyDBHandler.USER_EMAIL
        private val sex = MyDBHandler.USER_SEX
        private val mobile = MyDBHandler.USER_MOBILE
        private val dob = MyDBHandler.USER_DOB
        private val password = MyDBHandler.USER_PASSWORD
    }


    override fun add(context: Context, model: Model): Boolean {

        val user = model as User
        val db = MyDBHandler(context,MyDBHandler.DATABASE_NAME,MyDBHandler.DATABASE_VERSION).writableDatabase
        val values = ContentValues()
        values.put(username, user.name)
        values.put(email, user.email)
        values.put(password, user.password)
        values.put(sex, user.gender!!.sex)
        values.put(dob, user.dateOfBirth)
        values.put(mobile, user.mobile)
        val result = db.insert(tableName, null, values)
        if (result < 0) {
            db.close()
            return false
        } else {
            db.close()
            return true
        }


    }

    override fun findModel(context: Context, name: String?): Model? {
        val query = "SELECT * FROM $tableName " +
                "WHERE $username = '$name'"
        val db = MyDBHandler(context,MyDBHandler.DATABASE_NAME,MyDBHandler.DATABASE_VERSION).writableDatabase
        val cursor = db.rawQuery(query, null)
        var user: User? = null
        if (cursor.moveToFirst()) {
            cursor.moveToFirst()
            // put constructor elements
            val userId = cursor.getInt(0)
            val userName = cursor.getString(1)
            val userEmail = cursor.getString(2)
            val userPass = cursor.getString(3)
            val userSex = cursor.getString(4)
            val userMob = cursor.getString(5)
            val userDOB = cursor.getLong(6)

            user =
                User(userId, userName, userPass, Sex.getSex(userSex), userDOB, userMob, userEmail)
            cursor.close()
        }
        db.close()
        return user
    }

    override fun findModelById(context: Context, id: Int): Model? {
        val query = "SELECT * FROM $tableName " +
                "WHERE $userId = '$id'"
        val db = MyDBHandler(context,MyDBHandler.DATABASE_NAME,MyDBHandler.DATABASE_VERSION).writableDatabase
        val cursor = db.rawQuery(query, null)
        var user: User? = null
        if (cursor.moveToFirst()) {
            cursor.moveToFirst()
            // put constructor elements
            val userId = cursor.getInt(0)
            val userName = cursor.getString(1)
            val userEmail = cursor.getString(2)
            val userPass = cursor.getString(3)
            val userSex = cursor.getString(4)
            val userMob = cursor.getString(5)
            val userDOB = cursor.getLong(6)

            user =
                User(userId, userName, userPass, Sex.getSex(userSex), userDOB, userMob, userEmail)
            cursor.close()
        }
        db.close()
        return user    }
    fun updateMobile(context: Context,user: User,new_mobile: String): Boolean{
        val db = MyDBHandler(context,MyDBHandler.DATABASE_NAME,MyDBHandler.DATABASE_VERSION).writableDatabase
       val values = ContentValues()
        values.put(mobile,new_mobile)
        val result = db.update(tableName,values,"${MyDBHandler.USER_ID} = ${user.userId}",null)
        if (result<0){
            return false
        }else{
            return true
        }
    }

    fun updateEmail(context: Context,user: User,new_email: String): Boolean{
        val db = MyDBHandler(context,MyDBHandler.DATABASE_NAME,MyDBHandler.DATABASE_VERSION).writableDatabase
        val values = ContentValues()
        values.put(email,new_email)
        val result = db.update(tableName,values,"${MyDBHandler.USER_ID} = ${user.userId}",null)
        if (result<0){
            return false
        }else{
            return true
        }
    }

    fun updateSex(context: Context,user: User,sexU: Sex): Boolean{
        val db = MyDBHandler(context,MyDBHandler.DATABASE_NAME,MyDBHandler.DATABASE_VERSION).writableDatabase
        val values = ContentValues()
        values.put(sex,sexU.sex)
        val result = db.update(tableName,values,"${MyDBHandler.USER_ID} = ${user.userId}",null)
        if (result<0){
            return false
        }else{
            return true
        }
    }
}