package com.example.proyectofinal.data

object User {

    private var user: Person? = null
    private var listOrder = ArrayList<Order>()

    fun connectedUser(person: Person?) {
        user = person
    }

    fun disconnectedUser() {
        user = null
    }

    fun getUser(): Person? {
        return user
    }


    fun getIsConnected(): Boolean {
        return user != null
    }
}