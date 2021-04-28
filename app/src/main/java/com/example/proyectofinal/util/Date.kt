package com.example.proyectofinal.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object Date {
    @SuppressLint("SimpleDateFormat")
    fun getDate(): String{
        val cal = Calendar.getInstance()

        return SimpleDateFormat(
                "hh:mm:ss dd/MM/yyyy").format(cal.time)
    }
}