package com.example.cs4550weather.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.net.UnknownHostException
import com.example.cs4550weather.data.model.SavedCity
import com.example.cs4550weather.data.model.WeatherUiState
import com.example.cs4550weather.data.repository.SavedCitiesRepository
import com.example.cs4550weather.data.repository.WeatherRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val weatherRepository = WeatherRepository()
    private var savedCitiesRepository: SavedCitiesRepository? = null
    
    private val _weatherState = MutableLiveData<WeatherUiState>(WeatherUiState())
    val weatherState: LiveData<WeatherUiState> = _weatherState

    fun initializeRepository(context: Context) {
        savedCitiesRepository = SavedCitiesRepository(context)
    }

    fun searchWeather(cityName: String) {
        if (cityName.isBlank()) {
            _weatherState.value = WeatherUiState(error = "Please enter a city name")
            return
        }

        _weatherState.value = _weatherState.value?.copy(isLoading = true, error = null)
        
        viewModelScope.launch {
            try {
                val result = weatherRepository.getWeatherByCity(cityName)
                result.fold(
                    onSuccess = { weatherResponse ->
                        val uiState = WeatherUiState(
                            cityName = cityName,
                            temperature = "${weatherResponse.current.temperature_2m}°C",
                            humidity = "${weatherResponse.current.relative_humidity_2m}%",
                            windSpeed = "${weatherResponse.current.wind_speed_10m} km/h",
                            isLoading = false,
                            error = null
                        )
                        _weatherState.value = uiState
                    },
                    onFailure = { exception ->
                        val errorMessage = when (exception) {
                            is UnknownHostException -> "Invalid location!"
                            else -> exception.message ?: "Unknown error occurred"
                        }

                        _weatherState.value = WeatherUiState(
                            isLoading = false,
                            error = errorMessage
                        )
                    }
                )
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is UnknownHostException -> "Invalid location!"
                    else -> e.message ?: "Unknown error occurred"
                }

                _weatherState.value = WeatherUiState(
                    isLoading = false,
                    error = errorMessage
                )
            }
        }
    }

    fun saveCurrentCity() {
        val currentState = _weatherState.value
        if (currentState?.cityName?.isNotEmpty() == true && savedCitiesRepository != null) {
            val savedCity = SavedCity(
                name = currentState.cityName,
                temperature = currentState.temperature,
                humidity = currentState.humidity,
                windSpeed = currentState.windSpeed
            )
            savedCitiesRepository!!.addCity(savedCity)
        }
    }

    fun isCurrentCitySaved(): Boolean {
        val currentState = _weatherState.value
        return currentState?.cityName?.isNotEmpty() == true && 
               savedCitiesRepository?.isCitySaved(currentState.cityName) == true
    }
}