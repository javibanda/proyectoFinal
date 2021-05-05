package com.example.proyectofinal.adapter.cartHistory

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.base.BaseViewHolder
import com.example.proyectofinal.data.cart.DataCart
import com.example.proyectofinal.extensions.loadUrl

class ListCartHistoryViewHolder(itemView: View): BaseViewHolder(itemView) {

    private val txtNameProductCartHistory = itemView
        .findViewById<TextView>(R.id.txtNameProductCartHistory)
    private val txtPlatformProductCartHistory = itemView
        .findViewById<TextView>(R.id.txtPlatformProductCartHistory)
    private val txtPriceProductCartHistory = itemView
        .findViewById<TextView>(R.id.txtPriceProductCartHistory)
    private val txtQuantityProductCartHistory =  itemView
        .findViewById<TextView>(R.id.txtQuantityProductCartHistory)
    private val txtTotalPriceProductCartHistory = itemView
        .findViewById<TextView>(R.id.txtTotalPriceProductCartHistory)
    private val imgProductCartHistory = itemView
        .findViewById<ImageView>(R.id.imgProductCartHistory)

    fun bindListCart(cart: DataCart, fragment: Fragment){
        txtNameProductCartHistory.text = cart.getProduct().name
        txtPlatformProductCartHistory.text = cart.getProductPlatformName()
        txtPriceProductCartHistory.text = cart.getProduct().price.toString()
        txtQuantityProductCartHistory.text = cart.getQuantity().toString()
        txtTotalPriceProductCartHistory.text = String.format("%.2f",cart.getTotalPrice())
        imgProductCartHistory.loadUrl(cart.getProduct().listImg.first().url)
    }
}