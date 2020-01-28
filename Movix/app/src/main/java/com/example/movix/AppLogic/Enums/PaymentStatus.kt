package com.example.movix.AppLogic.Enums

import java.io.Serializable

enum class PaymentStatus(var type :String): Serializable {
    paid("Paid"),
    unpaid("Unpaid");
    companion object{
        fun getPayStat(type: String): PaymentStatus{
            when(type){
                "Paid"-> return paid
                else -> return unpaid
            }
        }
    }
}