package com.example.proyectofinal.adapter.user

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.base.BaseViewHolder
import com.example.proyectofinal.data.User
import com.example.proyectofinal.fragments.ListUserFragmentDirections

class ListUserViewHolder(itemView: View) : BaseViewHolder(itemView) {

    private val txtOption = itemView.findViewById<TextView>(R.id.txtOptionUser)
    private val userView = itemView.findViewById<ConstraintLayout>(R.id.userView)
    private var stringOption = ""
    private var action = ListUserFragmentDirections


    fun bindUser(option: String, fragment: Fragment) {
        stringOption = option
        txtOption.text = option
        directionLogOut(fragment)
    }


    fun directionLogOut(fragment: Fragment){
        userView.setOnClickListener {

            if (txtOption.text.toString() == "Cerrar Sesion"){
                NavHostFragment.findNavController(fragment).navigate(
                    action.actionListUserFragmentToLogInFragment()
                )
                User.disconnectedUser()
            }
        }
    }
}