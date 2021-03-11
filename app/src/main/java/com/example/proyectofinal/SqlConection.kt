package com.example.proyectofinal

import android.util.Log
import com.example.proyectofinal.data.City
import com.example.proyectofinal.data.Region
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import kotlin.collections.ArrayList

class SqlConection {

    private val CADENA = "jdbc:mysql://192.168.64.2:3306/finalProyect"
    private lateinit var conn: Connection

    fun conecting():Boolean{
        var cond = true
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance()
            conn = DriverManager.getConnection(CADENA, "root", "")
        }catch (ex: SQLException){
            cond = false
            Log.d(":::SQLExcepcion", ex.message.toString())
        }

        return cond
    }

    fun listCity(): List<City>{
        var listCity = ArrayList<City>()

        var st = conn.createStatement()


        var rs = st.executeQuery("select * from city")

        while (rs.next()){
            var id =rs.getInt("id")
            var name = rs.getString("name")



            listCity.add(City(id, name))

        }

        return listCity

    }

    fun listRegion(): List<Region>{
        var listRegion = ArrayList<Region>()

        var st = conn.createStatement()
        var st2 = conn.createStatement()
        var rs = st.executeQuery("Select id, name from region")

        while (rs.next()){
            var id = rs.getInt("id")
            var name = rs.getString("name")

            var rss = st2.executeQuery("select c.id, c.name from region r join city c on (c.id_ca = r.id) where r.id = $id")
            var listCity = ArrayList<City>()
            while (rss.next()){
                Log.d(":::Paso por aqui", name)
                var idC = rss.getInt("c.id")
                var nameC = rss.getString("c.name")
                listCity.add(City(idC, nameC))
            }
            listRegion.add(Region(id, name, listCity))
        }
        return  listRegion
    }

    fun disconect(){

        this.conn.close()

    }


}