package com.example.proyectofinal.adapter.cartHistory

import android.view.View
import androidx.fragment.app.Fragment
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.base.BaseRecyclerAdapter
import com.example.proyectofinal.data.cart.Cart
import com.example.proyectofinal.data.cart.DataCart

class ListCartHistoryRecyclerAdapter (
    private val itemsList: List<DataCart>,
    private val fragment: Fragment
) : BaseRecyclerAdapter<DataCart, ListCartHistoryViewHolder>(itemsList)  {

    override fun createViewHolder(view: View, viewType: Int) =  ListCartHistoryViewHolder(view)

    override fun getViewHolderLayoutId(viewType: Int) = R.layout.item_history_cart

    override fun onBindViewHolder(holder: ListCartHistoryViewHolder, position: Int) {
        holder.bindListCart(itemsList[position], fragment)
    }
}