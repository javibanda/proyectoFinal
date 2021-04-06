package com.example.proyectofinal

import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.proyectofinal.dataBase.DataManager.DataManager.listProduct
import com.example.proyectofinal.fragments.Fragment2
import com.example.proyectofinal.fragments.category.CategoryFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class MainActivity : AppCompatActivity(){

    val fragment = CategoryFragment.newInstance()
    val fragment2 = Fragment2.newInstance()


    private lateinit var bottonmNav: BottomNavigationView
    lateinit var navController: NavController
    lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        showFragment(fragment)

        Log.d(":::Product", listProduct().toString())
    }

//    private fun navigationItemSelected(){
//        bottonmNav = findViewById(R.id.bottom_navigation_view)
//        bottonmNav.setOnNavigationItemReselectedListener { item ->
//            when(item.itemId){
//                R.id.bottom_nav_favorites ->{
//                    //addNewFragment(fragment2)
//                    startActivity(Intent(this@MainActivity, Chekin::class.java ))
//                }
//
//                R.id.bottom_nav_music ->{
//
//
//
//
//                }
//            }
//        }
//
//    }

    fun showFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayoutMain, fragment)
        fragmentTransaction.commit()
    }
}


