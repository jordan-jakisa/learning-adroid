package com.empire.weatherapp.data.remote.mappers

import com.empire.weatherapp.data.remote.models.NetworkWeather
import com.empire.weatherapp.domain.weather.WeatherData
import com.empire.weatherapp.domain.weather.WeatherInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int, val data: WeatherData
)

fun NetworkWeather.toNetworkWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = this.teamperature[index]
        val weatherCode = this.waetherCode[index]
        val windSpeed = this.windspeed_10m[index]
        val pressure = this.pressure_msl[index]
        val humidity = this.relativehumidifty_2m[index]
        IndexedWeatherData(
            index = index, data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = ""
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}

fun NetworkWeather.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = this.toNetworkWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap, currentWeather = currentWeatherData
    )
}