package com.example.proyectofinal.dataBase

import android.widget.EditText
import com.example.proyectofinal.data.*
import com.example.proyectofinal.data.cart.Cart
import com.example.proyectofinal.data.cart.LocalCart
import com.example.proyectofinal.dataBase.SqlConnection.SqlConnection.getToken
import com.example.proyectofinal.extensions.toLowerCaseDefaultLocale
import com.example.proyectofinal.extensions.toUpperCaseDefaultLocale
import com.example.proyectofinal.util.Date
import java.sql.Connection
import java.sql.ResultSet
import kotlin.collections.ArrayList
import kotlin.Boolean as Boolean1

object DataManager {

    private const val BASE_PRODUCT_SELECT =
        "select pr.id, pr.name, pr.description, pr.price, pl.id, pl.name, pl.url, pl.color, pl.color_text, c.id, c.name, c.url, AVG(r.rating) media, COUNT(r.id) count" +
                " from product pr" +
                " join platform pl on (pl.id = id_platform)" +
                " join category c on (c.id = id_category)" +
                " left join rating r on (pr.id = id_product )"

    private const val FINAL_PRODUCT_SELECT = " group by pr.id"
    private const val BASE_CART_JOIN =
        " left join product_order por on (pr.id = por.id_product)" +
            " left join orders o on (por.id_order = o.id)" +
            " where o.id = ?" +
            " group by pr.id, por.id, o.id"

    private const val DELETE_FAVORITES = """
        DELETE FROM favourite 
        WHERE id_product = ?
        AND id_person = ?
        """

    private const val INSERT_INTO_FAVORITES = """
         INSERT INTO favourite(id_product, id_person)
         VALUES (?, ?)
    """


    private val connection: Connection = getToken()
    private val listProduct = queryProduct(BASE_PRODUCT_SELECT + FINAL_PRODUCT_SELECT, intArrayOf())

    fun listCity(): List<City> {
        val listCity = ArrayList<City>()
        val resultSet = connection.createStatement().executeQuery(
            "select c.id, c.name, r.name from city c join region r on r.id = c.id_ca"
        )
        with(resultSet) {
            while (next()) {
                val id = getInt("c.id")
                val name = getString("c.name")
                val nameRegion = getString("r.name")
                listCity.add(City(id, name, nameRegion))
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
                """SELECT c.id, c.name, r.name
                | FROM region r
                | JOIN city c ON (c.id_ca = r.id) 
                | WHERE r.id = ?""".trimMargin()
            )
            prepareSqlConnection.setInt(1, id)
            val listCity = ArrayList<City>()
            with(prepareSqlConnection.executeQuery()) {

                while (next()) {
                    val idC = getInt("c.id")
                    val nameC = getString("c.name")
                    val nameRegion = getString("r.name")
                    listCity.add(City(idC, nameC, nameRegion))
                }
            }
            listRegion.add(Region(id, name, listCity))
        }
        return listRegion
    }

    fun uniqueDni(dni: String): Boolean1 {
        val prepareSqlConnection = connection.prepareStatement(
            """SELECT * FROM person WHERE dni LIKE ?"""
        )
        prepareSqlConnection.setString(1, dni)
        val resultSet = prepareSqlConnection.executeQuery()
        return !resultSet.next()
    }

    fun uniqueEmail(email: String): Boolean1 {
        val prepareSqlConnection = connection.prepareStatement(
            """SELECT * FROM person WHERE email LIKE ?"""
        )
        prepareSqlConnection.setString(1, email)
        val resultSet = prepareSqlConnection.executeQuery()
        return !resultSet.next()
    }

    fun getPerson(email: EditText, pass: EditText): Person? {
        var person: Person? = null
        val prepareSqlConnection = connection.prepareStatement(
            """SELECT p.id, p.dni, p.name, p.lastName, p.secondLastName, p.email, p.pass, c.id, c.name, r.name 
                        |FROM person p  
                        |JOIN city c ON (p.id_city = c.id) 
                        |JOIN region r ON (id_ca = r.id)
                        |WHERE p.email LIKE ? 
                        |AND p.pass LIKE ?
                    """.trimMargin()
        )
        with(prepareSqlConnection) {
            setString(1, email.text.toString())
            setString(2, pass.text.toString())
        }
        with(prepareSqlConnection.executeQuery()) {
            while (next()) {
                val idP = getInt("p.id")
                val dniP = getString("p.dni")
                val nameP = getString("p.name")
                val lastNameP = getString("p.lastName")
                val secondLastNameP: String? = getString("p.secondLastName")
                val emailP = getString("p.email")
                val passP = getString("p.pass")
                val idC = getInt("c.id")
                val nameC = getString("c.name")
                val nameRegion = getString("r.name")

                person = Person(
                    idP,
                    dniP,
                    nameP,
                    lastNameP,
                    secondLastNameP,
                    emailP,
                    passP,
                    City(idC, nameC, nameRegion),
                    getListFavorites(idP)
                )
            }
        }
        return person
    }

