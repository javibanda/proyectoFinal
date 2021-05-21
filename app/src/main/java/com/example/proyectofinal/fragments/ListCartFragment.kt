package com.example.proyectofinal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.cartHistory.ListCartHistoryRecyclerAdapter
import com.example.proyectofinal.dataBase.DataManager.getListCart

class ListCartFragment : Fragment() {
    private lateinit var recView: RecyclerView
    private val args: ListCartFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list_cart, container, false)
        recView = view.findViewById(R.id.recyclerListCart)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listCart = getListCart(args.idOrder).getListDataCart()
        val cartHistoryCartAdapter = ListCartHistoryRecyclerAdapter(listCart, this)
        recView.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = cartHistoryCartAdapter
        }
    }
}