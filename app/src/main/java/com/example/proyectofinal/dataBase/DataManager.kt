package com.example.proyectofinal.dataBase

import android.widget.EditText
import com.example.proyectofinal.data.*
import com.example.proyectofinal.dataBase.SqlConection.SqlConection.getToken
import com.example.proyectofinal.extensions.toLowerCaseDefaultLocale
import com.example.proyectofinal.extensions.toUpperCaseDefaultLocale
import java.sql.Connection
import kotlin.collections.ArrayList

class DataManager {

    companion object {
        private const val BASE_PRODUCT_SELECT = "select pr.id, pr.name, pr.description, pr.price, pl.id, pl.name, pl.url, c.id, c.name, c.url, AVG(r.rating) media" +
                " from product pr" +
                " join platform pl on (pl.id = id_platform)" +
                " join category c on (c.id = id_category)" +
                " left join rating r on (pr.id = id_product )"

        private const val FINAL_PRODUCT_SELECT = " group by pr.id"
    }

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

        fun getListProduct(idCategory: Int, idPlatform: Int): List<Product>{
            val query: String
            val array: IntArray
            when {
                idCategory == 0 -> {
                    query = BASE_PRODUCT_SELECT + FINAL_PRODUCT_SELECT
                    array = intArrayOf()
                }
                idPlatform == 0 -> {
                    query = BASE_PRODUCT_SELECT +
                            " where c.id = ?" +
                            FINAL_PRODUCT_SELECT
                    array = intArrayOf(idCategory)
                }
                else -> {
                    query = BASE_PRODUCT_SELECT +
                            " where c.id = ?" +
                            " and pl.id = ?" +
                            FINAL_PRODUCT_SELECT
                    array = intArrayOf(idCategory, idPlatform)
                }
            }
            return queryProduct(query, array)
        }

        private fun queryProduct(sql: String, array: IntArray):List<Product>{
            val listProduct = ArrayList<Product>()
            with(getResultSetProduct(array, sql)){
                while (next()) {
                    val id = getInt("pr.id")
                    val name = getString("pr.name")
                    val description = getString("pr.description")
                    val price = getFloat("pr.price")
                    val idPlatform = getInt("pl.id")
                    val namePlatform = getString("pl.name")
                    val urlPlatform = getString("pl.url")
                    val idCategory = getInt("c.id")
                    val nameCategory = getString("c.name")
                    val urlCategory = getString("c.url")
                    val rating = getFloat("media")

                    val prepareSqlConnection =
                        connection.prepareStatement("""SELECT * FROM img_product WHERE id_product = ?""")
                    prepareSqlConnection.setInt(1, id)
                    with(prepareSqlConnection.executeQuery()){
                        val listImg = ArrayList<ProductImg>()
                        while (next()) {
                            val idImg = getInt("id")
                            val urlImg = getString("url")
                            listImg.add(ProductImg(idImg, urlImg))
                        }

                    val product = Product(
                        id,
                        name,
                        description,
                        price,
                        Category(idCategory, nameCategory, urlCategory),
                        Platform(idPlatform, namePlatform, urlPlatform),
                        rating,
                        listImg
                    )


                    //Log.d(":::Product " + product.id, product.toString())
                    listProduct.add(product)
                    }
                }
            }

            return listProduct
        }

        private fun getResultSetProduct(array: IntArray, sql: String): ResultSet{
            var cont = 0
            val prepareSqlConnection = connection.prepareStatement(sql)
            while ( cont < array.size){
                prepareSqlConnection.setInt((cont + 1), array[cont])
                cont++
            }
            return  prepareSqlConnection.executeQuery()
        }

        fun listPlatform():List<Platform>{
            val listPlatform = ArrayList<Platform>()

            val resultSet = connection.createStatement().executeQuery(
                    "SELECT id, name, url FROM platform"
            )
            while (resultSet.next()){
                val id = resultSet.getInt("id")
                val name = resultSet.getString("name")
                val url = resultSet.getString("url")
                listPlatform.add(Platform(id, name, url))
            }
            return listPlatform
        }
    }
}