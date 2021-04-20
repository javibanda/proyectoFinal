package com.example.proyectofinal.data

data class Person (val id:Int,
            val dni:String,
            val name:String,
            val lastName:String,
            val secondLastName:String?,
            val email:String,
            val pass:String,
            val city: City){
}