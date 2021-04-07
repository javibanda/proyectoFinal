package com.example.proyectofinal.dataBase

import android.widget.EditText
import com.example.proyectofinal.data.*
import com.example.proyectofinal.dataBase.SqlConection.SqlConection.getToken
import com.example.proyectofinal.extensions.changeEditTextToStringToUpperCase
import com.example.proyectofinal.extensions.toLowerCaseDefaultLocale
import com.example.proyectofinal.extensions.toUpperCaseDefaultLocale
import java.sql.Connection
import kotlin.collections.ArrayList

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

        fun insertIntoPerson(dni:EditText, name:EditText, lastName:EditText, secondLastName:String?, email:EditText, pass:EditText, id_city:Int){
            with(conection){
                var prepareSqlConection = prepareStatement("""INSERT INTO person (dni, name, lastName, secondLastName, email, pass, id_city) VALUES (?, ?, ?, ?, ?, ?, ?)""")
                prepareSqlConection.setString(1, dni.toUpperCaseDefaultLocale())
                prepareSqlConection.setString(2, name.toUpperCaseDefaultLocale())
                prepareSqlConection.setString(3, lastName.toUpperCaseDefaultLocale())
                prepareSqlConection.setString(4, secondLastName)
                prepareSqlConection.setString(5, email.toLowerCaseDefaultLocale())
                prepareSqlConection.setString(6, pass.text.toString())
                prepareSqlConection.setInt(7, id_city)
                prepareSqlConection.execute()
            }
        }

        fun listCategory(): List<Category>{
            val listKindProduct = ArrayList<Category>()
            val resultSet = conection.createStatement().executeQuery("SELECT * FROM category")
            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val name = resultSet.getString("name")
                val url = resultSet.getString("url")
                listKindProduct.add(Category(id, name, url))
            }
            return listKindProduct
        }

        fun listProduct():List<Product>{
            val listProduct = ArrayList<Product>()

            val resultSet = conection.createStatement().executeQuery(
                    "select pr.id, pr.name, pr.description, pr.price, pl.id, pl.name, c.id, c.name, c.url, AVG(r.rating) media" +
                    " from product pr" +
                    " join platform pl on (pl.id = id_platform)" +
                    " join category c on (c.id = id_category)" +
                    " join rating r on (pr.id = id_product )" +
                    " group by pr.id"
                   )
            while (resultSet.next()){
                val id = resultSet.getInt("pr.id")
                val name = resultSet.getString("pr.name")
                val description = resultSet.getString("pr.description")
                val price = resultSet.getFloat("pr.price")
                val idPlatform = resultSet.getInt("pl.id")
                val namePlatform = resultSet.getString("pl.name")
                val idCategory = resultSet.getInt("c.id")
                val nameCategory = resultSet.getString("c.name")
                val urlCategory = resultSet.getString("c.url")
                val rating = resultSet.getFloat("media")

                listProduct.add(Product(id, name, description, price, Category(idCategory, nameCategory, urlCategory), Platform(idPlatform, namePlatform), rating))
            }

            return listProduct
        }

        fun listProduct(idCategory: Int):List<Product>{
            val listProduct = ArrayList<Product>()
            if(idCategory == 1){
                return listProduct()
            }else{
                for (i in listProduct()){
                    if (i.category.id == idCategory){
                        listProduct.add(i)
                    }
                }
            }
           return listProduct
        }
    }
}