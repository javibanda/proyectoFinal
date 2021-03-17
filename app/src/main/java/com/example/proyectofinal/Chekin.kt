package com.example.proyectofinal;

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.proyectofinal.data.City
import com.example.proyectofinal.dataBase.DataManager
import com.example.proyectofinal.dataBase.DataManager.DataManager.insertIntoPerson
import com.example.proyectofinal.dataBase.DataManager.DataManager.listRegion
import com.example.proyectofinal.dataBase.DataManager.DataManager.uniqueDni
import com.example.proyectofinal.dataBase.DataManager.DataManager.uniqueEmail

class Chekin: AppCompatActivity(){

    private lateinit var spinerRegion: Spinner
    private lateinit var spinerCity: Spinner
    private lateinit var test: Button
    private lateinit var dni: EditText
    private lateinit var name: EditText
    private lateinit var lastName: EditText
    private lateinit var secondLastName: EditText
    private lateinit var email: EditText
    private lateinit var pass: EditText
    private lateinit var validPass: EditText

    private var idCity: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chekin);
        spinerRegion = findViewById(R.id.spinerRegion)
        spinerCity = findViewById(R.id.spinerCity)
        test = findViewById(R.id.button)
        dni = findViewById(R.id.etDni)
        name = findViewById(R.id.etName)
        lastName = findViewById(R.id.etFirstName)
        secondLastName = findViewById(R.id.etSecondLastName)
        email = findViewById(R.id.etEmail)
        pass = findViewById(R.id.etPass)
        validPass = findViewById(R.id.etValidPass)

        spinnerRegion(spinerRegion)
        dni.requestFocus()

        test.setOnClickListener {
            if(validInput()){
                if(!uniqueDni(dni.text.toString())){
                    Log.d(":::Datos", "el dni existe, no se realiza el inserInto")
                    setAlertDialog("YA EXISTE UNA CUENTA CON ESTE DNI")
                }else if(!uniqueEmail(email.text.toString())){
                    setAlertDialog("YA EXISTE UNA CUENTA CON ESTE EMAIL")
                }else{
                    insertIntoPerson(dni.text.toString().toUpperCase(), name.text.toString().toUpperCase(), lastName.text.toString().toUpperCase() , secondLastNameIsNull(), email.text.toString().toLowerCase() ,pass.text.toString(), idCity)
                    Log.d(":::Datos", "se realiza el insertInto")
                    startActivity(Intent(this@Chekin, MainActivity::class.java))
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun spinnerRegion(spinner: Spinner){
        val datos = listRegion()
        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, datos)
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
                spinnerCity(spinerCity, datos[position].getCity())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun spinnerCity(spinner: Spinner, lista: List<City>){

        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, lista)

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
                idCity = lista[position].getId()
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
        if(!isValid(name, "([a-zA-Z',.-]+( [a-zA-Z',.-]+)*){2,30}", getString(R.string.nameInput))){
            return false
        }
        if(!isValid(lastName, "([a-zA-Z',.-]+( [a-zA-Z',.-]+)*){2,30}", getString(R.string.lastNameInput))){
            return false
        }
        if(!isValid(email, "[a-z.0-9]+[@]{1}[a-z.]+[.]{1}[a-z]+", getString(R.string.emailInput))){
            return false
        }
        if(!isValid(pass, "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+\$).{8,}\$", getString(R.string.passInput))){
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
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(mensaje)

        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun setAlertDialog(title:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)

        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun dniIsValid():Boolean{
        return if(isValid(dni, "[0-9]{8}[A-Za-z]{1}", "dni")){
            val dniLeters = "TRWAGMYFPDXBNJZSQVHLCKE"
            val dniInput = dni.text.toString().toUpperCase()
            val letter: String = dniInput[8].toString()
            val number = dniInput.substring(0,8).toInt()
            val position = number % 23
            if (dniLeters[position].toString() == letter){
                true
            }else{
                dni.error = "Invalid dni"
                false
            }
        }else{
            false
        }
    }

    private fun passEqual(): Boolean{
        return if(pass.text.toString().equals(validPass.text.toString())){
            true
        }else{
            pass.error = "Las contraseñas deben coincidir"
            validPass.error = "Las contraseñas deben coincidir"
            pass.requestFocus()
            false
        }
    }

    private fun secondLastNameIsNull(): String? {
        return if(secondLastName.text.toString().isEmpty()){
            null
        }else{
            val str = secondLastName.text.toString().toUpperCase()
            str
        }
    }
}




