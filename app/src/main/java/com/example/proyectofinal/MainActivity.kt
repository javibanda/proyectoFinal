package com.example.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.proyectofinal.dataBase.DataManager.DataManager.listProduct
import com.example.proyectofinal.fragments.Fragment2
import com.example.proyectofinal.fragments.FragmentSwitch
import com.example.proyectofinal.fragments.category.CategoryFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class MainActivity : AppCompatActivity(), FragmentSwitch {

    val fragment = CategoryFragment.newInstance()
    val fragment2 = Fragment2.newInstance()
    val listFragment = Stack<Fragment>()
    val fm = supportFragmentManager

    private lateinit var btnProduct: BottomNavigationView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        showFragment(fragment)
        addNewFragment(fragment)
        navigationItemSelected()

        Log.d(":::Product", listProduct().toString())
    }

    private fun navigationItemSelected(){
        btnProduct = findViewById(R.id.bottom_navigation_view)
        btnProduct.setOnNavigationItemReselectedListener { item ->
            when(item.itemId){
                R.id.bottom_nav_favorites ->{
                    //addNewFragment(fragment2)
                    startActivity(Intent(this@MainActivity, Chekin::class.java ))
                }

                R.id.bottom_nav_music ->{

                    //addNewFragment(fragment)

                    //onBackPressedPopBackstack()
                }
            }
        }

    }

    fun showFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayoutMain, fragment)
        fragmentTransaction.commit()
    }

    fun addNewFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayoutMain, fragment)
        fragmentTransaction.addToBackStack(null)
        listFragment.push(fragment)
        fragmentTransaction.commit()
        Log.d(":::ListaFragments", listFragment.size.toString())
        Log.d(":::Fragments reales", fm.backStackEntryCount.toString())
    }

    private fun clearFragments(){

    }

    fun onBackPressedPopBackstack(){
        finish()
    }

    override fun switchCategoryProduct() {
        fragment2
    }


}


