package com.example.proyectofinal.dataBase

import android.os.StrictMode
import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class SqlConection {

    object SqlConection{

        private val driverString = "jdbc:mysql://192.168.64.2:3306/finalProyect"
        private lateinit var connection: Connection
        fun getToken():Connection{
            try {
                StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
                Class.forName("com.mysql.jdbc.Driver").newInstance()
                connection = DriverManager.getConnection(driverString, "root", "kurwa")
            }catch (ex: SQLException){
                Log.d(":::SQLExcepcion", ex.message.toString())
            }
            return connection
        }

        fun disconect(){
            this.connection.close()
        }

    }







}