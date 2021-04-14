package com.example.proyectofinal.adapter.platform

import android.view.View
import androidx.fragment.app.Fragment
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.base.BaseRecyclerAdapter

import com.example.proyectofinal.data.Platform

class PlatformRecyclerAdapter (
    private val itemsList: List<Platform>,
    private val fragment: Fragment,
    private val idCategory: Int
    ) : BaseRecyclerAdapter<Platform, PlatformViewHolder>(itemsList)  {

        override fun createViewHolder(view: View, viewType: Int) = PlatformViewHolder(view)

        override fun onBindViewHolder(holder: PlatformViewHolder, position: Int) {
            holder.bindPlatform(itemsList[position], fragment, idCategory)
        }

        override fun getViewHolderLayoutId(viewType: Int) = R.layout.item_main_list
}