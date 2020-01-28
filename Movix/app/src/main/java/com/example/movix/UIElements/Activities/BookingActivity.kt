package com.example.movix.UIElements.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movix.AppLogic.Adapters.ShowsRecAdapter
import com.example.movix.AppLogic.Models.Movie
import com.example.movix.AppLogic.Models.Show
import com.example.movix.R
import kotlinx.android.synthetic.main.activity_booking.*

class BookingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        val movie = intent.getSerializableExtra("movie") as Movie
        val shows = Show.getAllShowsRelatedToMovie(this,movie).asReversed()

        recShows.layoutManager = LinearLayoutManager(this)
        recShows.adapter = ShowsRecAdapter(this,shows)
    }
}
