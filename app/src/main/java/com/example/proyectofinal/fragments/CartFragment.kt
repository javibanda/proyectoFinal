package com.example.proyectofinal.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.cart.ListCartRecyclerAdapter
import com.example.proyectofinal.adapter.base.OnItemClickListener
import com.example.proyectofinal.data.User.getIsConnected
import com.example.proyectofinal.data.cart.LocalCart
import com.example.proyectofinal.data.cart.LocalCart.getPrice
import com.example.proyectofinal.dataBase.DataManager
import com.example.proyectofinal.util.SetToast


class CartFragment : Fragment(), OnItemClickListener {

    private lateinit var txtNotLogInCart: TextView
    private lateinit var btnLogInCart: Button
    private lateinit var recyclerViewCart: RecyclerView
    private lateinit var btnBuyCart: Button
    private lateinit var txtTotalCart: TextView

    private var action = CartFragmentDirections

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        txtNotLogInCart = view.findViewById(R.id.txtNotLogInCart)
        btnLogInCart = view.findViewById(R.id.btnLogInCart)
        recyclerViewCart = view.findViewById(R.id.recyclerViewCart)
        btnBuyCart = view.findViewById(R.id.btnBuyCart)
        txtTotalCart = view.findViewById(R.id.txtTotalCart)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cartAdapter = ListCartRecyclerAdapter(LocalCart.getListDataCart(), this, this)

        recyclerViewCart.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = cartAdapter
        }
        updateTotalPrice()
        checkIsUserConnected()
        clickLogIn()
        clickBuy()
    }

    private fun checkIsUserConnected(){
        if(getIsConnected()){
            hideLogIn()
        }else{
            showLogIn()
        }
    }

    private fun showLogIn(){
        txtNotLogInCart.visibility = View.VISIBLE
        btnLogInCart.visibility = View.VISIBLE
        btnBuyCart.visibility = View.INVISIBLE
        txtTotalCart.visibility = View.INVISIBLE
    }

    private fun hideLogIn(){
        txtNotLogInCart.visibility = View.INVISIBLE
        btnLogInCart.visibility = View.INVISIBLE
        btnBuyCart.visibility = View.VISIBLE
        txtTotalCart.visibility = View.VISIBLE
    }

    private fun clickLogIn(){
        btnLogInCart.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(
                    action.actionCartFragmentToLogInFragment(true)
            )
        }
    }

    private fun clickBuy(){
        btnBuyCart.setOnClickListener {
            if (LocalCart.isEmpty()){
                SetToast.set("No hay ningun producto en el carrito", context)
            }
        }
    }

    override fun onItemClick(position: Int) {
        Log.d(":::Test: ", position.toString())
        LocalCart.remove(position)
        recyclerViewCart.adapter?.notifyDataSetChanged()
        updateTotalPrice()
    }

    override fun onItemClick() {
        updateTotalPrice()
    }

    @SuppressLint("SetTextI18n")
    private fun updateTotalPrice(){
        val totalPrice = String.format("%.2f",getPrice())
        txtTotalCart.text = "TOTAL:       $totalPrice€"
    }
}