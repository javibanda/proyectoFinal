package com.example.proyectofinal.adapter.favorite

import android.view.View
import androidx.fragment.app.Fragment
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.base.BaseRecyclerAdapter
import com.example.proyectofinal.data.Product

class ListFavoriteRecyclerAdapter(
    private val itemsList: List<Product>,
    private val fragment: Fragment
): BaseRecyclerAdapter<Product, ListFavoriteViewHolder> (itemsList){
    override fun createViewHolder(view: View, viewType: Int) = ListFavoriteViewHolder(view)

    override fun getViewHolderLayoutId(viewType: Int) = R.layout.item_favorite

    override fun onBindViewHolder(holder: ListFavoriteViewHolder, position: Int) {
        holder.bindProducts(itemsList[position], fragment)
    }

}