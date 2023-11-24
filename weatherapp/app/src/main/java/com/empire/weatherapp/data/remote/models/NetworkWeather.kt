package com.empire.weatherapp.data.remote.models

import android.util.Log
import com.google.gson.Gson
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

data class NetworkWeather(
    val time: List<String>,
    val teamperature: List<Double>,
    val waetherCode: List<Int>,
    val relativehumidifty_2m: List<Int>,
    val windspeed_10m: List<Double>,
    val pressure_msl: List<Double>
)
