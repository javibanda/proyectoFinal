package com.example.proyectofinal.adapter.history

import android.view.View
import androidx.fragment.app.Fragment
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.base.BaseRecyclerAdapter
import com.example.proyectofinal.adapter.category.ListCategoryViewHolder
import com.example.proyectofinal.data.Category
import com.example.proyectofinal.data.Order

class ListHistoryRecyclerAdapter(
        private val itemsList: List<Order>,
        private val fragment: Fragment
) : BaseRecyclerAdapter<Order, ListHistoryViewHolder>(itemsList)  {
    override fun createViewHolder(view: View, viewType: Int) = ListHistoryViewHolder(view)

    override fun getViewHolderLayoutId(viewType: Int) = R.layout.item_history

    override fun onBindViewHolder(holder: ListHistoryViewHolder, position: Int) {
        holder.bindOrders(itemsList[position], fragment)
    }
}