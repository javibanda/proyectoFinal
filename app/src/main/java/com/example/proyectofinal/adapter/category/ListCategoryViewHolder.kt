package com.example.proyectofinal.adapter.category

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.base.BaseViewHolder
import com.example.proyectofinal.data.Category
import com.example.proyectofinal.extensions.loadUrl
import com.example.proyectofinal.fragments.ListCategoryFragmentDirections
import com.example.proyectofinal.util.NavOptions


class ListCategoryViewHolder(itemView: View) : BaseViewHolder(itemView) {

    private val textView = itemView.findViewById<TextView>(R.id.nameMainList)
    private val imgView = itemView.findViewById<ImageView>(R.id.imgMainList)
    private val context = itemView.findViewById<ConstraintLayout>(R.id.mainList)
    private var id = 0
    private var action = ListCategoryFragmentDirections

    fun bindCategory(category: Category, fragment: Fragment) {
        textView.text = category.name
        id = category.id
        imgView.loadUrl(category.url)
        click(fragment)
    }

    private fun click(fragment: Fragment){

        context.setOnClickListener {
            navigate(fragment)
        }
//        textView.setOnClickListener {
//            navigate(fragment)
//        }
    }

    private fun navigate(fragment: Fragment) {
        when (id) {
            0 -> {
                NavHostFragment.findNavController(fragment).navigate(
                    action.actionCategoryFragmentToListProductFragment(intArrayOf(0,0)),
                    NavOptions.getAnimation()
                )
            }
            3 -> {
                NavHostFragment.findNavController(fragment).navigate(
                    action.actionCategoryFragmentToListProductFragment(intArrayOf(id,0)),
                    NavOptions.getAnimation()
                )
            }
            else -> {
                NavHostFragment.findNavController(fragment).navigate(
                    action.actionCategoryFragmentToListPlatformFragment(id),
                    NavOptions.getAnimation()
                )
            }
        }
    }



}