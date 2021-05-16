package com.example.proyectofinal.adapter.favorite

import android.annotation.SuppressLint
import android.media.Image
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.base.BaseViewHolder
import com.example.proyectofinal.data.Product
import com.example.proyectofinal.data.cart.LocalCart
import com.example.proyectofinal.extensions.loadUrl
import com.example.proyectofinal.fragments.ListFavoritesFragmentDirections
import com.example.proyectofinal.util.NavOptions

class ListFavoriteViewHolder (itemView: View): BaseViewHolder(itemView){

    private val imgProductFavorite = itemView.findViewById<ImageView>(R.id.imgProductFavorite)
    private val txtNameFavorite = itemView.findViewById<TextView>(R.id.txtNameFavorite)
    private val txtPlatformFavorite = itemView.findViewById<TextView>(R.id.txtPlatformFavorite)
    private val txtPriceFavorite =  itemView.findViewById<TextView>(R.id.txtPriceFavorite)
    private val btnAddCartFavorite = itemView.findViewById<Button>(R.id.btnAddCartFavorite)
    private val btnNavProduct =  itemView.findViewById<Button>(R.id.btnNavProduct)

    private val action = ListFavoritesFragmentDirections

    fun bindProducts(product: Product, fragment: Fragment){
        setImg(product)
        setTextTextView(product)
        setDirectionToProductFragment(fragment, product)
        addProductToCart(product)
    }

    private fun setImg(product: Product){
        imgProductFavorite.loadUrl(product.listImg.first().url)
    }

    @SuppressLint("SetTextI18n")
    private fun setTextTextView(product: Product){
        txtNameFavorite.text = product.name.toLowerCase()
        txtPlatformFavorite.text = product.platform.name
        txtPriceFavorite.text = product.getPriceString() + "€"
    }

    private fun setDirectionToProductFragment(fragment: Fragment, product: Product){
        btnNavProduct.setOnClickListener {
            NavHostFragment.findNavController(fragment).navigate(
                action.actionListFavoritesFragmentToProductFragment(product.id)
            )
        }
    }

    private fun addProductToCart(product: Product){
        btnAddCartFavorite.setOnClickListener {
            LocalCart.add(product, 1)
        }
    }

}