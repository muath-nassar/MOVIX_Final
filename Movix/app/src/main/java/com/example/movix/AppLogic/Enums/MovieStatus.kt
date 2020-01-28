package com.example.movix.AppLogic.Enums

import java.io.Serializable

enum class MovieStatus(var status: String): Serializable {
    available("Available"),
    unavailable("Unavailable");
    companion object{
        fun getStatus(type: String): MovieStatus {
            when (type) {
                "Available" -> return available
                "Unavailable" -> return unavailable
                else -> return unavailable
            }
        }
    }
}