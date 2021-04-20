package com.example.proyectofinal.data

object User {

    private var user: Person? = null

    fun connect(user: Person?) {
        this.user = user
    }

    fun disconnect() {
        this.user = null
    }

    fun getIdUser(): Person? {
        return this.user
    }

    fun getIsConnected(): Boolean {
        return this.user != null
    }
}