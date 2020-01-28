package com.example.movix.AppLogic.Models

import android.content.Context
import com.example.movix.Database.ShowsTable
import java.io.Serializable

class Show: Model,Serializable {
    var id : Int? = null;
    var date: Long = 0;
    var movie:Movie = Movie()
    var theater:Theater= Theater()

    constructor()
    constructor(id: Int, date: Long, movie: Movie, theater: Theater) {
        this.id = id
        this.date = date
        this.movie = movie
        this.theater = theater
    }

    constructor(date: Long, movie: Movie, theater: Theater) {
        this.date = date
        this.movie = movie
        this.theater = theater
    }

    companion object{
        fun getAllShowsRelatedToMovie(context: Context,movie: Movie): MutableList<Show>{
            return ShowsTable().getShowsToMovie(context,movie)
        }
    }
}