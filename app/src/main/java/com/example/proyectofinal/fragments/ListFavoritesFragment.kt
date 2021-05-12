package com.example.proyectofinal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.favorite.ListFavoriteRecyclerAdapter
import com.example.proyectofinal.data.Product
import com.example.proyectofinal.data.User
import com.example.proyectofinal.dataBase.DataManager
import com.example.proyectofinal.dataBase.DataManager.getProduct


class ListFavoritesFragment : Fragment() {
    private lateinit var recView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_favorites, container, false)
        recView = view.findViewById(R.id.recyclerFavorites)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favoriteAdapter = ListFavoriteRecyclerAdapter(getFavoriteList(), this)
        recView.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }

    private fun getFavoriteList(): List<Product>{
        val listFavorites = ArrayList<Product>()
        for (i in User.getUser()!!.listFavorites){
            listFavorites.add(getProduct(i))
        }
        return listFavorites
    }

}