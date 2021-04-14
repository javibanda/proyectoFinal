package com.example.proyectofinal.adapter.category

import android.view.View
import androidx.fragment.app.Fragment
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.base.BaseRecyclerAdapter
import com.example.proyectofinal.data.Category

class CategoryRecyclerAdapter(
        private val itemsList: List<Category>,
        private val fragment: Fragment
) : BaseRecyclerAdapter<Category, CategoryViewHolder>(itemsList)  {

    override fun createViewHolder(view: View, viewType: Int) = CategoryViewHolder(view)

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindCategory(itemsList[position], fragment)
    }

    override fun getViewHolderLayoutId(viewType: Int) = R.layout.item_main_list
}