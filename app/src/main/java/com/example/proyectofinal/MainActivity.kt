package com.example.proyectofinal

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinal.dataBase.DataManager.DataManager.getListProduct


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

//    private fun navigationItemSelected(){
//        bottonmNav = findViewById(R.id.bottom_navigation_view)
//        bottonmNav.setOnNavigationItemReselectedListener { item ->
//            when(item.itemId){
//                R.id.bottom_nav_favorites ->{
//                    //addNewFragment(fragment2)
//                    startActivity(Intent(this@MainActivity, Chekin::class.java ))
//                }
//                R.id.bottom_nav_music ->{
//                }
//            }
//        }
//    }
}


