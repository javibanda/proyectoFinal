package com.example.proyectofinal.data

import com.example.proyectofinal.extensions.setFormatFloat


data class Product(
        val id: Int,
        val name: String,
        val description: String,
        val price: Float,
        val category: Category,
        val platform: Platform,
        var rating: Float,
        var numRating: Int,
        val listImg: List<ProductImg>
        ){

        fun getRatingString(): String{
                return if(numRating == 0){
                        "N/R"
                }else{
                        rating.setFormatFloat()
                }
        }

        override fun toString() =
                "Product(id=$id," +
                " name='$name'," +
                " description='$description'," +
                " price=$price," +
                " category=$category," +
                " platform=$platform," +
                " rating=$rating)," +
                " numRating=$numRating)," +
                " imgProduct=$listImg"

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Product

                if (id != other.id) return false

                return true
        }

        override fun hashCode(): Int {
                return id
        }

        fun getPriceString() = price.setFormatFloat()


}
