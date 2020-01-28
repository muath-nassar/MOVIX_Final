package com.example.movix.Database

import android.content.ContentValues
import android.content.Context
import com.example.movix.AppLogic.Models.Model
import com.example.movix.AppLogic.Models.Movie
import com.example.movix.AppLogic.Models.Show
import com.example.movix.AppLogic.Models.Theater
import java.util.*

class ShowsTable: Database {

    companion object {
        private val tableName = MyDBHandler.TABLE_SHOWS

        private val showId = MyDBHandler.SHOW_ID
        private val showDate = MyDBHandler.SHOW_DATE
        private val showMovie = MyDBHandler.SHOW_MOVIE
        private val showTheater = MyDBHandler.SHOW_THEATER

    }

    override fun add(context: Context, model: Model): Boolean {
        val show = model as Show
        val db = MyDBHandler(context,MyDBHandler.DATABASE_NAME,MyDBHandler.DATABASE_VERSION).writableDatabase
        val values = ContentValues()
        values.put(showId, show.id)
        values.put(showDate, show.date)
        values.put(showMovie, show.movie.id)
        values.put(showTheater, show.theater.id)


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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findModelById(context: Context, id: Int): Model? {
        val query = "SELECT * FROM $tableName " +
                "WHERE $showId = '$id'"
        val db = MyDBHandler(context,MyDBHandler.DATABASE_NAME,MyDBHandler.DATABASE_VERSION).writableDatabase
        val cursor = db.rawQuery(query, null)
        var show: Show? = null
        if (cursor.moveToFirst()) {
            cursor.moveToFirst()
            // put constructor elements
            val showId = cursor.getInt(0)
            val showDate = cursor.getLong(1)
            val showMovie = cursor.getInt(2)
            val showTheater = cursor.getInt(3)

            //models
            val movie = MoviesTable().findModelById(context,showMovie) as? Movie
            val theater = TheatersTable().findModelById(context,showTheater) as? Theater



            show =
                Show(showId,showDate,movie!!,theater!!)
            cursor.close()
        }
        db.close()
        return show
    }

    fun getShowsToMovie(context: Context,movie: Movie): MutableList<Show>{
        val shows = mutableListOf<Show>()
        val c = Calendar.getInstance()
        val current = c.timeInMillis
        val query = "SELECT * FROM $tableName WHERE $showMovie = ${movie.id} AND $showDate >= $current ;"
        val db = MyDBHandler(context,MyDBHandler.DATABASE_NAME,MyDBHandler.DATABASE_VERSION).readableDatabase
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        while (cursor.isAfterLast == false){
            val id = cursor.getInt(0)
            shows.add(findModelById(context,id) as Show)
            cursor.moveToNext()

        }
        cursor.close()
        return shows
    }
}