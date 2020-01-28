package com.example.movix.UIElements.Fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movix.AppLogic.Adapters.FavoriteAdapter
import com.example.movix.AppLogic.Adapters.MovieRecyclerViewAdapter
import com.example.movix.AppLogic.Models.User
import com.example.movix.Database.DBContext
import com.example.movix.Database.FavoritesTable
import com.example.movix.Database.UsersTable

import com.example.movix.R
import kotlinx.android.synthetic.main.fragment_favorite.view.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_favorite, container, false)

        val sharedPreferences = context!!.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username",null)
        //user from db
        val userFromDB = DBContext(context!!, UsersTable(), User()).findModel(context!!,username) as? User
        val data = FavoritesTable().getAllFavoriteMoviesToUser(context!!,userFromDB!!).asReversed()
        root.recFav.layoutManager = LinearLayoutManager(context)
        root.recFav.adapter = FavoriteAdapter(context!!,data)

        return root
    }


}
