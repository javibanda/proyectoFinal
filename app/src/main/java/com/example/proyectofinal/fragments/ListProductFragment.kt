package com.example.proyectofinal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.product.ListProductRecyclerAdapter
import com.example.proyectofinal.data.Product
import com.example.proyectofinal.dataBase.DataManager.getListProduct
import com.example.proyectofinal.fragments.ListProductFragmentArgs
import kotlin.collections.ArrayList


class ListProductFragment : Fragment() {
    private val args: ListProductFragmentArgs by navArgs()

    private lateinit var recView: RecyclerView
    private lateinit var searchView: SearchView

    private val listProduct = ArrayList<Product>()
    private val displayProduct = ArrayList<Product>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list_product, container, false)
        recView = view.findViewById(R.id.myRecyclerProduct)
        searchView = view.findViewById(R.id.searchView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listProduct.clear()
        listProduct.addAll(getListProduct(args.idCategoryPlatform[0], args.idCategoryPlatform[1]))
        displayProduct.addAll(listProduct)
        val productAdapter = ListProductRecyclerAdapter(displayProduct, this)

        recView.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = productAdapter
        }

        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isNotEmpty()){
                    displayProduct.clear()
                    val search = newText.toLowerCase()
                    listProduct.forEach { it
                        if (it.name.toLowerCase().contains(search)){
                            displayProduct.add(it)
                        }
                    }
                    recView.adapter!!.notifyDataSetChanged()

                }else{
                    displayProduct.clear()
                    displayProduct.addAll(listProduct)
                    recView.adapter!!.notifyDataSetChanged()
                }
                return true
            }
        })
    }
}