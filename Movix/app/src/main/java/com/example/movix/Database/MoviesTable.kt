package com.example.movix.Database

import android.content.ContentValues
import android.content.Context
import com.example.movix.AppLogic.Enums.MovieGenre
import com.example.movix.AppLogic.Enums.MovieStatus
import com.example.movix.AppLogic.Enums.MovieType
import com.example.movix.AppLogic.Models.Model
import com.example.movix.AppLogic.Models.Movie


class MoviesTable : Database {
    companion object {
        private val tableName = MyDBHandler.TABLE_MOVIES
        private val movieId = MyDBHandler.MOVIE_ID
        private val movieName = MyDBHandler.MOVIE_NAME
        private val rating = MyDBHandler.MOVIE_RATING
        private val type = MyDBHandler.MOVIE_TYPE
        private val genre = MyDBHandler.MOVIE_GENRE
        private val status = MyDBHandler.MOVIE_STATUS
        private val movieImg = MyDBHandler.MOVIE_IMAGE
    }

    override fun add(context: Context, model: Model): Boolean {
        val movie = model as Movie
        val db = MyDBHandler(context,MyDBHandler.DATABASE_NAME,MyDBHandler.DATABASE_VERSION).writableDatabase
        val values = ContentValues()
        values.put(movieName, movie.name)
        values.put(rating, movie.rating)
        values.put(type, movie.type!!.type)
        values.put(genre, movie.genre!!.genre)
        values.put(status, movie.status!!.status)
        values.put(movieImg, movie.movieImage)
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
                "WHERE $movieName = '$name'"
        val db = MyDBHandler(context,MyDBHandler.DATABASE_NAME,MyDBHandler.DATABASE_VERSION).writableDatabase
        val cursor = db.rawQuery(query, null)
        var movie: Movie? = null
        if (cursor.moveToFirst()) {
            cursor.moveToFirst()
            // put constructor elements
            val movieId = cursor.getInt(0)
            val movieName = cursor.getString(1)
            val rating = cursor.getString(2)
            val type = cursor.getString(3)
            val genre = cursor.getString(4)
            val status = cursor.getString(5)
            val movieImg = cursor.getString(6)

            movie =
                Movie(
                    movieId,
                    movieName,
                    rating.toDouble(),
                    MovieType.getType(type),
                    MovieGenre.getGenre(genre),
                    MovieStatus.getStatus(status),
                    movieImg
                )
            cursor.close()
        }
        db.close()
        return movie
    }

    override fun findModelById(context: Context, id: Int): Model? {
        val query = "SELECT * FROM $tableName " +
                "WHERE $movieId = '$id'"
        val db = MyDBHandler(context,MyDBHandler.DATABASE_NAME,MyDBHandler.DATABASE_VERSION).writableDatabase
        val cursor = db.rawQuery(query, null)
        var movie: Movie? = null
        if (cursor.moveToFirst()) {
            cursor.moveToFirst()
            // put constructor elements
            val movieId = cursor.getInt(0)
            val movieName = cursor.getString(1)
            val rating = cursor.getString(2)
            val type = cursor.getString(3)
            val genre = cursor.getString(4)
            val status = cursor.getString(5)
            val movieImg = cursor.getString(6)

            movie =
                Movie(
                    movieId,
                    movieName,
                    rating.toDouble(),
                    MovieType.getType(type),
                    MovieGenre.getGenre(genre),
                    MovieStatus.getStatus(status),
                    movieImg
                )
            cursor.close()
        }
        db.close()
        return movie    }

    fun getAll(context: Context): MutableList<Movie> {
        val movies = mutableListOf<Movie>()

        val query = "SELECT * FROM $tableName"
        val db = MyDBHandler(context,MyDBHandler.DATABASE_NAME,MyDBHandler.DATABASE_VERSION).readableDatabase
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        while (cursor.isAfterLast==false){
            val id = cursor.getInt(0)
            val movieName = cursor.getString(1)
            val rating = cursor.getString(2)
            val type = cursor.getString(3)
            val genre = cursor.getString(4)
            val status = cursor.getString(5)
            val movieImg = cursor.getString(6)
            movies.add(
                Movie(
                    id,
                    movieName,
                    rating.toDouble(),
                    MovieType.getType(type),
                    MovieGenre.getGenre(genre),
                    MovieStatus.getStatus(status),
                    movieImg
                )
            )
            cursor.moveToNext()
        }
        cursor.close()
return movies.asReversed()
    }

}