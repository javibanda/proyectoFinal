package com.example.proyectofinal.util

import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*
import java.util.Date
import java.util.regex.Pattern

class Date {

    private val date: java.sql.Date
    private val time: Time
    private val fullDate: Date

    constructor(){
        this.date = getDate()
        this.time = getTime()
        this.fullDate = getFullDate()
    }

    constructor(date: java.sql.Date, time:Time){

        this.date = date
        this.time = time
        this.fullDate = getFullDateFromDatabase()
    }

    fun getDate() = java.sql.Date(Calendar.getInstance().time.time)

    fun getTime() = Time(Calendar.getInstance().time.time)

    private fun getFullDate(): Date = Date(time.time)

    private fun getFullDateFromDatabase(): Date = Date(date.time + time.time + addOneHour())

    private fun addOneHour(): Long = 3600000

    private fun setFormat(pattern: String) = SimpleDateFormat(pattern).format(fullDate)

    private fun getSecond() = setFormat("ss")

    private fun getMinute() = setFormat("mm")

    private fun getHour() = setFormat("HH")

    private fun getDay() = setFormat("dd")

    private fun getMonth() = setFormat("MM")

    private fun getYear() = setFormat("yyyy")

    fun getDateFormat() = "${getYear()}-${getMonth()}-${getDay()}"

    fun getTimeFormat() = "${getHour()}:${getMinute()}:${getSecond()}"
}