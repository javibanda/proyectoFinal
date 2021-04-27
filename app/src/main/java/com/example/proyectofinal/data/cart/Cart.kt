package com.example.proyectofinal.data.cart

import com.example.proyectofinal.data.Product

object Cart {
    private val listCart = ArrayList<DataCart>()
    private var totalPrice: Float = 0.0f

    fun add(product: Product, add: Int){
        totalPrice += product.price * add
        if (contains(product)>= 0){
            listCart[getIndex(product)].apply {
                updateQuantity(getQuantity() + add)
            }
        }else{
            listCart.add(DataCart(product, add))
        }
    }

    private fun getIndex(product: Product): Int{
        return contains(product)
    }

    fun getListCart(): List<DataCart>{
        return listCart
    }

    fun getTotalPrice() = totalPrice

    private fun contains(product: Product): Int{
        var contains = -1
        for((cont, i) in listCart.withIndex()){
            if (i.getProduct() == product){
                contains = cont
            }
        }
        return contains
    }


    fun remove(idProduct: Int){
        listCart.removeAt(idProduct)
    }

    fun clear(){
        listCart.clear()
        totalPrice = 0.0f
    }

    fun isEmpty(): Boolean{
        return getListCart().isEmpty()
    }

    fun isNotEmpty(): Boolean{
        return getListCart().isNotEmpty()
    }


}