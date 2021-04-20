package com.example.proyectofinal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import com.example.proyectofinal.R
import com.example.proyectofinal.data.User
import com.example.proyectofinal.dataBase.DataManager.DataManager.checkMailAndPass
import com.google.android.material.textfield.TextInputLayout


class LogInFragment : Fragment() {

    private lateinit var etEmail: TextInputLayout
    private lateinit var etPass: TextInputLayout

    private lateinit var btnLogIn: Button
    private var action = LogInFragmentDirections

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        if (User.getIsConnected()){
            changeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_log_in, container, false)
        etEmail = view.findViewById(R.id.tiUserLogIn)
        etPass = view.findViewById(R.id.tiPasswordLogIn)

        btnLogIn = view.findViewById(R.id.btnLogIn)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pressLogIn()
    }

    private fun pressLogIn(){
        btnLogIn.setOnClickListener {
            if (checkMailAndPass(etEmail, etPass) == null){
                textInputsError()
                etPass.error = "Email o contraseña incorrectas"
            }else{
                User.connectedUser(checkMailAndPass(etEmail, etPass))
                removeTextInputsError()
                changeFragment()
            }
        }
    }

    private fun textInputsError(){
        etEmail.error = " "
        etPass.error = "Email o contraseña incorrectas"
    }

    private fun removeTextInputsError(){
        etEmail.error = ""
        etPass.error = ""
    }

    private fun changeFragment(){
        NavHostFragment.findNavController(this).navigate(
            action.actionLogInFragmentToListUserFragment()
        )
    }


}