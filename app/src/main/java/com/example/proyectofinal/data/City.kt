package com.example.proyectofinal.data

data class City(
        val id: Int,
        val name: String
        ){

    override fun toString(): String {
        return name
    }
}