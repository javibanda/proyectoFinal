package com.example.proyectofinal

import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinal.data.City


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)



        var c = SqlConection()
        c.conecting()
        Log.d(":::pruebaReal", c.toString())

       for (region in c.listRegion()){
           Log.d(":::Region", region.toString())

               Log.d(":::City", region.getCity().toString())

       }
        c.disconect()





    }
}