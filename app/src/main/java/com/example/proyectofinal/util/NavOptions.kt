package com.example.proyectofinal.util

import com.example.proyectofinal.R

object NavOptions {

    fun getAnimation() =
            androidx.navigation.navOptions {
                anim {
                    enter = R.anim.slide_in_right
                    exit = R.anim.slide_out_left
                    popEnter = R.anim.slide_in_left
                    popExit = R.anim.slide_out_right
                }
            }
}