package com.empire.weatherapp.domain.repository

import com.empire.weatherapp.domain.util.Resource
import com.empire.weatherapp.domain.weather.WeatherInfo

interface WeatherRepository {

    suspend fun getWeatherData(lat: Double, lon: Double): Resource<WeatherInfo>
}