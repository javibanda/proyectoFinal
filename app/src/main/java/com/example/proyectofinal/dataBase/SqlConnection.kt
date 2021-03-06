package com.example.proyectofinal.dataBase

import android.os.StrictMode
import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class SqlConnection {
    object SqlConnection{
        private const val DRIVER_STRING = "jdbc:mysql://javibanda.atthost24.pl:3306/19458_finalo"
        private lateinit var connection: Connection
        fun getToken():Connection{
            try {
                StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
                Class.forName("com.mysql.jdbc.Driver").newInstance()
                connection = DriverManager.getConnection(DRIVER_STRING, "19458", "kBjTlzk9j7JQRQNs")
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