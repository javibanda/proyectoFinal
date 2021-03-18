package com.example.proyectofinal.dataBase

import com.example.proyectofinal.data.City
import com.example.proyectofinal.data.Region
import com.example.proyectofinal.dataBase.SqlConection.SqlConection.getToken
import java.sql.Connection

class DataManager {

    object DataManager{
        private val conection: Connection = getToken()

        fun listCity(): List<City> {
            val listCity = ArrayList<City>()
            val resultSet = conection.createStatement().executeQuery("select * from city")
            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val name = resultSet.getString("name")
                listCity.add(City(id, name))
            }

            return listCity
        }

        fun listRegion(): List<Region> {
            val listRegion = ArrayList<Region>()
            val resulSet = conection.createStatement().executeQuery("Select id, name from region")
            while (resulSet.next()) {
                val id = resulSet.getInt("id")
                val name = resulSet.getString("name")

                val prepareSqlConection = conection.prepareStatement("""select c.id, c.name from region r join city c on (c.id_ca = r.id) where r.id = ?""")
                prepareSqlConection.setInt(1, id)
                val resultSet2 = prepareSqlConection.executeQuery()
                val listCity = ArrayList<City>()
                while (resultSet2.next()) {
                    var idC = resultSet2.getInt("c.id")
                    var nameC = resultSet2.getString("c.name")
                    listCity.add(City(idC, nameC))
                }
                listRegion.add(Region(id, name, listCity))
            }
            return listRegion
        }

        fun uniqueDni(dni:String):Boolean{
            val prepareSqlConection = conection.prepareStatement("""SELECT * FROM person WHERE dni LIKE ?""")
            prepareSqlConection.setString(1, dni)
            val resultSet = prepareSqlConection.executeQuery()
            return !resultSet.next()
        }

        fun uniqueEmail(email:String):Boolean{
            val prepareSqlConection = conection.prepareStatement("""SELECT * FROM person WHERE email LIKE ?""")
            prepareSqlConection.setString(1, email)
            val resultSet = prepareSqlConection.executeQuery()
            return !resultSet.next()
        }

        fun insertIntoPerson(dni:String, name:String, lastName:String, secondLastName:String?, email:String, pass:String, id_city:Int){
            with(conection){
                var prepareSqlConection = prepareStatement("""INSERT INTO person (dni, name, lastName, secondLastName, email, pass, id_city) VALUES (?, ?, ?, ?, ?, ?, ?)""")
                prepareSqlConection.setString(1, dni)
                prepareSqlConection.setString(2, name)
                prepareSqlConection.setString(3, lastName)
                prepareSqlConection.setString(4, secondLastName)
                prepareSqlConection.setString(5, email)
                prepareSqlConection.setString(6, pass)
                prepareSqlConection.setInt(7, id_city)
                prepareSqlConection.execute()
            }
        }
    }
}