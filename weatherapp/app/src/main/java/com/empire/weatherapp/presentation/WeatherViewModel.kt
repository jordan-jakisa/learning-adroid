package com.empire.weatherapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.empire.weatherapp.domain.location.LocationTracker
import com.empire.weatherapp.domain.repository.Resource
import com.empire.weatherapp.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository, private val locationTracker: LocationTracker
) : ViewModel() {
    var state by mutableStateOf(WeatherState())
        private set

    fun getWeatherInfo() = viewModelScope.launch {
        state = state.copy(
            isLoading = true, error = null
        )

        locationTracker.getCurrentLocation()?.let { location ->
            when (val resource = repository.getWeatherData(location.latitude, location.longitude)) {
                is Resource.Success -> {
                    state = state.copy(
                        weatherInfo = resource.data, isLoading = false, error = null
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        weatherInfo = null, isLoading = false, error = resource.message
                    )
                }
            }

        } ?: kotlin.run {
            state = state.copy(
                isLoading = false,
                error = "Couldn't retrieve location. Make sure to grant location permission and enableGPS."
            )
        }
    }
}