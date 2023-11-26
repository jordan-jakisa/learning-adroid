package com.empire.weatherapp.domain.repository

import com.empire.weatherapp.domain.weather.WeatherInfo

interface WeatherRepository {

    suspend fun getWeatherData(lat: Double, lon: Double): Resource<WeatherInfo>
}

sealed class Resource<T>(message: String? = null, data: T? = null) {
    class Error<T>(message: String, data: T? = null) : Resource<T>(message = message, data = data)
    class Success<T>(data: T?) : Resource<T>(data = data)

}