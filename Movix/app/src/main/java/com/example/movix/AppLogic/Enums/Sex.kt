package com.example.movix.AppLogic.Enums

import java.io.Serializable

enum class Sex(var sex: String): Serializable {
    male("Male"),
    female("Female");
    companion object{
        fun getSex(type: String): Sex{
            when(type){
                "Male" -> return male
                "Female" -> return female
                else -> return male
            }
    }


    }
}