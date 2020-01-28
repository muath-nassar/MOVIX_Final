package com.example.movix.AppLogic.Enums

import java.io.Serializable

enum class MovieType(var type: String) : Serializable {

    hindi("Hindi"),
    english("English"),
    arabic("Arabic");

    companion object {
        fun getType(type: String): MovieType {
            when (type) {
                "Hindi" -> return hindi
                "English" -> return english
                "Arabic" -> return arabic
                else -> return english
            }
        }
    }


}