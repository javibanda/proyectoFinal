package com.example.proyectofinal.adapter.product

import android.view.View
import androidx.fragment.app.Fragment
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.base.BaseRecyclerAdapter
import com.example.proyectofinal.data.Product

class ProductRecyclerAdapter (
    private val itemsList: List<Product>,
    private val fragment: Fragment
    ) : BaseRecyclerAdapter<Product, ProductViewHolder>(itemsList) {

    override fun createViewHolder(view: View, viewType: Int) = ProductViewHolder(view)

    override fun getViewHolderLayoutId(viewType: Int) = R.layout.item_product_list

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindProduct(itemsList[position], fragment)
    }


}