    private fun getListFavorites(idPerson: Int): ArrayList<Int>{
        val listFavorites = ArrayList<Int>()
        val prepareSqlConnection = connection.prepareStatement(
            """
                SELECT p.id
                from favourite f
                join product p on (f.id_product = p.id)
                where f.id_person = ?
            """.trimIndent()

        )
        with(prepareSqlConnection){
            setInt(1, idPerson)
        }
        with(prepareSqlConnection.executeQuery()){
            while (next()){
                listFavorites.add(getInt("p.id"))
            }

        }
        return listFavorites
    }

    fun insertIntoFavorites(idProduct: Int, person: Person){
        baseFavorites(idProduct, person, INSERT_INTO_FAVORITES)
        person.addFavorite(idProduct)
    }

    fun deleteFavorites(idProduct: Int, person: Person){
        baseFavorites(idProduct, person, DELETE_FAVORITES)
        person.removeFavorite(idProduct)
    }


    private fun baseFavorites(
        idProduct: Int,
        person: Person,
        sqlQuery: String
    ){
        with(connection){
            val prepareSqlConnection = prepareStatement(sqlQuery)
            with(prepareSqlConnection){
                setInt(1, idProduct)
                setInt(2, person.id)
                execute()
            }
        }
    }

    fun insertIntoPerson(
        dni: EditText,
        name: EditText,
        lastName: EditText,
        secondLastName: String?,
        email: EditText,
        pass: EditText,
        id_city: Int
    ) {
        with(connection) {
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


    fun listCategory(): List<Category> {
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

    fun getListProduct(idCategory: Int, idPlatform: Int): List<Product> {
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



    fun getProduct(idProduct: Int): Product {
        val query: String = BASE_PRODUCT_SELECT +
                " where pr.id = ?" +
                FINAL_PRODUCT_SELECT
        val arrayParameters: IntArray = intArrayOf(idProduct)
        return queryProduct(query, arrayParameters).first()
    }

    private fun queryProduct(sql: String, array: IntArray): List<Product> {
        val listProduct = ArrayList<Product>()
        with(getResultSetProduct(array, sql)) {
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
                with(prepareSqlConnection.executeQuery()) {
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
                        Platform(
                            idPlatform,
                            namePlatform,
                            urlPlatform,
                            colorPlatform,
                            colorTextPlatform
                        ),
                        rating,
                        numRating,
                        listImg
                    )
                    listProduct.add(product)
                }
            }
        }

        return listProduct
    }

    private fun getResultSetProduct(array: IntArray, sql: String): ResultSet {
        var cont = 0
        val prepareSqlConnection = connection.prepareStatement(sql)
        while (cont < array.size) {
            prepareSqlConnection.setInt((cont + 1), array[cont])
            cont++
        }
        return prepareSqlConnection.executeQuery()
    }

    fun listPlatform(): List<Platform> {
        val listPlatform = ArrayList<Platform>()

        val resultSet = connection.createStatement().executeQuery(
            "SELECT id, name, url, color, color_text FROM platform"
        )
        while (resultSet.next()) {
            val id = resultSet.getInt("id")
            val name = resultSet.getString("name")
            val url = resultSet.getString("url")
            val color = resultSet.getString("color")
            val colorText = resultSet.getString("color_text")
            listPlatform.add(Platform(id, name, url, color, colorText))
        }
        return listPlatform
    }


    fun isRated(product: Product, person: Person?): kotlin.Boolean {
        if (person == null) {
            return false
        }
        with(connection) {
            val prepareSqlConnection = prepareStatement(
                """SELECT * 
                           |FROM rating
                           |WHERE id_product = ?
                           |AND id_person = ?
                       """.trimMargin()
            )
            with(prepareSqlConnection) {
                setInt(1, product.id)
                setInt(2, person.id)
                with(executeQuery()) {
                    return next()
                }
            }
        }
    }

    fun setRate(rating: Int, product: Product, person: Person) {
        var rat: Float = 0.0F

        with(connection) {
            val prepareSqlConnection = prepareStatement(
                """INSERT INTO rating (rating, id_product, id_person)
                            |VALUES (?, ?, ?)
                       """.trimMargin()
            )
            with(prepareSqlConnection) {
                setInt(1, rating)
                setInt(2, product.id)
                setInt(3, person.id)
                execute()
            }
        }
    }

    fun getRate(product: Product): Float {
        var rating: Float = 0.0F
        with(connection) {
            val prepareSqlConnection = prepareStatement(
                """SELECT p.name, AVG(r.rating) as rat
                            |FROM product p
                            |LEFT JOIN rating r ON (p.id = r.id_product)
                            |WHERE p.id = ?
                            |GROUP BY p.id
                        """.trimMargin()
            )
            with(prepareSqlConnection) {
                setInt(1, product.id)
                with(executeQuery()) {
                    while (next()) {
                        rating = getFloat("rat")
                    }
                }
            }
        }

        return rating
    }


    fun insertIntoOrder(street: String, id_city: Int) {
        with(connection) {
            val prepareSqlConnection = prepareStatement(
                """INSERT INTO orders (date, time,  price_products, price_delivery, total_price, id_person, id_city, street)
                            |VALUES (?,?,?,?,?,?, ?, ?)
                        """.trimMargin()
            )
            with(prepareSqlConnection) {
                setDate(1, Date().getDate())
                setTime(2, Date().getTime())
                setFloat(3, LocalCart.getPrice())
                setFloat(4, LocalCart.getDeliveryPrice())
                setFloat(5, LocalCart.getTotalPrice())
                setInt(6, User.getUser()?.id!!)
                setInt(7, id_city)
                setString(8, street)


                execute()
            }
        }

        insertIntoProducts()
    }

    private fun insertIntoProducts() {
        for (product in LocalCart.getProducts()) {
            with(connection) {
                val prepareSqlConnection = prepareStatement(
                    """INSERT INTO product_order(id_order, id_product)
                                |VALUES (?, ?)
                            """.trimMargin()
                )
                with(prepareSqlConnection) {
                    setInt(1, getLastOrder())
                    setInt(2, product.id)
                    execute()
                }
            }
        }
    }

    private fun getLastOrder(): Int {
        var id = 0
        val resultSet = connection.createStatement()
            .executeQuery("SELECT id FROM orders ORDER BY id DESC LIMIT 1")
        with(resultSet) {
            while (next()) {
                id = getInt("id")
            }
        }
        return id
    }

    fun getOrderHistory(person: Person): List<Order> {
        val listOrder = ArrayList<Order>()
        with(connection) {
            val prepareSqlConnection = prepareStatement(
                """
                SELECT o.id, o.date, o.time, o.price_products, o.price_delivery, o.id_person, c.id, c.name, r.name
                FROM orders o 
                JOIN city c ON c.id = o.id_city 
                JOIN region r ON r.id = c.id_ca
                WHERE o.id_person = ? 
            """.trimIndent()
            )
            with(prepareSqlConnection) {
                setInt(1, person.id)
                with(prepareSqlConnection.executeQuery()) {
                    while (next()) {
                        val id = getInt("o.id")
                        val date = getDate("o.date")
                        val time = getTime("o.time")
                        val priceProduct = getFloat("o.price_products")
                        val priceDelivery = getFloat("o.price_delivery")
                        val city = City(
                            getInt("c.id"),
                            getString("c.name"),
                            getString("r.name")
                        )
                        listOrder
                            .add(
                                Order(
                                    id,
                                    Date(date, time),
                                    priceProduct,
                                    priceDelivery,
                                    person,
                                    city
                                )
                            )
                    }
                }
            }
        }
        return listOrder
    }

    fun getListCart(idOrder: Int): Cart {
        val cart = Cart()
        val query = BASE_PRODUCT_SELECT + BASE_CART_JOIN
        for (i in queryProduct(query, intArrayOf(idOrder))){
            cart.add(i, 1)
        }
        return cart
    }


    fun getFavoriteList(): List<Product>{
        val listFavorites = ArrayList<Product>()
        for (i in User.getUser()!!.listFavorites){
            listFavorites.add(getProduct(i))
        }
        return listFavorites
    }


}
