package com.example.proyectofinal.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.platform.ListPlatformRecyclerAdapter
import com.example.proyectofinal.dataBase.DataManager.DataManager.listPlatform
import com.example.proyectofinal.fragments.listPlatform.ListPlatformFragmentArgs


class ListPlatformFragment : Fragment() {
    val args: ListPlatformFragmentArgs by navArgs()
    private lateinit var recView: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_list_platform, container, false)
        recView = view.findViewById(R.id.myRecyclerPlatform)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(":::Args", args.idCategory.toString())

        val platformAdapter = ListPlatformRecyclerAdapter(listPlatform(), this, args.idCategory)
        recView.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = platformAdapter
        }
    }
}