package com.example.proyectofinal.data

class Region {

    private val id: Int
    private val name: String
    private val city: List<City>

    constructor(id:Int, name:String, city: List<City>){
        this.id = id
        this.name = name
        this.city = city
    }

    fun getId(): Int{
        return this.id
    }

    fun getName(): String{
        return this.name
    }

    fun getCity(): List<City>{
        return this.city
    }

    override fun toString(): String {
        return name
    }


}