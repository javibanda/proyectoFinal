package com.example.proyectofinal.dataBase

import android.widget.EditText
import com.example.proyectofinal.data.*
import com.example.proyectofinal.dataBase.SqlConection.SqlConection.getToken
import com.example.proyectofinal.extensions.toLowerCaseDefaultLocale
import com.example.proyectofinal.extensions.toUpperCaseDefaultLocale
import java.sql.Connection
import java.sql.ResultSet
import kotlin.collections.ArrayList

class DataManager {

    companion object {
        private const val BASE_PRODUCT_SELECT = "select pr.id, pr.name, pr.description, pr.price, pl.id, pl.name, pl.url, pl.color, pl.color_text, c.id, c.name, c.url, AVG(r.rating) media, COUNT(r.id) count" +
                " from product pr" +
                " join platform pl on (pl.id = id_platform)" +
                " join category c on (c.id = id_category)" +
                " left join rating r on (pr.id = id_product )"

        private const val FINAL_PRODUCT_SELECT = " group by pr.id"
    }

    object DataManager{
        private val connection: Connection = getToken()
        private val listProduct = queryProduct(BASE_PRODUCT_SELECT + FINAL_PRODUCT_SELECT, intArrayOf())

        fun listCity(): List<City> {
            val listCity = ArrayList<City>()
            val resultSet = connection.createStatement().executeQuery(
                    "select * from city"
            )
            with(resultSet){
                while (next()) {
                    val id = getInt("id")
                    val name = getString("name")
                    listCity.add(City(id, name))
                }
            }


            return listCity
        }

        fun listRegion(): List<Region> {
            val listRegion = ArrayList<Region>()
            val resultSet = connection.createStatement().executeQuery(
                    "Select id, name from region"
            )
            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val name = resultSet.getString("name")

                val prepareSqlConnection = connection.prepareStatement(
                        """select c.id, c.name from region r join city c on (c.id_ca = r.id) where r.id = ?"""
                )
                prepareSqlConnection.setInt(1, id)
                val listCity = ArrayList<City>()
                with(prepareSqlConnection.executeQuery()) {

                    while (next()) {
                        val idC = getInt("c.id")
                        val nameC = getString("c.name")
                        listCity.add(City(idC, nameC))
                    }
                }
                listRegion.add(Region(id, name, listCity))
            }
            return listRegion
        }

        fun uniqueDni(dni:String):Boolean{
            val prepareSqlConnection = connection.prepareStatement(
                    """SELECT * FROM person WHERE dni LIKE ?"""
            )
            prepareSqlConnection.setString(1, dni)
            val resultSet = prepareSqlConnection.executeQuery()
            return !resultSet.next()
        }

        fun uniqueEmail(email:String):Boolean{
            val prepareSqlConnection = connection.prepareStatement(
                    """SELECT * FROM person WHERE email LIKE ?"""
            )
            prepareSqlConnection.setString(1, email)
            val resultSet = prepareSqlConnection.executeQuery()
            return !resultSet.next()
        }

        fun insertIntoPerson(dni:EditText,
                             name:EditText,
                             lastName:EditText,
                             secondLastName:String?,
                             email:EditText,
                             pass:EditText,
                             id_city:Int
        ){
            with(connection){
                val prepareSqlConnection = prepareStatement(
                        """INSERT INTO person (dni, name, lastName, secondLastName, email, pass, id_city) VALUES (?, ?, ?, ?, ?, ?, ?)"""
                )
                with(prepareSqlConnection) {
                    setString(1, dni.toUpperCaseDefaultLocale())
                    setString(2, name.toUpperCaseDefaultLocale())
                    setString(3, lastName.toUpperCaseDefaultLocale())
                    setString(4, secondLastName)
                    setString(5, email.toLowerCaseDefaultLocale())
                    setString(6, pass.text.toString())
                    setInt(7, id_city)
                    execute()
                }

            }
        }

        fun listCategory(): List<Category>{
            val listKindProduct = ArrayList<Category>()
            val resultSet = connection.createStatement().executeQuery("SELECT * FROM category")
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
            val arrayParameters
                    : IntArray
            when {
                idCategory == 0 -> {
                    return listProduct
                }
                idPlatform == 0 -> {
                    query = BASE_PRODUCT_SELECT +
                            " where c.id = ?" +
                            FINAL_PRODUCT_SELECT
                    arrayParameters = intArrayOf(idCategory)
                }
                else -> {
                    query = BASE_PRODUCT_SELECT +
                            " where c.id = ?" +
                            " and pl.id = ?" +
                            FINAL_PRODUCT_SELECT
                    arrayParameters = intArrayOf(idCategory, idPlatform)
                }
            }
            return queryProduct(query, arrayParameters)

        }

        fun getProduct(idProduct: Int): Product{
            val query: String = BASE_PRODUCT_SELECT +
                    " where pr.id = ?" +
                    FINAL_PRODUCT_SELECT
            val arrayParameters: IntArray = intArrayOf(idProduct)
            return queryProduct(query, arrayParameters).first()
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
                    val colorPlatform = getString("pl.color")
                    val colorTextPlatform = getString("pl.color_text")
                    val idCategory = getInt("c.id")
                    val nameCategory = getString("c.name")
                    val urlCategory = getString("c.url")
                    val rating = getFloat("media")
                    val numRating = getInt("count")

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
                        Platform(idPlatform, namePlatform, urlPlatform, colorPlatform, colorTextPlatform),
                        rating,
                        numRating,
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
                    "SELECT id, name, url, color, color_text FROM platform"
            )
            while (resultSet.next()){
                val id = resultSet.getInt("id")
                val name = resultSet.getString("name")
                val url = resultSet.getString("url")
                val color = resultSet.getString("color")
                val colorText = resultSet.getString("color_text")
                listPlatform.add(Platform(id, name, url, color, colorText))
            }
            return listPlatform
        }
    }
}