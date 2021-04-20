package com.example.proyectofinal.adapter.user

import android.view.View
import androidx.fragment.app.Fragment
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.base.BaseRecyclerAdapter
import com.example.proyectofinal.adapter.product.ListProductViewHolder
import com.example.proyectofinal.data.Product

class ListUserRecyclerAdapter (
    private val itemsList: List<String>,
    private val fragment: Fragment
) : BaseRecyclerAdapter<String, ListUserViewHolder>(itemsList) {

    override fun createViewHolder(view: View, viewType: Int) = ListUserViewHolder(view)

    override fun getViewHolderLayoutId(viewType: Int) = R.layout.item_user_list

    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        holder.bindUser(itemsList[position], fragment)
    }

}