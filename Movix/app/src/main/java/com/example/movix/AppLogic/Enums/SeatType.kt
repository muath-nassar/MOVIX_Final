package com.example.movix.AppLogic.Enums

import java.io.Serializable

enum class SeatType( var type: String) : Serializable{
    noraml("Normal"),
    premium("Premium"),
    vip("VIP")
}