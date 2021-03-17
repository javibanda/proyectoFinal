package com.example.proyectofinal.data

class City {

    private val id: Int
    private val name: String


    constructor(id:Int, name:String){
        this.id = id
        this.name = name

    }

    fun getId(): Int{
        return this.id
    }

    fun getName(): String{
        return this.name
    }



    override fun toString(): String {
        return name
    }


}