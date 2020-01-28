package com.example.movix.AppLogic.Adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.movix.AppLogic.Models.Movie
import com.example.movix.AppLogic.Models.User
import com.example.movix.Database.DBContext
import com.example.movix.Database.FavoritesTable
import com.example.movix.Database.UsersTable
import com.example.movix.R
import com.example.movix.UIElements.Activities.BookingActivity
import com.example.movix.UIElements.Activities.MovieDetails
import kotlinx.android.synthetic.main.movie_item.view.*

class FavoriteAdapter(var context: Context, var data: MutableList<Movie>) :
    RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {

    private var copyData = data

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieImage = itemView.movieImage
        val tvName = itemView.tvMovieName
        val tvRating = itemView.tvRating
        val btnFavorite = itemView.btnFavorite
        val btnBook = itemView.btnBook



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return copyData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (copyData[position].isFavoriteToUser(context)){
            holder.btnFavorite.setImageResource(R.drawable.ic_favorite_red_24dp)
        }
        //

        holder.tvName.text = copyData[position].name
        holder.movieImage.setImageURI(Uri.parse(copyData[position].movieImage!!))
        holder.tvRating.text = "${copyData[position].rating}/10"
        holder.movieImage.setOnClickListener {
            val i = Intent(context, MovieDetails::class.java)
            i.putExtra("movie", copyData[position])
            context.startActivity(i)
        }
        holder.btnBook.setOnClickListener {
            val movie = copyData[position]
            val i = Intent(context,BookingActivity::class.java)
            i.putExtra("movie",movie)
            context.startActivity(i)
        }
        holder.btnFavorite.setOnClickListener {
            val sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            val username = sharedPreferences.getString("username",null)
            val userFromDB = DBContext(context, UsersTable(), User()).findModel(context,username) as? User
            val favTab = FavoritesTable()
            if (!copyData[position].isFavoriteToUser(context)){
                val result = favTab.addMovieToUserFavorite(context,userFromDB!!,copyData[position])
                if (result){
                    holder.btnFavorite.setImageResource(R.drawable.ic_favorite_red_24dp)
                    Toast.makeText(context,"${copyData[position].name} is added to favorite",Toast.LENGTH_SHORT).show()
                }
            }else{
                val result = favTab.deletFavoriteMovie(context,copyData[position])
                if (result){
                    holder.btnFavorite.setImageResource(R.drawable.ic_favorite_black_24dp)
                    Toast.makeText(context,"${copyData[position].name} deleted from favorite",Toast.LENGTH_SHORT).show()
                    data.removeAt(position)
                    notifyDataSetChanged()

                }


            }
        }
    }





}