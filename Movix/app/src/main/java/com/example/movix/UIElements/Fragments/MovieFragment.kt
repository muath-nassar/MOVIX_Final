package com.example.movix.UIElements.Fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.support.v4.app.INotificationSideChannel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movix.AppLogic.Adapters.MovieRecyclerViewAdapter

import com.example.movix.Database.*

import com.example.movix.R
import com.example.movix.UIElements.Activities.AddMovieActivity

import kotlinx.android.synthetic.main.fragment_movie.view.*


/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_movie, container, false)

        //testStart
       /* val m1 = Movie(
            "Jumanji",
            7.0,
            MovieType.english,
            MovieGenre.Comedy,
            MovieStatus.available,
            R.drawable.jumanji
        )

        val m2 = Movie(
            "The Lion King",
            7.0,
            MovieType.english,
            MovieGenre.Fantasia,
            MovieStatus.available,
            R.drawable.king
        )*/



     /* val M1 = Movie(
            1,
            "Jumanji",
            7.0,
            MovieType.english,
            MovieGenre.Comedy,
            MovieStatus.available,
            ""
        )
        val c = Calendar.getInstance()
        c.set(2020, 5, 6)
        val s1 = Show(c.timeInMillis, M1, T1)
        val dp0 = DBContext(context!!, ShowsTable(), s1)
        dp0.add()*/
        //create Movies  in DB
     /*  val dp = DBContext(context!!, MoviesTable(), m1)
        dp.add()
        val dp2 = DBContext(context!!, MoviesTable(), m2)
        dp2.add()
        val dp3 = DBContext(context!!, TheatersTable(), t1)
        dp3.add()
 val t1 = Theater("Fox Cinama ", "Vegas", "Nevada", "str15A/14p")
        val T1 = Theater(1, "Fox Cinama ", "Vegas", "Nevada", "str15A/14p")
        val dp3 = DBContext(context!!, TheatersTable(), t1)
dp3.add()





        */

        //retriev Movies
        val data = MoviesTable().getAll(context!!)



        root.recMovies.layoutManager = LinearLayoutManager(context)
        val adapter = MovieRecyclerViewAdapter(context!!, data)
        root.recMovies.adapter = adapter

        //testEnd
        root.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }

        })
        //end of search
        root.fabAddMovie.setOnClickListener {
            val i = Intent(context,AddMovieActivity::class.java)
            startActivity(i)

        }
        return root
    }


}
