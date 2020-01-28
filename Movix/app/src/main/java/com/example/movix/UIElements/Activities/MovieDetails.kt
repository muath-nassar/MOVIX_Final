package com.example.movix.UIElements.Activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movix.AppLogic.Models.Movie
import com.example.movix.R
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.movie_item.*
import kotlinx.android.synthetic.main.movie_item.tvMovieName

class MovieDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val movie = intent.getSerializableExtra("movie") as? Movie
        val name = movie!!.name
        val type = movie.type!!.type
        val genre = movie.genre
        val status = movie.status
        val rating = movie.rating

        //set Movie Image
        imgMovie.setImageURI(Uri.parse(movie.movieImage!!))
        //setName
        tvMovieName.text = name
        tvType.text = type
        tvStatus.text = status.toString()
        tvRatingMovie.text = rating.toString()

        // Genre Collection
        var text = genre.toString()

        tvGenre.text = text
        btnBookThis.setOnClickListener {
            val i = Intent(this,BookingActivity::class.java)
            i.putExtra("movie",movie)
            startActivity(i)
        }

/*

            val movie = data[position]
            val i = Intent(context,BookingActivity::class.java)
            i.putExtra("movie",movie)
            context.startActivity(i)
 */


    }

}
