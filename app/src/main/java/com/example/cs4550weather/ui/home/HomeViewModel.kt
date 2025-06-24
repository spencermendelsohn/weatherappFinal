package com.example.cs4550weather.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.net.UnknownHostException
import com.example.cs4550weather.data.model.SavedCity
import com.example.cs4550weather.data.model.WeatherResponse
import com.example.cs4550weather.data.model.WeatherUiState
import com.example.cs4550weather.data.repository.SavedCitiesRepository
import com.example.cs4550weather.data.repository.WeatherAppRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val weatherAppRepository = WeatherAppRepository()
    private var savedCitiesRepository: SavedCitiesRepository? = null

    private val _weatherState = MutableStateFlow(WeatherUiState())
    val weatherState: StateFlow<WeatherUiState> = _weatherState.asStateFlow()

    private val _fullWeatherResponse = MutableLiveData<WeatherResponse?>()
    val fullWeatherResponse: LiveData<WeatherResponse?> = _fullWeatherResponse

    fun initializeRepository(context: Context) {
        savedCitiesRepository = SavedCitiesRepository(context)
    }

    fun searchWeather(cityName: String) {
        if (cityName.isBlank()) {
            _weatherState.value = WeatherUiState(error = "Please enter a city name")
            return
        }

        _weatherState.value = _weatherState.value.copy(isLoading = true, error = null)
        
        viewModelScope.launch {
            try {
                val result = weatherAppRepository.getWeatherByCity(cityName)
                result.fold(
                    onSuccess = { weatherResponse ->
                        _fullWeatherResponse.value = weatherResponse

                        val uiState = WeatherUiState(
                            cityName = cityName,
                            temperature = "${weatherResponse.current.temperature_2m}°C",
                            humidity = "${weatherResponse.current.relative_humidity_2m}%",
                            windSpeed = "${weatherResponse.current.wind_speed_10m} km/h",
                            weatherCondition = getWeatherCondition(weatherResponse.current.weather_code),
                            rainToday = "${weatherResponse.hourly.precipitation_probability.firstOrNull() ?: 0}%",
                            uvIndex = "${weatherResponse.current.uv_index}",
                            isLoading = false,
                            error = null
                        )
                        Log.d("WeatherDebug", "Full weather response: ${weatherResponse}")
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

    fun getWeatherCondition(code: Int): String {
        return when (code) {
            0 -> "Clear"
            1 -> "Mainly Clear"
            2 -> "Partly Cloudy"
            3 -> "Overcast"
            45, 48 -> "Foggy"
            51, 53, 55 -> "Drizzle"
            56, 57 -> "Freezing Drizzle"
            61, 63, 65 -> "Rain"
            66, 67 -> "Freezing Rain"
            71, 73, 75, 77 -> "Snow"
            80, 81, 82 -> "Rain Showers"
            85, 86	-> "Snow Showers"
            95, 96, 99 -> "Thunderstorm"
            else -> "Unknown"
        }
    }
}