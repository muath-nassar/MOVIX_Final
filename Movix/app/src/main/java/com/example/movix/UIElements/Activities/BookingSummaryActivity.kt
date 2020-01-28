package com.example.movix.UIElements.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.movix.AppLogic.Enums.PaymentStatus
import com.example.movix.AppLogic.Models.Booking
import com.example.movix.AppLogic.Models.Show
import com.example.movix.AppLogic.Models.User
import com.example.movix.Database.BookingsTable
import com.example.movix.Database.DBContext
import com.example.movix.Database.UsersTable
import com.example.movix.R
import kotlinx.android.synthetic.main.activity_booking_summary.*
import java.sql.Timestamp

class BookingSummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_summary)
        val show = intent.getSerializableExtra("show") as Show
        tvMovieName.setText(show.movie.name)
        tvDate.setText(Timestamp(show.date).toGMTString())
        tvCost.setText("50.0")
        tvTheater.setText(show.theater.name)
        tvAddress.setText("${show.theater.city}  ${show.theater.state} \n" +
                "${show.theater.str}")

        btnBuy.setOnClickListener {
            //we need to add one booking to database
            val sharedPreferences = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            val username = sharedPreferences.getString("username",null)

            //user from db

            val userFromDB = DBContext(this, UsersTable(), User()).findModel(this,username) as? User
            val bookingUser = userFromDB!!.userId
            val showId = show.id
            val payStat = PaymentStatus.paid
            val cost = tvCost.text.toString().toDouble()
            val book = Booking(userFromDB,cost,payStat,show)
            val add = DBContext(this,BookingsTable(),book).add()
            if (add){
                Toast.makeText(this,"purchase is complete. Have a nice day",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"failed to purchase, sorry!!",Toast.LENGTH_SHORT).show()
            }
            val i = Intent(this,HomeActivity::class.java)
            startActivity(i)
        }

        btnLater.setOnClickListener {
            //we need to add one booking to database
            val sharedPreferences = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            val username = sharedPreferences.getString("username",null)

            //user from db

            val userFromDB = DBContext(this, UsersTable(), User()).findModel(this,username) as? User
            val bookingUser = userFromDB!!.userId
            val showId = show.id
            val payStat = PaymentStatus.unpaid
            val cost = tvCost.text.toString().toDouble()
            val book = Booking(userFromDB,cost,payStat,show)
            val add = DBContext(this,BookingsTable(),book).add()
            if (add){
                Toast.makeText(this,"You can buy this ticket later from History",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"failed to add, sorry!!",Toast.LENGTH_SHORT).show()
            }
            val i = Intent(this,HomeActivity::class.java)
            startActivity(i)

        }

    }
}
