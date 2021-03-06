package com.example.proyectofinal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.user.ListUserRecyclerAdapter


class ListUserFragment : Fragment() {
    private lateinit var recView: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list_user, container, false)
        recView = view.findViewById(R.id.myRecyclerUser)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userAdapter = ListUserRecyclerAdapter(listOptions(), this)
        recView.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = userAdapter
        }
    }

    private fun listOptions():List<String>{
        val listOptions = ArrayList<String>()
        listOptions.add("Información")
        listOptions.add("Historial de compras")
        listOptions.add("Favoritos")
        listOptions.add("Cerrar Sesión")
        return listOptions
    }
}