package com.example.proyectofinal.adapter.base

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<ItemType, RvViewHolder : RecyclerView.ViewHolder>(
        private val itemsList: List<ItemType>
) : RecyclerView.Adapter<RvViewHolder>(), BaseAdapterHolder {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            createViewHolder(createView(parent, viewType), viewType)

    abstract fun createViewHolder(view: View, viewType: Int): RvViewHolder

    override fun getItemCount(): Int = itemsList.size
}