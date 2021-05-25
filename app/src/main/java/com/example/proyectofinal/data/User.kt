package com.example.proyectofinal.data

import android.media.Rating

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

    private fun getSecondLastName(): String {
        return if (user!!.secondLastName == null){
            ""
        }else{
            user!!.secondLastName!!.toLowerCase().capitalize()
        }
    }

    fun getCity() = user!!.city.name.capitalize() + ", " + user!!.city.region.capitalize()

    fun getEmail() = user!!.email

    fun getName() = user!!.name.toLowerCase().capitalize() + " " + user!!.lastName.toLowerCase().capitalize() + " " + getSecondLastName()

    fun getDni() = user!!.dni.toUpperCase()
}