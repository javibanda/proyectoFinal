package com.example.proyectofinal.data


data class Product(
        val id: Int,
        val name: String,
        val description: String,
        val price: Float,
        val category: Category,
        val platform: Platform,
        val rating: Float,
        //val listImg: List<String>
        ){

        override fun toString(): String {
                return "Product(id=$id, name='$name', description='$description', price=$price, category=$category, platform=$platform, rating=$rating)"
        }
}
