package com.example.proyectofinal.data



data class Category(
        val id:Int,
        val name: String,
        val url: String
        ){

    override fun toString(): String {
        return "Category(id=$id, name='$name', url='$url')"
    }
}