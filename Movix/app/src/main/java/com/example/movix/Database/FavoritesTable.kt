package com.example.movix.Database

import android.content.ContentValues
import android.content.Context

import com.example.movix.AppLogic.Models.Movie
import com.example.movix.AppLogic.Models.User

class FavoritesTable{
    companion object{
        private val tableName = MyDBHandler.TABLE_FAVORITES
        private  val favId = MyDBHandler.FAVORITE_ID
        private val favUser = MyDBHandler.FAVORITE_USER
        private val favMovie = MyDBHandler.FAVORITE_MOVIE
    }
  fun addMovieToUserFavorite(context: Context,user: User,movie: Movie): Boolean{
      val db = MyDBHandler(context,MyDBHandler.DATABASE_NAME,MyDBHandler.DATABASE_VERSION).writableDatabase
      val values = ContentValues()
      val movieId = movie.id
      val userId = user.userId
      values.put(favUser,userId)
      values.put(favMovie,movieId)
      val result = db.insert(tableName,null,values)
      if (result < 0) {
          db.close()
          return false
      } else {
          db.close()
          return true
      }
  }
    fun getAllFavoriteMoviesToUser(context: Context,user: User) :MutableList<Movie>{
        val movies = mutableListOf<Movie>()
        val query = "SELECT * FROM $tableName WHERE $favUser = ${user.userId}"
        val db = MyDBHandler(context,MyDBHandler.DATABASE_NAME,MyDBHandler.DATABASE_VERSION).readableDatabase
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()
        while (cursor.isAfterLast == false){
            val movieId = cursor.getInt(cursor.getColumnIndex(favMovie))
            val movie = MoviesTable().findModelById(context,movieId) as Movie
            movies.add(movie)
            cursor.moveToNext()
        }
     cursor.close()
    return movies
    }
    fun deletFavoriteMovie(context: Context,movie: Movie): Boolean{
        val sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username",null)
        val userFromDB = DBContext(context, UsersTable(), User()).findModel(context,username) as? User
        val db = MyDBHandler(context,MyDBHandler.DATABASE_NAME,MyDBHandler.DATABASE_VERSION).writableDatabase
        val values = ContentValues()
        val movieId = movie.id
        val result = db.delete(tableName,"$favUser = ${userFromDB!!.userId} AND $favMovie = ${movie.id}", null  )

        if (result < 0) {
            db.close()
            return false
        } else {
            db.close()
            return true
        }
    }
}