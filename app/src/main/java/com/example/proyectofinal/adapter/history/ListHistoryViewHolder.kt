package com.example.proyectofinal.adapter.history

import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.base.BaseViewHolder
import com.example.proyectofinal.data.Order

class ListHistoryViewHolder(itemView: View): BaseViewHolder(itemView) {
    private val idOrderHistory = itemView.findViewById<TextView>(R.id.id_order_history)
    private val txtPriceHistory = itemView.findViewById<TextView>(R.id.txtPriceHistory)
    private val txtDateHistory = itemView.findViewById<TextView>(R.id.txtDateHistory)
    private val txtTimeHistory = itemView.findViewById<TextView>(R.id.txtTimeHistory)


    fun bindOrders(order: Order, fragment: Fragment){
        idOrderHistory.text = order.id.toString()
        txtPriceHistory.text = String.format("%.2f",order.priceProducts)
        txtDateHistory.text = order.date.getDateFormat()
        txtTimeHistory.text = order.date.getTimeFormat()
    }
}