package com.example.proyectofinal.data.cart
import com.example.proyectofinal.data.Product
import com.example.proyectofinal.extensions.setFormatFloat

open class Cart(){

    private val listDataCart = ArrayList<DataCart>()
    private val listProduct = ArrayList<Product>()
    private var price: Float = 0f

    fun add(product: Product, add: Int){
        price += product.price * add
        if(add >0){
            listProduct.add(product)
        }else{
            listProduct.removeAt(listProduct.lastIndex)
        }
        if (contains(product) >= 0){
            listDataCart[getIndex(product)].apply {
                updateQuantity(getQuantity() + add)
            }
        }else{
            listDataCart.add(DataCart(product, add))
        }
    }

    fun getDeliveryPrice(): Float{
        return if (price == 0f|| price > 30){
            0f
        }else{
            30f
        }
    }

    fun getTotalPrice() = price + getDeliveryPrice()

    fun getProducts() = listProduct

    fun getPriceString() = price.setFormatFloat()

    fun getTotalPriceString() = getTotalPrice().setFormatFloat()

    fun getDeliveryPriceString() = getDeliveryPrice().setFormatFloat()

    private fun contains(product: Product): Int{
        var contains = -1
        for((cont, i) in listDataCart.withIndex()){
            if (i.getProduct() == product){
                contains = cont
            }
        }
        return contains
    }

    private fun getIndex(product: Product): Int{
        return contains(product)
    }

    fun getListDataCart(): List<DataCart>{
        return listDataCart
    }

    fun getPrice() = price

    fun remove(position: Int){
        val price = listDataCart[position].getTotalPrice()
        this.price -= price
        listProduct.remove(listDataCart[position].getProduct())
        listDataCart.removeAt(position)
    }

    fun clear(){
        listProduct.clear()
        listDataCart.clear()
        price = 0.0f
    }

    fun isEmpty(): Boolean{
        return getListDataCart().isEmpty()
    }

    fun isNotEmpty(): Boolean {
        return getListDataCart().isNotEmpty()
    }
}