package com.example.proyectofinal.adapter.cart

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.base.BaseViewHolder
import com.example.proyectofinal.adapter.base.OnItemClickListener
import com.example.proyectofinal.data.cart.Cart
import com.example.proyectofinal.data.cart.DataCart

class ListCartViewHolder(itemView: View) : BaseViewHolder(itemView){

    private val txtName = itemView.findViewById<TextView>(R.id.txtNameCart)
    private val txtPlatform = itemView.findViewById<TextView>(R.id.txtPlatformCart)
    private val btnSubtract = itemView.findViewById<ImageView>(R.id.btnSubtractCart)
    private val btnAdd = itemView.findViewById<ImageView>(R.id.btnAddCart)
    private val txtCount = itemView.findViewById<TextView>(R.id.txtCountCart)
    private val txtPriceProduct = itemView.findViewById<TextView>(R.id.txtPriceCart)
    private lateinit var listener: OnItemClickListener

    fun bindCart(cart: DataCart, fragment: Fragment, listener: OnItemClickListener) {
        this.listener = listener
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
            if (cart.getQuantity() > 1) {
                addOrRemoveOne(cart, -1)
            }else{
                removeItem()
            }
        }
    }

    private fun removeItem(){
            listener.onItemClick(adapterPosition)
    }

    private fun addOrRemoveOne(cart: DataCart, add: Int) {
        Cart.add(cart.getProduct(), add)
        listener.onItemClick()
        txtPriceProduct.text = String.format("%.2f", cart.getTotalPrice())
        txtCount.text = cart.getQuantity().toString()
    }
}