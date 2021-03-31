package com.example.proyectofinal.data

class KindProduct {

    private val id: Int
    private val name: String
    private val url: String


    constructor(id:Int, name:String, url:String){
        this.id = id
        this.name = name
        this.url = url


    }

    fun getId(): Int{
        return this.id
    }

    fun getName(): String{
        return this.name
    }

    fun getUrl(): String{
        return this.url
    }

    override fun toString(): String {
        return "KindProduct(id=$id, name='$name', url='$url')"
    }


}