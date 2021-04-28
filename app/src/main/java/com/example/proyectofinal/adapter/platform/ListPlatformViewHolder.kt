package com.example.proyectofinal.adapter.platform

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.compose.navArgument
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.base.BaseViewHolder
import com.example.proyectofinal.data.Platform
import com.example.proyectofinal.extensions.loadUrl
import com.example.proyectofinal.fragments.ListPlatformFragmentArgs
import com.example.proyectofinal.fragments.ListPlatformFragmentDirections
import com.example.proyectofinal.util.NavOptions

class ListPlatformViewHolder (itemView: View) : BaseViewHolder(itemView) {

    private val textView = itemView.findViewById<TextView>(R.id.nameMainList)
    private val imgView = itemView.findViewById<ImageView>(R.id.imgMainList)
    private val action = ListPlatformFragmentDirections



    fun bindPlatform(platform: Platform, fragment: Fragment, idCategory: Int) {

        textView.text = platform.name
        imgView.loadUrl(platform.url)

        imgView.setOnClickListener {
            NavHostFragment.findNavController(fragment).navigate(
                    action.actionListPlatformFragmentToListProductFragment(intArrayOf(idCategory, platform.id)),
                    NavOptions.getAnimation()
            )
        }
    }
}