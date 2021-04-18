package com.example.proyectofinal.fragments.listProduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.product.ListProductRecyclerAdapter
import com.example.proyectofinal.dataBase.DataManager.DataManager.getListProduct


class ListProductFragment : Fragment() {
    val args: ListProductFragmentArgs by navArgs()

    private lateinit var recView: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list_product, container, false)
        recView = view.findViewById(R.id.myRecyclerProduct)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productAdapter = ListProductRecyclerAdapter(getListProduct(args.idCategoryPlatform[0], args.idCategoryPlatform[1]), this)
        recView.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = productAdapter
        }
    }

//    private fun choseProductList(): List<Product>{
//        val listProduct: List<Product>
//        if(args.idCategoryPlatform == 0){
//            return getListProduct()
//        }else{
//            return getListProduct()
//        }
//    }
}