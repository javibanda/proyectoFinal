package com.example.proyectofinal.fragments.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.data.Platform
import com.example.proyectofinal.data.Product
import com.example.proyectofinal.dataBase.DataManager
import com.example.proyectofinal.dataBase.DataManager.DataManager.getListProduct
import com.example.proyectofinal.dataBase.DataManager.DataManager.getProduct
import com.example.proyectofinal.extensions.loadUrl
import com.example.proyectofinal.fragments.listPlatform.ListPlatformFragmentArgs
import com.example.proyectofinal.fragments.listProduct.ListProductFragmentArgs

class ProductFragment : Fragment() {

    val args: ProductFragmentArgs by navArgs()
    private lateinit var txtName: TextView
    private lateinit var txtPlatform: TextView
    private lateinit var txtPrice1: TextView
    private lateinit var txtPrice2: TextView
    private lateinit var txtDetails: TextView
    private lateinit var recyclerView : RecyclerView
    private lateinit var imgProduct : ImageView
    private var product: Product? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_product, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewImg)
        imgProduct = view.findViewById(R.id.imgProduct)
        txtName = view.findViewById(R.id.txtNameProduct)
        txtPlatform = view.findViewById(R.id.txtPlatform)
        txtDetails = view.findViewById(R.id.txtDetails)
        txtPrice1 = view.findViewById(R.id.txtPrice1)
        txtPrice2 = view.findViewById(R.id.txtPrice2)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        product = getProduct(args.idProduct)

        txtDetails.text = product!!.description
        txtName.text = product!!.name.toUpperCase()
        txtPlatform.text = product!!.platform.name

        setPrice()
        setImg()

    }

    private fun setPrice(){
        val price = (product!!.price - 0.05).toString()
        val arrayPrice = price.split('.')
        txtPrice1.text = arrayPrice[0] + ","
        txtPrice2.text = arrayPrice[1] + "€"
    }

    private fun listImgIsEmpty(): Boolean{
        return product!!.listImg.isNotEmpty()
    }

    private fun listImgSizeIsOne(): Boolean{
        return product!!.listImg.size == 1
    }

    private fun showImgView(){
        imgProduct.loadUrl(product!!.listImg.first().url)

    }

    private fun setImg(){
        if (listImgIsEmpty()){
            if (listImgSizeIsOne()){
                showImgView()
            }
        }
    }

}