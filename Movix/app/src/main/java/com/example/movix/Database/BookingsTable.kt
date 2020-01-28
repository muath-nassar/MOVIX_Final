package com.example.movix.Database

import android.content.ContentValues
import android.content.Context
import com.example.movix.AppLogic.Enums.PaymentStatus
import com.example.movix.AppLogic.Models.Booking
import com.example.movix.AppLogic.Models.Model
import com.example.movix.AppLogic.Models.Show
import com.example.movix.AppLogic.Models.User

class BookingsTable : Database {
    companion object {
        private val tableName = MyDBHandler.TABLE_BOOKINGS

        private val bookId = MyDBHandler.BOOKING_ID
        private val bookCost = MyDBHandler.BOOKING_COST
        private val bookPayStat = MyDBHandler.BOOKING_PAYMENT_STATUS
        private val bookShow = MyDBHandler.BOOKING_SHOW
        private val bookUser = MyDBHandler.BOOKING_USER}

    override fun add(context: Context, model: Model): Boolean {
        val booking = model as Booking
        val db = MyDBHandler(
            context,
            MyDBHandler.DATABASE_NAME,
            MyDBHandler.DATABASE_VERSION
        ).writableDatabase
        val values = ContentValues()
        values.put(bookId, booking.id)
        values.put(bookCost, booking.cost)
        values.put(bookPayStat, booking.payStatus.type)
        values.put(bookShow, booking.show.id)
        values.put(bookUser,booking.user.userId)


        val result = db.insert(tableName, null, values)
        if (result < 0) {
            db.close()
            return false
        } else {
            db.close()
            return true
        }
    }

    override fun findModel(context: Context, name: String?): Model? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun findModelById(context: Context, id: Int): Model? {
        val query = "SELECT * FROM $tableName WHERE $bookId = '$id'"

        val db = MyDBHandler(context,MyDBHandler.DATABASE_NAME,MyDBHandler.DATABASE_VERSION).writableDatabase
        val cursor = db.rawQuery(query,null)
        var booking: Booking? = null
        if (cursor.moveToFirst()){
            cursor.moveToFirst()
            val bookingId = cursor.getInt(cursor.getColumnIndex(bookId))
            val bookingUser = cursor.getInt(cursor.getColumnIndex(bookUser))
            val bookingCost = cursor.getDouble(cursor.getColumnIndex(bookCost))
            val bookingPS = cursor.getString(cursor.getColumnIndex(bookPayStat))
            val bookingShow = cursor.getInt(cursor.getColumnIndex(bookShow))
            //model
            val show = ShowsTable().findModelById(context,bookingShow)as Show
            val user = UsersTable().findModelById(context,bookingUser) as User
            booking = Booking(bookingId,user,bookingCost, PaymentStatus.getPayStat(bookingPS),show)
            cursor.close()
        }
        db.close()
        return booking
    }
fun getAllBookings(context: Context): MutableList<Booking>{
    val sharedPreferences = context!!.getSharedPreferences("MyPref",Context.MODE_PRIVATE)
    val username = sharedPreferences.getString("username",null)

    //user from db

    val userFromDB = DBContext(context!!,UsersTable(),User()).findModel(context!!,username) as? User
    val bookings = mutableListOf<Booking>()
val query = "SELECT * FROM $tableName WHERE $bookUser = ${userFromDB!!.userId}"
    val db = MyDBHandler(context,MyDBHandler.DATABASE_NAME,MyDBHandler.DATABASE_VERSION).readableDatabase
    val cursor = db.rawQuery(query,null)
    cursor.moveToFirst()
    while (cursor.isAfterLast == false){
        var id = cursor.getInt(cursor.getColumnIndex(bookId))
        bookings.add(findModelById(context,id) as Booking)
        cursor.moveToNext()
    }
    cursor.close()
    return bookings
}


}