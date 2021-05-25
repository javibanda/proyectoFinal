package com.example.proyectofinal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.proyectofinal.R
import com.example.proyectofinal.data.User


class UserFragment : Fragment() {

    private lateinit var txtDniUser: TextView
    private lateinit var txtNameUser: TextView
    private lateinit var txtEmailUser: TextView
    private lateinit var txtCityUser: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        txtDniUser = view.findViewById(R.id.txtDniUser)
        txtNameUser = view.findViewById(R.id.txtNameUser)
        txtEmailUser = view.findViewById(R.id.txtEmailUser)
        txtCityUser = view.findViewById(R.id.txtCityUser)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextView()
    }

    private fun setTextView(){
        txtCityUser.text = User.getCity()
        txtDniUser.text = User.getDni()
        txtNameUser.text = User.getName()
        txtEmailUser.text = User.getEmail()
    }
}