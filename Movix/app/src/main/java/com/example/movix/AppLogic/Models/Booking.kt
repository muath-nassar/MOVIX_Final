package com.example.movix.AppLogic.Models

import com.example.movix.AppLogic.Enums.PaymentStatus
import java.io.Serializable

class Booking: Model,Serializable {
    var id: Int? = null
    var user: User = User()
    var cost: Double = 0.0
    var payStatus: PaymentStatus = PaymentStatus.unpaid
    var show: Show = Show()

    constructor(id: Int, user: User, cost: Double, payStatus: PaymentStatus, show: Show) {
        this.id = id
        this.user = user
        this.cost = cost
        this.payStatus = payStatus
        this.show = show
    }

    constructor(user: User, cost: Double, payStatus: PaymentStatus, show: Show) {
        this.user = user
        this.cost = cost
        this.payStatus = payStatus
        this.show = show
    }

    constructor()


}