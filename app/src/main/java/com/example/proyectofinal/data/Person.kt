package com.example.proyectofinal.data

data class Person (val id:Int,
            val dni:String,
            val name:String,
            val lastName:String,
            val secondLastName:String?,
            val email:String,
            val pass:String,
            val city: City,
            val listFavorites: ArrayList<Int>){

    fun removeFavorite(idProduct: Int){
        listFavorites.remove(idProduct)
    }

    fun addFavorite(idProduct: Int){
        listFavorites.add(idProduct)
    }

    fun isFavorite(idProduct: Int): Boolean{
        return listFavorites.contains(idProduct)
    }

    override fun toString(): String {
        return "Person(id=$id, dni='$dni', name='$name', lastName='$lastName', secondLastName=$secondLastName, email='$email', pass='$pass', city=$city)"
    }
}