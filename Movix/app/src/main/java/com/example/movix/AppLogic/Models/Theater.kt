package com.example.movix.AppLogic.Models

import java.io.Serializable

 class Theater: Model,Serializable{

     var id: Int? = null
     var name: String? = null
     var city: String? = null
     var state: String? = null
     var str: String? = null

     constructor()
     constructor(id: Int, name: String?, city: String?, state: String?, str: String?) {
         this.id = id
         this.name = name
         this.city = city
         this.state = state
         this.str = str
     }

     constructor(name: String?, city: String?, state: String?, str: String?) {
         this.name = name
         this.city = city
         this.state = state
         this.str = str
     }


 }