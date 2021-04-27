package com.example.proyectofinal.adapter.cart

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.base.BaseRecyclerAdapter
import com.example.proyectofinal.data.cart.DataCart

class ListCartRecyclerAdapter(
        private val itemsList: List<DataCart>,
        private val fragment: Fragment
): BaseRecyclerAdapter<DataCart, ListCartViewHolder>(itemsList){
    override fun getViewHolderLayoutId(viewType: Int) = R.layout.item_cart_list

    override fun createViewHolder(view: View, viewType: Int) = ListCartViewHolder(view)

    override fun onBindViewHolder(holder: ListCartViewHolder, position: Int) {
        holder.bindCart(itemsList[position], fragment)
    }

}