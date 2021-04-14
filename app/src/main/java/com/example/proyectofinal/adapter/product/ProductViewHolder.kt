package com.example.proyectofinal.adapter.product

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.Fragment
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.base.BaseViewHolder
import com.example.proyectofinal.data.Product
import com.example.proyectofinal.extensions.loadUrl

class ProductViewHolder(itemView: View) : BaseViewHolder(itemView){

    private val textView =  itemView.findViewById<TextView>(R.id.txtName)
    private val imageView = itemView.findViewById<ImageView>(R.id.imgProductList)

    fun bindProduct(product: Product, fragment: Fragment){

        textView.text = product.name
        setImg(product)

    }

    private fun setImg(product: Product){
        if(product.listImg.isNotEmpty()){
            imageView.loadUrl(product.listImg.first().url)
        }
    }
}