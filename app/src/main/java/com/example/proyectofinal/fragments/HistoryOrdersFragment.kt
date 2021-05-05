package com.example.proyectofinal.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.history.ListHistoryRecyclerAdapter
import com.example.proyectofinal.data.User
import com.example.proyectofinal.dataBase.DataManager.getOrderHistory


class HistoryOrdersFragment : Fragment() {
    private lateinit var recView: RecyclerView
    private lateinit var txtEmptyHistory: TextView
    private val listHistory = getOrderHistory(User.getUser()!!)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history_orders, container, false)
        txtEmptyHistory = view.findViewById(R.id.txtEmptyHistory)
        recView = view.findViewById(R.id.recyclerHistory)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listHistoryIsEmpty()
        Log.d(":::History", listHistory.size.toString())
        val historyAdapter = ListHistoryRecyclerAdapter(listHistory, this)
        recView.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = historyAdapter
        }
    }

    private fun showTxtEmptyHistory(){
        txtEmptyHistory.visibility = View.VISIBLE
    }

    private fun hideTxtEmptyHistory(){
        txtEmptyHistory.visibility = View.INVISIBLE
    }

    private fun listHistoryIsEmpty(){
        if (listHistory.isEmpty()){
            showTxtEmptyHistory()
        }else{
            hideTxtEmptyHistory()
        }
    }



}