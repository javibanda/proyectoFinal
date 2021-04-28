package com.example.proyectofinal.util

import android.content.Context
import android.widget.Toast

object SetToast {

    fun set(string: String, context: Context?){
        Toast.makeText(
                context,
                string,
                Toast.LENGTH_LONG
        ).show()
    }
}