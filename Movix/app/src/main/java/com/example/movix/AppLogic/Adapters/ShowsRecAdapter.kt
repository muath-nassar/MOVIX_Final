package com.example.movix.AppLogic.Adapters


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movix.AppLogic.Models.Show
import com.example.movix.R
import com.example.movix.UIElements.Activities.BookingSummaryActivity
import kotlinx.android.synthetic.main.show_item.view.*
import java.sql.Timestamp
import java.util.*

class ShowsRecAdapter(var context: Context,var data: MutableList<Show>):
RecyclerView.Adapter<ShowsRecAdapter.MyViewHolder>(){

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvShowDate = itemView.tvShowDate
        val tvTheater = itemView.tvTheater
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowsRecAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.show_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ShowsRecAdapter.MyViewHolder, position: Int) {
        val show = data[position]
        val date = Timestamp(show.date)
        val txt = date.toGMTString()
        val c = Calendar.getInstance()
        holder.tvShowDate.setText(txt)
        holder.tvTheater.setText(show.theater.name)
       holder.itemView.setOnClickListener {
           //need some rechecks
           val show = data[position]
           val i = Intent(context,BookingSummaryActivity::class.java)
           i.putExtra("show",show)
           context.startActivity(i)

       }
    }
}