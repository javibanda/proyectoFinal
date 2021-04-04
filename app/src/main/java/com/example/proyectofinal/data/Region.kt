package com.example.proyectofinal.data


data class Region(
        val id:Int,
        val name: String,
        val city: List<City>
        ){

        override fun toString(): String {
                return name
        }
}