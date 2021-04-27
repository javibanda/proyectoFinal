package com.example.proyectofinal.data.cart

import com.example.proyectofinal.data.Product

class DataCart(private val product: Product, count: Int) {

    private val price: Float = product.price

    private var quantity: Int = count

    fun updateQuantity(count: Int) {
        quantity = count
    }

    fun getTotalPrice() = (price * quantity)

    fun getQuantity() = quantity

    fun getProduct() = product

    fun getProductName() = product.name

    fun getProductPlatformName() = product.platform.name
}