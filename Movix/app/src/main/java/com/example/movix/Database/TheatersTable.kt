package com.example.movix.Database

import android.content.ContentValues
import android.content.Context
import com.example.movix.AppLogic.Models.Model
import com.example.movix.AppLogic.Models.Theater

class TheatersTable: Database {

    companion object {
        private val tableName = MyDBHandler.TABLE_THEATERS
        private val name = MyDBHandler.THEATER_NAME
        private val theaterId = MyDBHandler.THEATER_ID
        private val city = MyDBHandler.THEATER_CITY
        private val state = MyDBHandler.THEATER_STATE
        private val str = MyDBHandler.THEATER_STR

    }

    override fun add(context: Context, model: Model): Boolean {
        val theater = model as Theater
        val db = MyDBHandler(context,MyDBHandler.DATABASE_NAME,MyDBHandler.DATABASE_VERSION).writableDatabase
        val values = ContentValues()
        values.put(theaterId, theater.id)
        values.put(name, theater.name)
        values.put(city, theater.city)
        values.put(state, theater.state)
        values.put(str, theater.str)

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
        val query = "SELECT * FROM $tableName " +
                "WHERE $theaterId = '$id'"
        val db = MyDBHandler(context,MyDBHandler.DATABASE_NAME,MyDBHandler.DATABASE_VERSION).writableDatabase
        val cursor = db.rawQuery(query, null)
        var theater: Theater? = null
        if (cursor.moveToFirst()) {
            cursor.moveToFirst()
            // put constructor elements
            val thId = cursor.getInt(0)
            val thName = cursor.getString(1)
            val thCity = cursor.getString(2)
            val thState = cursor.getString(3)
            val thStr = cursor.getString(4)


            theater =
                Theater(thId,thName,thCity,thState,thStr)
            cursor.close()
        }
        db.close()
        return theater
    }
}