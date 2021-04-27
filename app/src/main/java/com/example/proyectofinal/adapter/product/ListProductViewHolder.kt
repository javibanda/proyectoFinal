package com.example.proyectofinal.adapter.product

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.base.BaseViewHolder
import com.example.proyectofinal.data.Product
import com.example.proyectofinal.extensions.loadUrl
import com.example.proyectofinal.fragments.listProduct.ListProductFragmentDirections
import com.example.proyectofinal.util.NavOptions

class ListProductViewHolder(itemView: View) : BaseViewHolder(itemView){

    private val textView =  itemView.findViewById<TextView>(R.id.txtName)
    private val imageView = itemView.findViewById<ImageView>(R.id.imgProductList)
    private val context = itemView.findViewById<ConstraintLayout>(R.id.itemProductList)
    private var action = ListProductFragmentDirections
    private var id = 0

    fun bindProduct(product: Product, fragment: Fragment){
        this.id = product.id
        textView.text = product.name
        setImg(product)
        click(fragment)

    }

    private fun setImg(product: Product){
        if(product.listImg.isNotEmpty()){
            imageView.loadUrl(product.listImg.first().url)
        }
    }

    private fun click(fragment: Fragment){

        context.setOnClickListener {
            navigate(fragment)
        }
//        textView.setOnClickListener {
//            navigate(fragment)
//        }
    }

    private fun navigate(fragment: Fragment){
        NavHostFragment.findNavController(fragment).navigate(
                action.actionListProductFragmentToProductFragment(id),
                NavOptions.getAnimation()
        )
    }
}