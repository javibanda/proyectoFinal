package com.example.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.proyectofinal.dataBase.DataManager.DataManager.listKindProduct
import com.example.proyectofinal.fragments.Fragment2
import com.example.proyectofinal.lists.citys.MainFragment



class MainActivity : AppCompatActivity() {

    val fragment = MainFragment.newInstance()
    val fragment2 = Fragment2.newInstance()


    private lateinit var btnFragment1: ImageView
    private lateinit var btnFragment2: ImageView
    private lateinit var btnFragment3: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        showFragment(fragment)

        btnFragment1 = findViewById(R.id.btnFragment1)
        btnFragment2 = findViewById(R.id.btnFragment2)
        btnFragment3 = findViewById(R.id.btnFragment3)
        focusButtonColor(btnFragment2)
        btnFragment1.setOnClickListener {
            focusButtonColor(btnFragment1)
        }

        btnFragment2.setOnClickListener {
            focusButtonColor(btnFragment2)
        }

        btnFragment3.setOnClickListener {
            focusButtonColor(btnFragment3)
        }
    }

    fun showFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayoutMain, fragment)
        fragmentTransaction.commit()
    }

    private fun focusButtonColor(button: ImageView){
        unFocusAllButtonColor()
        button.setColorFilter(R.color.design_default_color_primary_dark)
    }

    private fun unFocusAllButtonColor(){
        btnFragment1.clearColorFilter()
        btnFragment2.clearColorFilter()
        btnFragment3.clearColorFilter()
    }
}


