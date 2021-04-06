package com.example.proyectofinal.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.proyectofinal.R
import com.squareup.picasso.Picasso

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
        LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun ImageView.loadUrl(url: String){
    Picasso.with(context).load(url).into(this)
}


fun navOptions(): NavOptions {
    val options = androidx.navigation.navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }
    return options
}
