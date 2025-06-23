package com.example.cs4550weather.ui.notifications

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cs4550weather.data.model.SavedCity
import com.example.cs4550weather.data.model.WeatherUiState
import com.example.cs4550weather.data.repository.SavedCitiesRepository
import com.example.cs4550weather.data.repository.WeatherAppRepository
import com.example.cs4550weather.data.service.LocationService
import kotlinx.coroutines.launch

class NotificationsViewModel : ViewModel() {

    private val weatherAppRepository = WeatherAppRepository()
    private var savedCitiesRepository: SavedCitiesRepository? = null
    private var locationService: LocationService? = null
    
    private val _weatherState = MutableLiveData<WeatherUiState>(WeatherUiState())
    val weatherState: LiveData<WeatherUiState> = _weatherState

    fun initializeRepositories(context: Context) {
        savedCitiesRepository = SavedCitiesRepository(context)
        locationService = LocationService(context)
    }

    fun getCurrentLocationWeather() {
        _weatherState.value = _weatherState.value?.copy(isLoading = true, error = null)
        
        viewModelScope.launch {
            try {
                val locationResult = locationService?.getCurrentLocation()
                
                when (locationResult) {
                    is LocationService.LocationResult.Success -> {
                        val weatherResult = weatherAppRepository.getWeatherByCity(locationResult.cityName)
                        weatherResult.fold(
                            onSuccess = { weatherResponse ->
                                val uiState = WeatherUiState(
                                    cityName = locationResult.cityName,
                                    temperature = "${weatherResponse.current.temperature_2m}°C",
                                    humidity = "${weatherResponse.current.relative_humidity_2m}%",
                                    windSpeed = "${weatherResponse.current.wind_speed_10m} km/h",
                                    isLoading = false,
                                    error = null
                                )
                                _weatherState.value = uiState
                            },
                            onFailure = { exception ->
                                _weatherState.value = WeatherUiState(
                                    isLoading = false,
                                    error = exception.message ?: "Failed to get weather data"
                                )
                            }
                        )
                    }
                    is LocationService.LocationResult.Error -> {
                        _weatherState.value = WeatherUiState(
                            isLoading = false,
                            error = locationResult.message
                        )
                    }
                    null -> {
                        _weatherState.value = WeatherUiState(
                            isLoading = false,
                            error = "Location service not initialized"
                        )
                    }
                }
            } catch (e: Exception) {
                _weatherState.value = WeatherUiState(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun saveCurrentLocation() {
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

    fun isCurrentLocationSaved(): Boolean {
        val currentState = _weatherState.value
        return currentState?.cityName?.isNotEmpty() == true && 
               savedCitiesRepository?.isCitySaved(currentState.cityName) == true
    }
}