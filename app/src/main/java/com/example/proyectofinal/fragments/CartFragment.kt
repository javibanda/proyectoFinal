package com.example.proyectofinal.fragments

import android.os.Bundle
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
import com.example.proyectofinal.data.User.getIsConnected
import com.example.proyectofinal.data.cart.Cart


class CartFragment : Fragment() {

    private lateinit var txtNotLogInCart: TextView
    private lateinit var btnLogInCart: Button
    private lateinit var recyclerViewCart: RecyclerView
    private lateinit var btnBuyCart: Button

    private var action = CartFragmentDirections

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        txtNotLogInCart = view.findViewById(R.id.txtNotLogInCart)
        btnLogInCart = view.findViewById(R.id.btnLogInCart)
        recyclerViewCart = view.findViewById(R.id.recyclerViewCart)
        btnBuyCart = view.findViewById(R.id.btnBuyCart)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cartAdapter = ListCartRecyclerAdapter(Cart.getListCart(), this)

        recyclerViewCart.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = cartAdapter
        }
        checkIsUserConnected()
        clickLogIn()
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
    }

    private fun hideLogIn(){
        txtNotLogInCart.visibility = View.INVISIBLE
        btnLogInCart.visibility = View.INVISIBLE
        btnBuyCart.visibility = View.VISIBLE
    }

    private fun clickLogIn(){
        btnLogInCart.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(
                    action.actionCartFragmentToLogInFragment(true)
            )
        }

    }


}