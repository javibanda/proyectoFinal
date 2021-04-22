package com.example.proyectofinal.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.proyectofinal.R
import com.example.proyectofinal.data.User
import com.example.proyectofinal.data.User.getIsConnected
import com.example.proyectofinal.dataBase.DataManager.DataManager.checkMailAndPass
import com.google.android.material.textfield.TextInputLayout


class LogInFragment : Fragment() {

    private lateinit var etEmail: TextInputLayout
    private lateinit var etPass: TextInputLayout

    private lateinit var btnLogIn: Button
    private lateinit var btnCheck: Button

    private var action = LogInFragmentDirections
    private val args: LogInFragmentArgs by navArgs()

    override fun onStart() {
        super.onStart()
        if(getIsConnected()){
            changeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_log_in, container, false)
        etEmail = view.findViewById(R.id.tiUserLogIn)
        etPass = view.findViewById(R.id.tiPasswordLogIn)

        btnLogIn = view.findViewById(R.id.btnLogIn)
        btnCheck = view.findViewById(R.id.btnCheckInLogInFragment)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pressLogIn()
        pressCheckIn()
    }

    private fun pressLogIn(){
        btnLogIn.setOnClickListener {
            if (checkMailAndPass(etEmail.editText!!, etPass.editText!!) == null){
                textInputsError()
            }else{
                User.connectedUser(checkMailAndPass(etEmail.editText!!, etPass.editText!!))
                changeFragment()
            }
        }
    }

    private fun pressCheckIn(){
        btnCheck.setOnClickListener {
            Log.d(":::boton presionado", "sdfsdf")
            NavHostFragment.findNavController(this).navigate(
                    action.actionLogInFragmentToCheckInFragment()
            )
        }
    }

    private fun textInputsError(){
        etEmail.error = " "
        etPass.error = "Email o contraseña incorrectas"
    }

    private fun logInFragmentToListUserFragment(){
        NavHostFragment.findNavController(this).navigate(
            action.actionLogInFragmentToListUserFragment()
        )
    }

    private fun changeFragment(){
        if(args.back){
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }else{
            logInFragmentToListUserFragment()
        }
    }


}