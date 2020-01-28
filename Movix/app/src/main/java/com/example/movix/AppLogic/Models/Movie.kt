package com.example.movix.AppLogic.Models

import android.content.Context
import android.media.Image
import android.media.Rating
import com.example.movix.AppLogic.Enums.MovieGenre
import com.example.movix.AppLogic.Enums.MovieStatus
import com.example.movix.AppLogic.Enums.MovieType
import com.example.movix.Database.MyDBHandler
import com.example.movix.Database.UsersTable
import java.io.Serializable

class Movie: Model, Serializable {
    var id: Int? = null
    var name: String? = null
    var rating: Double?  = 0.0
    var type:MovieType?  = null
    var genre: MovieGenre? = null
    var status: MovieStatus?  = null
    var movieImage: String? = ""

    constructor()
    constructor(
        name: String?,
        rating: Double?,
        type: MovieType?,
        genre: MovieGenre?,
        status: MovieStatus?,
        movieImage: String?
    ) {
        this.name = name
        this.rating = rating
        this.type = type
        this.genre = genre
        this.status = status
        this.movieImage = movieImage
    }

    constructor(
        id: Int?,
        name: String?,
        rating: Double?,
        type: MovieType?,
        genre: MovieGenre?,
        status: MovieStatus?,
        movieImage: String?
    ) {
        this.id = id
        this.name = name
        this.rating = rating
        this.type = type
        this.genre = genre
        this.status = status
        this.movieImage = movieImage
    }
    fun isFavoriteToUser(context: Context): Boolean{
        val movId = MyDBHandler.FAVORITE_MOVIE
        val userIdFromFav = MyDBHandler.FAVORITE_USER
        val tableName = MyDBHandler.TABLE_FAVORITES
        val db = MyDBHandler(context,MyDBHandler.DATABASE_NAME,MyDBHandler.DATABASE_VERSION).readableDatabase
        val sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username",null)
        val userTable = UsersTable()
        val user = userTable.findModel(context,username) as? User
        val query = "" +
                "SELECT * " +
                "FROM $tableName " +
                "WHERE $userIdFromFav = ${user!!.userId} AND $movId = ${this.id}"
        val cursor = db.rawQuery(query,null)
        val retNumber = cursor.count
        cursor.close()
        if (retNumber>0){
            return true
        }else return false


    }

}