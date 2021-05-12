package com.example.proyectofinal.adapter.favorite

import android.media.Image
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.Fragment
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.base.BaseViewHolder
import com.example.proyectofinal.data.Product

class ListFavoriteViewHolder (itemView: View): BaseViewHolder(itemView){

    private val imgProductFavorite = itemView.findViewById<ImageView>(R.id.imgProductFavorite)
    private val txtNameFavorite = itemView.findViewById<TextView>(R.id.txtNameFavorite)
    private val txtPlatformFavorite = itemView.findViewById<TextView>(R.id.txtPlatformFavorite)
    private val txtPriceFavorite =  itemView.findViewById<TextView>(R.id.txtPriceFavorite)
    private val btnAddCartFavorite = itemView.findViewById<Button>(R.id.btnAddCartFavorite)
    private val btnNavProduct =  itemView.findViewById<Button>(R.id.btnNavProduct)

    fun bindProducts(product: Product, fragment: Fragment){

    }
}