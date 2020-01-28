package com.example.movix.Database
import android.content.Context
import com.example.movix.AppLogic.Models.Model

class DBContext(val context: Context, var strategy : Database,val model: Model) {
    fun add(): Boolean{
        return strategy.add(context,model)
    }

    fun findModel(context: Context,name : String? ): Model?{

        return strategy.findModel(context,name)
    }
    fun findModelById(context: Context,id:Int): Model?{
        return strategy.findModelById(context,id)
    }
}