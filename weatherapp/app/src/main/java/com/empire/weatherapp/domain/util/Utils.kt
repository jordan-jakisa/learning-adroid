package com.empire.weatherapp.domain.util

import android.util.Log
import com.empire.weatherapp.data.remote.models.NetworkWeather
import com.google.gson.Gson
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend fun HttpResponse.toNetworkWeather(): NetworkWeather?{
    return try {
        val json = this.body<String>()
        val gson = Gson()
        gson.fromJson(json, NetworkWeather::class.java)
    } catch (e: Exception){
        e.printStackTrace()
        null
    }
}