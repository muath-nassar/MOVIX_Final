package com.example.movix.AppLogic.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movix.AppLogic.Models.Booking
import com.example.movix.R
import com.example.movix.UIElements.Activities.BookingSummaryActivity
import kotlinx.android.synthetic.main.booking_item.view.*

class BookingsAdapter(var context: Context,var data: MutableList<Booking>):
RecyclerView.Adapter<BookingsAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
val tvMovieName = itemView.tvMovieName
        val tvPaimmentStatus = itemView.tvPaymentStatus
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
val item = LayoutInflater.from(context).inflate(R.layout.booking_item,parent,false)
    return MyViewHolder(item)}

    override fun getItemCount(): Int {
return data.size   }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val booking = data[position]
        holder.tvMovieName.text = booking.show.movie.name
        holder.tvPaimmentStatus.text = booking.payStatus.type
        holder.itemView.setOnClickListener {
            val i = Intent(context,BookingSummaryActivity::class.java)
            val show = data[position].show
            i.putExtra("show",show)
            context.startActivity(i)
        }
    }
}