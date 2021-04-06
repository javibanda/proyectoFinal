package com.example.proyectofinal.fragments.category

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.data.Category
import com.example.proyectofinal.extensions.inflate
import com.example.proyectofinal.extensions.loadUrl
import com.example.proyectofinal.extensions.navOptions

class CategoryAdapter(private val list: List<Category>, fragment: Fragment) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    var fragment = fragment

    class CategoryViewHolder(item: View, fragment: Fragment) : RecyclerView.ViewHolder(item) {
        val txtKindProduct = item.findViewById(R.id.nameKindProduct) as TextView
        val imgKindProduct = item.findViewById(R.id.imgKindProduct) as ImageView
        val fragment = fragment
        fun bindCategory(category: Category){
            txtKindProduct.text = category.name.capitalize()
            imgKindProduct.loadUrl(category.url)
            itemView.setOnClickListener{
                Log.d(":::Clicado", category.id.toString())
                findNavController(fragment).navigate(R.id.fragment2,null, navOptions())
            }
            txtKindProduct.setOnClickListener{
                Log.d(":::Clicado", category.id.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = parent.inflate(R.layout.item_category, false)
        return CategoryViewHolder(view, fragment)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = list[position]
        holder.bindCategory(category)
    }

    override fun getItemCount(): Int = list.size
}