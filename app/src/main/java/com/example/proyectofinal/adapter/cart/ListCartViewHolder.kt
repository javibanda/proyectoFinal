package com.example.proyectofinal.adapter.cart

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.base.BaseViewHolder
import com.example.proyectofinal.data.cart.Cart
import com.example.proyectofinal.data.cart.DataCart

class ListCartViewHolder(itemView: View) : BaseViewHolder(itemView) {


    private val txtName = itemView.findViewById<TextView>(R.id.txtNameCart)
    private val txtPlatform = itemView.findViewById<TextView>(R.id.txtPlatformCart)
    private val btnSubtract = itemView.findViewById<ImageView>(R.id.btnSubtractCart)
    private val btnAdd = itemView.findViewById<ImageView>(R.id.btnAddCart)
    private val txtCount = itemView.findViewById<TextView>(R.id.txtCountCart)
    private val txtPriceProduct = itemView.findViewById<TextView>(R.id.txtPriceCart)

    fun bindCart(cart: DataCart, fragment: Fragment) {

        txtName.text = cart.getProductName()
        txtPlatform.text = cart.getProductPlatformName()
        txtPriceProduct.text = cart.getTotalPrice().toString()
        txtCount.text = cart.getQuantity().toString()
        clickAddOrRemoveOne(cart)


    }

    private fun clickAddOrRemoveOne(cart: DataCart) {

        btnAdd.setOnClickListener {
            addOrRemoveOne(cart, 1)
        }

        btnSubtract.setOnClickListener {
            if (cart.getQuantity() != 1) {
                addOrRemoveOne(cart, -1)
            }
        }
    }

    private fun addOrRemoveOne(cart: DataCart, add: Int) {
        Cart.add(cart.getProduct(), add)
        txtPriceProduct.text = cart.getTotalPrice().toString()
        txtCount.text = cart.getQuantity().toString()

    }



}