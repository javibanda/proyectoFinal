package com.example.proyectofinal.data

import com.example.proyectofinal.util.Date

class Order(val id: Int,
            val date: Date,
            val priceProducts: Float,
            val priceDelivery: Float,
            val person: Person,
            val city: City
            ) {
    override fun toString(): String {
        return "Order(id=$id, date=$date, priceProducts=$priceProducts, priceDelivery=$priceDelivery, person=$person, city=$city)"
    }
}