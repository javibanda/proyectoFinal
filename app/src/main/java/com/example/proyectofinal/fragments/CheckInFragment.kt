package com.example.proyectofinal.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import com.example.proyectofinal.MainActivity
import com.example.proyectofinal.R
import com.example.proyectofinal.data.City
import com.example.proyectofinal.data.Person
import com.example.proyectofinal.data.User.connectedUser
import com.example.proyectofinal.data.User.getUser
import com.example.proyectofinal.dataBase.DataManager
import com.example.proyectofinal.dataBase.DataManager.DataManager.getPerson
import com.example.proyectofinal.dataBase.DataManager.DataManager.insertIntoPerson
import com.google.android.material.textfield.TextInputLayout

class CheckInFragment : Fragment() {

    private lateinit var tiDni: TextInputLayout
    private lateinit var tiName: TextInputLayout
    private lateinit var tiLastName: TextInputLayout
    private lateinit var tiSecondLastName: TextInputLayout
    private lateinit var tiEmail: TextInputLayout
    private lateinit var tiPass: TextInputLayout
    private lateinit var tiValidPass: TextInputLayout

    private lateinit var spinnerRegion: Spinner
    private lateinit var spinnerCity: Spinner

    private lateinit var btnCheckIn: Button

    private var idCity: Int = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_check_in, container, false)
        spinnerRegion = view.findViewById(R.id.spinerCityCheckIn)
        spinnerCity = view.findViewById(R.id.spinerRegionCheckIn)
        tiDni = view.findViewById(R.id.tiDniCheckIn)
        tiName =  view.findViewById(R.id.tiNameCheckIn)
        tiLastName = view.findViewById(R.id.tiLastNameCheckIn)
        tiSecondLastName = view.findViewById(R.id.tiSecondLastNameCheckIn)
        tiEmail = view.findViewById(R.id.tiEmailCheckIn)
        tiPass = view.findViewById(R.id.tiPasswordCheckIn)
        tiValidPass = view.findViewById(R.id.tiVerifyPasswordCheckIn)
        btnCheckIn = view.findViewById(R.id.btnCheckIn)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        spinnerRegion(spinnerRegion)

        btnCheckIn.setOnClickListener {
            if(validInput()){
                Log.d(":::Persona antes", getUser().toString())
                if(!DataManager.DataManager.uniqueDni(tiDni.editText!!.text.toString())){
                    setAlertDialog("YA EXISTE UNA CUENTA CON ESTE DNI")
                }else if(!DataManager.DataManager.uniqueEmail(tiEmail.editText!!.text.toString())){
                    setAlertDialog("YA EXISTE UNA CUENTA CON ESTE EMAIL")
                }else{

                    insertIntoPerson(
                        tiDni.editText!!,
                        tiName.editText!!,
                        tiLastName.editText!!,
                        secondLastNameIsNull(),
                        tiEmail.editText!!,
                        tiPass.editText!!,
                        idCity
                    )
                    connectUser(tiEmail.editText!!.text.toString().toLowerCase())
                    Log.d(":::Persona despues", getUser().toString())
                    requireActivity().onBackPressedDispatcher.onBackPressed()

                    Log.d(":::Datos", "se realiza el insertInto")
                }
            }
        }
    }

    private fun connectUser(email: String){
        connectedUser(getPerson(email))
    }

    private fun spinnerRegion(spinner: Spinner){
        val datos = DataManager.DataManager.listRegion()
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
                spinnerCity(spinnerCity, datos[position].city)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun spinnerCity(spinner: Spinner, lista: List<City>){

        val adaptador = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, lista)

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
                idCity = lista[position].id
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun validInput():Boolean{
        if(!dniIsValid()){
            return false
        }
        //validar la ñ
        if(!isValid(tiName.editText!!, "([a-zA-Z',.-]+( [a-zA-Z',.-]+)*){2,30}", getString(R.string.nameInput))){
            return false
        }
        if(!isValid(tiLastName.editText!!, "([a-zA-Z',.-]+( [a-zA-Z',.-]+)*){2,30}", getString(R.string.lastNameInput))){
            return false
        }
        if(!isValid(tiEmail.editText!!, "[a-z.0-9]+[@]{1}[a-z.]+[.]{1}[a-z]+", getString(R.string.emailInput))){
            return false
        }
        if(!isValid(tiPass.editText!!, "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+\$).{8,}\$", getString(R.string.passInput))){
            return false
        }
        if(!passEqual()){
            return false
        }
        return true
    }

    private fun isValid(editText: EditText, regex: String, input: String):Boolean{
        return if(Regex(pattern = regex).matches(input = editText.text.toString())){
            true
        }else{
            if(input.equals(getString(R.string.passInput))){
                setAlertDialog(getString(R.string.dialogMensajePass), "CONTRASEÑA INCORRECTA")
            }else{
                editText.error = getString(R.string.verify___) +" " + input
                editText.requestFocus()
            }
            false
        }
    }

    private fun setAlertDialog(mensaje:String, title:String){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(mensaje)

        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun setAlertDialog(title:String){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)

        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
    //validar el dni
    private fun dniIsValid():Boolean{
        return if(isValid(tiDni.editText!!, "[0-9]{8}[A-Za-z]{1}", "dni")){
            val dniLeters = "TRWAGMYFPDXBNJZSQVHLCKE"
            val dniInput = tiDni.editText!!.text.toString().toUpperCase()
            val letter: String = dniInput[8].toString()
            val number = dniInput.substring(0,8).toInt()
            val position = number % 23
            if (dniLeters[position].toString() == letter){
                true
            }else{
                tiDni.editText!!.error = "Invalid dni"
                false
            }
        }else{
            false
        }
    }

    private fun passEqual(): Boolean{
    return if(tiPass.editText!!.text.toString().equals(tiValidPass.editText!!.text.toString())){
            true
        }else{
            tiPass.error = "Las contraseñas deben coincidir"
            tiValidPass.error = "Las contraseñas deben coincidir"
            tiPass.editText!!.requestFocus()
            false
        }
    }

    private fun secondLastNameIsNull(): String? {
        return if(tiSecondLastName.editText!!.text.toString().isEmpty()){
            null
        }else{
            return tiSecondLastName.editText!!.text.toString().toUpperCase()
        }
    }


}