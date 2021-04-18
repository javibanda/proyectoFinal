package com.example.proyectofinal.adapter.product

import android.view.View
import androidx.fragment.app.Fragment
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.base.BaseRecyclerAdapter
import com.example.proyectofinal.data.Product
import kotlin.collections.List

class ListProductRecyclerAdapter (
        private val itemsList: List<Product>,
        private val fragment: Fragment
    ) : BaseRecyclerAdapter<Product, ListProductViewHolder>(itemsList) {

    override fun createViewHolder(view: View, viewType: Int) = ListProductViewHolder(view)

    override fun getViewHolderLayoutId(viewType: Int) = R.layout.item_product_list

    override fun onBindViewHolder(holder: ListProductViewHolder, position: Int) {
        holder.bindProduct(itemsList[position], fragment)
    }


}