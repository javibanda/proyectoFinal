package com.example.proyectofinal.dataBase

import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class SqlConection {

    object SqlConection{

        private val driverString = "jdbc:mysql://192.168.64.2:3306/finalProyect"
        private lateinit var conn: Connection

        fun getToken():Connection{
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance()
                conn = DriverManager.getConnection(driverString, "root", "")
            }catch (ex: SQLException){
                Log.d(":::SQLExcepcion", ex.message.toString())
            }
            return conn
        }

        fun disconect(){
            this.conn.close()
        }

    }







}