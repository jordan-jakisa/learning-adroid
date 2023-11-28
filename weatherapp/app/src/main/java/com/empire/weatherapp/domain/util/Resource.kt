package com.empire.weatherapp.domain.util

sealed class Resource<T>(val message: String? = null, val data: T? = null) {
    class Error<T>(message: String, data: T? = null) : Resource<T>(message = message, data = data)
    class Success<T>(data: T?) : Resource<T>(data = data)

}