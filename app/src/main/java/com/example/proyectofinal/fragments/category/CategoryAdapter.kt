package com.example.proyectofinal.fragments.category

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.data.KindProduct
import com.example.proyectofinal.extensions.inflate
import com.example.proyectofinal.extensions.loadUrl

class CategoryAdapter(private val list: List<KindProduct>) : RecyclerView.Adapter<CategoryAdapter.KindProductViewHolder>() {



    class KindProductViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        val txtKindProduct = item.findViewById(R.id.nameKindProduct) as TextView
        val imgKindProduct = item.findViewById(R.id.imgKindProduct) as ImageView
        fun bindKindProduct(kindProduct: KindProduct){
            txtKindProduct.text = kindProduct.getName().capitalize()
            imgKindProduct.loadUrl(kindProduct.getUrl())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KindProductViewHolder {
        val view = parent.inflate(R.layout.item_category, false)
        return KindProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: KindProductViewHolder, position: Int) {
        val kindProduct = list[position]
        holder.bindKindProduct(kindProduct)
    }

    override fun getItemCount(): Int = list.size
}