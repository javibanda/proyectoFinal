package com.example.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.proyectofinal.dataBase.DataManager
import com.example.proyectofinal.fragments.Fragment2
import com.example.proyectofinal.fragments.MainFragment



class MainActivity : AppCompatActivity() {

    val fragment = MainFragment.newInstance()
    val fragment2 = Fragment2.newInstance()


    private lateinit var btnFragment: Button
    private lateinit var btnFragment2: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        showFragment(fragment)

        btnFragment = findViewById(R.id.btnFagmen1)
        btnFragment2 = findViewById(R.id.btnFragment2)

        btnFragment.setOnClickListener {
            startActivity(Intent(this@MainActivity, Chekin::class.java ))
        }

        btnFragment2.setOnClickListener {

        }
    }

    fun showFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayoutMain, fragment)
        fragmentTransaction.commit()
    }




}


