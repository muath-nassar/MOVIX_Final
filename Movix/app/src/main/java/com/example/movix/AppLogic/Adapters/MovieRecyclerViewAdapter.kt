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
import com.example.movix.Database.MoviesTable
import com.example.movix.Database.UsersTable
import com.example.movix.R
import com.example.movix.UIElements.Activities.BookingActivity
import com.example.movix.UIElements.Activities.MovieDetails
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieRecyclerViewAdapter(var context: Context, var data: MutableList<Movie>) :
    RecyclerView.Adapter<MovieRecyclerViewAdapter.MyViewHolder>(),Filterable {
    var fullData= mutableListOf<Movie>()
init {
     fullData = this.data
}


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
        return this.data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (this.data[position].isFavoriteToUser(context)){
            holder.btnFavorite.setImageResource(R.drawable.ic_favorite_red_24dp)
        }
        //

        holder.tvName.text = this.data[position].name
        holder.movieImage.setImageURI(Uri.parse(this.data[position].movieImage!!))
        holder.tvRating.text = "${this.data[position].rating}/10"
        holder.movieImage.setOnClickListener {
            val i = Intent(context, MovieDetails::class.java)
            i.putExtra("movie", this.data[position])
            context.startActivity(i)
        }
        holder.btnBook.setOnClickListener {
            val movie = this.data[position]
            val i = Intent(context,BookingActivity::class.java)
            i.putExtra("movie",movie)
            context.startActivity(i)
        }
        holder.btnFavorite.setOnClickListener {
            val sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            val username = sharedPreferences.getString("username",null)
            val userFromDB = DBContext(context, UsersTable(), User()).findModel(context,username) as? User
            val favTab = FavoritesTable()
            if (!this.data[position].isFavoriteToUser(context)){
                val result = favTab.addMovieToUserFavorite(context,userFromDB!!,
                    this.data[position])
                if (result){
                    holder.btnFavorite.setImageResource(R.drawable.ic_favorite_red_24dp)
                    Toast.makeText(context,"${this.data[position].name} is added to favorite",Toast.LENGTH_SHORT).show()
                }
            }else{
               val result = favTab.deletFavoriteMovie(context, this.data[position])
                if (result){
                    holder.btnFavorite.setImageResource(R.drawable.ic_favorite_black_24dp)
                    Toast.makeText(context,"${this.data[position].name} deleted from favorite",Toast.LENGTH_SHORT).show()

            }


        }
    }
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filteredList = mutableListOf<Movie>()
                if (constraint == null || constraint.length ==0){
                    filteredList.addAll(MoviesTable().getAll(context))


                }else{
                    var filterPattern = constraint.toString().toLowerCase().trim()
                    for (item in fullData){
                        if (item.name!!.toLowerCase().contains(filterPattern)){
                            filteredList.add(item)
                        }
                    }
                }
                val result = FilterResults()
                result.values = filteredList
                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                this@MovieRecyclerViewAdapter.data.clear()
                this@MovieRecyclerViewAdapter.data.addAll(results!!.values as MutableList<Movie>)
                notifyDataSetChanged()
            }

        }
    }



}