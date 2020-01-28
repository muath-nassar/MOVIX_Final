package com.example.movix.Database

import android.content.Context
import android.graphics.ColorSpace
import com.example.movix.AppLogic.Models.Model

interface Database {
    fun add(context: Context, model: Model ): Boolean
    fun findModel(context: Context, name:String?): Model?
    fun findModelById(context: Context,id:Int): Model?
}