package com.empire.weatherapp.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod

private const val BASE_URL = "https://api.open-meteo.com/v1/forecast?hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl"

private val client = HttpClient(Android) {
    install(HttpRequestRetry) {
        retryOnServerErrors(maxRetries = 5)
        exponentialDelay()
    }
}

object WeatherAPI {
    suspend fun getWeather(lat: String, lon: String): HttpResponse {
        return client.request(BASE_URL) {
            method = HttpMethod.Get
            url {
                parameters.append("latitude", lat)
                parameters.append("longitude", lon)
            }
        }
    }
}