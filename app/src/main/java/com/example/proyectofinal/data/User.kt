package com.example.proyectofinal.data

object User {

    private var user: Person? = null

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