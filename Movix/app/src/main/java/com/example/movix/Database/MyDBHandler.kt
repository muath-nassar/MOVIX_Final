package com.example.movix.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHandler(context: Context, val name: String?,val version: Int?) :
    SQLiteOpenHelper(context, this.DATABASE_NAME, null, this.DATABASE_VERSION) {
    companion object {
        val DATABASE_NAME = "MOVIX_DB"
        val DATABASE_VERSION = 6

        //Tables
        val TABLE_USERS = "users"
        val TABLE_MOVIES = "movies"
        val TABLE_THEATERS = "theater"
        val TABLE_SHOWS = "shows"
        val TABLE_BOOKINGS = "bookings"
        val TABLE_FAVORITES = "fav"

        //Columns of Users
        val USER_ID = "userid"
        val USER_NAME = "username"
        val USER_PASSWORD = "password"
        val USER_EMAIL = "email"
        val USER_DOB = "dob"
        val USER_SEX = "sex"
        val USER_MOBILE = "mobile"
        //Columns for Movies
        val MOVIE_ID = "movieid"
        val MOVIE_NAME = "name"
        val MOVIE_RATING = "rating"
        val MOVIE_TYPE = "type"
        val MOVIE_GENRE = "genre"
        val MOVIE_STATUS = "status"
        val MOVIE_IMAGE = "movie_img"
        //Columns for theater
        val THEATER_ID = "theaterid"
        val THEATER_NAME = "name"
        val THEATER_CITY = "city"
        val THEATER_STATE = "state"
        val THEATER_STR = "str"
        // columns for shows
        val SHOW_ID = "showid"
        val SHOW_THEATER = "theater_id"
        val SHOW_MOVIE = "movie_id"
        val SHOW_DATE = "show_date"
        //columns for table bookings
        val BOOKING_ID = "book_id"
        val BOOKING_USER = "userid"
        val BOOKING_COST = "cost"
        val BOOKING_PAYMENT_STATUS = "payment_status"
        val BOOKING_SHOW = "show_id"
        //columns for table favorites
        val FAVORITE_ID = "favid"
        val FAVORITE_USER = "fav_user"
        val FAVORITE_MOVIE = "fav_movie"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(
            "CREATE TABLE $TABLE_USERS (" +
                    "$USER_ID INTEGER  PRIMARY KEY AUTOINCREMENT ," +
                    "$USER_NAME TEXT NOT NULL UNIQUE," +
                    "$USER_EMAIL TEXT UNIQUE ," +
                    "$USER_PASSWORD TEXT NOT NULL," +
                    "$USER_SEX TEXT ," +
                    "$USER_MOBILE CHAR(10) ," +
                    "$USER_DOB REAL )"
        )

        db.execSQL(
            "CREATE TABLE $TABLE_MOVIES (" +
                    "$MOVIE_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$MOVIE_NAME TEXT UNIQUE NOT NULL," +
                    "$MOVIE_RATING REAL NOT NULL," +
                    "$MOVIE_TYPE TEXT NOT NULL," +
                    "$MOVIE_GENRE TEXT NOT NULL," +
                    "$MOVIE_STATUS TEXT NOT NULL," +
                    "$MOVIE_IMAGE TEXT NOT NULL)"
        )
        db.execSQL(
            "CREATE TABLE $TABLE_THEATERS (" +
                    " $THEATER_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " $THEATER_NAME TEXT NOT NULL ," +
                    "$THEATER_CITY TEXT NOT NULL," +
                    "$THEATER_STATE TEXT NOT NULL," +
                    "$THEATER_STR TEXT NOT NULL)"
        )
        db.execSQL("CREATE TABLE $TABLE_SHOWS (" +
                "$SHOW_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$SHOW_DATE REAL," +
                "$SHOW_MOVIE INTEGER NOT NULL," +
                "$SHOW_THEATER INTEGER NOT NULL," +
                "FOREIGN KEY ($SHOW_MOVIE) REFERENCES $TABLE_MOVIES($MOVIE_ID)," +
                "FOREIGN KEY ($SHOW_THEATER) REFERENCES $TABLE_THEATERS($THEATER_ID) )")
        db.execSQL("CREATE TABLE $TABLE_BOOKINGS (" +
                "$BOOKING_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$BOOKING_USER INTEGER," +
                "$BOOKING_COST REAL," +
                "$BOOKING_PAYMENT_STATUS TEXT ," +
                "$BOOKING_SHOW INTEGER," +
                "FOREIGN KEY ($BOOKING_SHOW) REFERENCES $TABLE_SHOWS($SHOW_ID)," +
                "FOREIGN KEY ($BOOKING_USER) REFERENCES $TABLE_USERS($USER_ID))")
        db.execSQL("CREATE TABLE $TABLE_FAVORITES(" +
                "$FAVORITE_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$FAVORITE_USER INTEGER," +
                "$FAVORITE_MOVIE INTEGER," +
                "FOREIGN KEY ($FAVORITE_USER) REFERENCES $TABLE_USERS($USER_ID)," +
                "FOREIGN KEY ($FAVORITE_MOVIE) REFERENCES $TABLE_MOVIES($MOVIE_ID)," +
                "UNIQUE ($FAVORITE_USER,$FAVORITE_MOVIE))")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE  IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE  IF EXISTS $TABLE_MOVIES")
        db.execSQL("DROP TABLE  IF EXISTS $TABLE_THEATERS")
        db.execSQL("DROP TABLE  IF EXISTS $TABLE_SHOWS")
        db.execSQL("DROP TABLE  IF EXISTS $TABLE_BOOKINGS")
        db.execSQL("DROP TABLE  IF EXISTS $TABLE_FAVORITES")
        onCreate(db)

    }
}