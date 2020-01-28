package com.example.movix.AppLogic.Enums

import java.io.Serializable

enum class MovieGenre(var genre: String): Serializable {
    Romance("Romance"),
    Action("Action"),
    Fantasia("Fantasia"),
    Historical("Historical"),
    Horror("Horror"),
    Comedy("Comedy"),
    Documentary("Documentary"),
    ScienceFiction("Science fiction");
    companion object{
        fun getGenre(type: String): MovieGenre {
            when (type) {
                "Romance" -> return Romance
                "Action" -> return Action
                "Fantasia" -> return Fantasia
                "Historical" -> return Historical
                "Horror" -> return Horror
                "Comedy" -> return Comedy
                "Documentary" -> return Documentary
                "Science fiction" -> return ScienceFiction

                else -> return Action
            }
        }
    }
}