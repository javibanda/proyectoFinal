package com.example.proyectofinal.fragments

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.graphics.toColorInt
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.data.Product
import com.example.proyectofinal.data.User.getIsConnected
import com.example.proyectofinal.data.User.getUser
import com.example.proyectofinal.data.cart.LocalCart
import com.example.proyectofinal.dataBase.DataManager.deleteFavorites
import com.example.proyectofinal.dataBase.DataManager.getProduct
import com.example.proyectofinal.dataBase.DataManager.getRate
import com.example.proyectofinal.dataBase.DataManager.insertIntoFavorites
import com.example.proyectofinal.dataBase.DataManager.isRated
import com.example.proyectofinal.dataBase.DataManager.setRate
import com.example.proyectofinal.extensions.loadUrl
import com.example.proyectofinal.util.SetToast


@Suppress("DEPRECATION")
class ProductFragment : Fragment() {

    private val args: ProductFragmentArgs by navArgs()
    private lateinit var txtName: TextView
    private lateinit var txtPlatform: TextView
    private lateinit var txtPrice1: TextView
    private lateinit var txtPrice2: TextView
    private lateinit var txtDetails: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var imgProduct: ImageView
    private lateinit var btnBuy: Button
    private lateinit var imgFavorites: ImageView
    private lateinit var btnRating: Button
    private lateinit var ratingBar: RatingBar
    private lateinit var drawableStarBigOn: Drawable
    private lateinit var drawableStarBigOf: Drawable

    private var product: Product? = null

    private var action = ProductFragmentDirections

    @SuppressLint("UseCompatLoadingForDrawables")
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
        btnBuy = view.findViewById(R.id.btnBuyProduct)
        imgFavorites = view.findViewById(R.id.imgFavorites)
        ratingBar = view.findViewById(R.id.ratingBar)
        btnRating = view.findViewById(R.id.btnRating)
        drawableStarBigOn = resources.getDrawable(android.R.drawable.star_big_on)
        drawableStarBigOf = resources.getDrawable(android.R.drawable.star_big_off)
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
        setColorPlatform()
        listenerBtnBuy()
        btnRatingClick()
        setRatingBar()
        listenerRatingBar()
        listenerBtnFavorites()
    }

    private fun btnRatingClick(){
        btnRating.setOnClickListener {
            if (!getIsConnected()){
                SetToast.set("Usuario desconectado", context)
                disableRatingBar()
            }else if (isRated(product!!, getUser())){
                SetToast.set("Ya has valorado este producto", context)
                disableRatingBar()
            }else{
                SetToast.set("Producto valorado", context)
                setRate((ratingBar.rating * 2).toInt(), product!!, getUser()!!)

                disableRatingBar()
                ratingBar.rating = getRate(product!!)/2
            }
        }
    }

    private fun disableRatingBar(){
        ratingBar.isEnabled = false
        ratingBar.rating = product!!.rating/2
        btnRating.visibility = View.INVISIBLE
    }

    private fun enableRatingBar(){
        ratingBar.isEnabled = true
        btnRating.visibility = View.VISIBLE
    }

    private fun setRatingBar(){
        if (!getIsConnected()){
            disableRatingBar()
        }else if (isRated(product!!, getUser())){
            disableRatingBar()
        }else {
            enableRatingBar()
        }
    }

    private fun listenerRatingBar(){
        ratingBar.setOnClickListener{
            if (!getIsConnected()){
                SetToast.set("No esta conectado", context)
            }else if (isRated(product!!, getUser())){
                SetToast.set("Ya ha valorado este producto", context)
            }

        }
    }

    private fun listenerBtnFavorites(){
            setDrawableFavorite()
        imgFavorites.setOnClickListener {
            if (getIsConnected()){
                addOrRemoveFavorite()
            }else{
                super.onDestroy()
                NavHostFragment.findNavController(this).navigate(
                    ProductFragmentDirections.actionProductFragmentToLogInFragment(true)
                )
            }
        }
    }

    private fun addOrRemoveFavorite(){
        if(getUser()!!.isFavorite(product!!.id)){
            deleteFavorites(product!!.id, getUser()!!)
            setOffImgFavorites()
        }else{
            insertIntoFavorites(product!!.id, getUser()!!)
            setOnImgFavorites()
        }
    }

    private fun setDrawableFavorite(){
        if ( !getIsConnected()){
            setOffImgFavorites()

        }else if (!getUser()!!.isFavorite(product!!.id)){
            setOffImgFavorites()
        }
        else{
            setOnImgFavorites()
        }
    }

    private fun setOnImgFavorites(){
        imgFavorites.setImageDrawable(drawableStarBigOn)
    }

    private fun setOffImgFavorites(){
        imgFavorites.setImageDrawable(drawableStarBigOf)
    }



    private fun listenerBtnBuy() {
        btnBuy.setOnClickListener {
            if (getIsConnected()){
                LocalCart.add(product!!, 1)
            }else{
                super.onDestroy()
                NavHostFragment.findNavController(this).navigate(
                        ProductFragmentDirections.actionProductFragmentToLogInFragment(true)
                )
            }
        }
    }

    private fun setColorPlatform(){
        txtPlatform.setTextColor(product!!.platform.color.toColorInt())
    }
    @SuppressLint("SetTextI18n")
    private fun setPrice() {
        val price = String.format("%.2f",product!!.price)
        val arrayPrice = price.split(',')
        txtPrice1.text = arrayPrice[0] + ","
        txtPrice2.text = arrayPrice[1] + "€"
    }

    private fun listImgIsEmpty(): Boolean {
        return product!!.listImg.isNotEmpty()
    }

    private fun listImgSizeIsOne(): Boolean {
        return product!!.listImg.size == 1
    }

    private fun showImgView() {
        imgProduct.loadUrl(product!!.listImg.first().url)
    }

    private fun setImg() {
        if (listImgIsEmpty()) {
            if (listImgSizeIsOne()) {
                showImgView()
            }
        }
    }

}