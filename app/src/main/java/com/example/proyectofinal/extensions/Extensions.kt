package com.example.proyectofinal.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.navigation.NavOptions
import com.example.proyectofinal.R
import com.squareup.picasso.Picasso
import java.util.*

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
        LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun ImageView.loadUrl(url: String, ){
    Picasso.with(context).load(url).resize(context.convertDpToPX( 200), context.convertDpToPX(100)).into(this)
}




fun EditText.toUpperCaseDefaultLocale():String? =
        text.toString().toUpperCase(Locale.getDefault())

fun EditText.toLowerCaseDefaultLocale():String? =
        text.toString().toLowerCase(Locale.getDefault())

fun Context.convertDpToPX( dp: Int): Int {
    return (dp * resources.displayMetrics.density).toInt()
}