package com.example.proyectofinal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.NavHostFragment
import com.example.proyectofinal.R
import com.example.proyectofinal.data.City
import com.example.proyectofinal.data.cart.LocalCart
import com.example.proyectofinal.dataBase.DataManager


class OrderFragment : Fragment() {

    private lateinit var spinnerRegionOrder: Spinner
    private lateinit var spinnerCityOrder: Spinner
    private lateinit var btnBuyOrder: Button
    private lateinit var etStreet: EditText
    private lateinit var txtPriceProductOrder: TextView
    private lateinit var txtPriceDeliveryOrder: TextView
    private lateinit var txtPriceTotalOrder: TextView

    private var idCity: Int = 0

    private val action = OrderFragmentDirections

    override fun onStart() {
        super.onStart()
        changeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order, container, false)
        spinnerCityOrder = view.findViewById(R.id.spinnerCityOrder)
        spinnerRegionOrder = view.findViewById(R.id.spinnerRegionOrder)
        btnBuyOrder = view.findViewById(R.id.btnBuyOrder)
        etStreet = view.findViewById(R.id.etStreet)
        txtPriceProductOrder = view.findViewById(R.id.txtPriceProductsOrder)
        txtPriceDeliveryOrder = view.findViewById(R.id.txtPriceDeliveryOrder)
        txtPriceTotalOrder = view.findViewById(R.id.txtPriceTotalOrder)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinnerRegion(spinnerRegionOrder)
        btnBuy()
        setTextView()
    }


    private fun spinnerRegion(spinner: Spinner){
        val datos = DataManager.listRegion()
        val adaptador = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, datos)
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adaptador
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                spinnerCity(spinnerCityOrder, datos[position].city)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun spinnerCity(spinner: Spinner, list: List<City>){

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, list)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                idCity = list[position].id
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun changeFragment(){
        if (LocalCart.isEmpty()){
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun btnBuy(){
        btnBuyOrder.setOnClickListener {
            if (isValidStreet()){
                DataManager.insertIntoOrder(etStreet.text.toString(), idCity)
                LocalCart.clear()
                NavHostFragment.findNavController(this).navigate(
                    action.actionOrderFragmentToOrderMade()
                )
            }
        }
    }

    private fun isValidStreet(): Boolean{
        return if (etStreet.text.isEmpty()){
            etStreet.error = "Este campo no puede estar en blanco"
            etStreet.requestFocus()
            false
        }else{
            true
        }
    }

    private fun setTextView(){

        txtPriceProductOrder.text = LocalCart.getPriceString()
        txtPriceDeliveryOrder.text = LocalCart.getDeliveryPriceString()
        txtPriceTotalOrder.text = LocalCart.getTotalPriceString()

    }
}