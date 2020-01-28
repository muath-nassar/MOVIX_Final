package com.example.movix.AppLogic.Models

class ModelFactory {
    fun getModel(type: String): Model? {
        if (type.equals("movie",true)){
            return Movie()
        }else if (type.equals("theater",true)){
            return Theater()
        }
        else if (type.equals("user",true)){
            return User()
        }

        else if (type.equals("booking",true)){
            return Booking()
        }
        else if (type.equals("show",true)){
            return Show()
        }
        return null
    }
}