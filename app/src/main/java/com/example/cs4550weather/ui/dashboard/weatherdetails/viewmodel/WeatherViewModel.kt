package com.example.cs4550weather.ui.dashboard.weatherdetails.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cs4550weather.data.model.WeatherResponse
import com.example.cs4550weather.data.model.WeatherUiState
import com.example.cs4550weather.data.service.ClothingRecommendationService
import com.example.cs4550weather.R
import com.example.cs4550weather.ui.dashboard.weatherdetails.data.WeatherEntity
import com.example.cs4550weather.ui.dashboard.weatherdetails.data.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val clothingRecommendationService = ClothingRecommendationService()

    private val _averageTempToday = MutableStateFlow("")
    val averageTempToday = _averageTempToday.asStateFlow()

    private val _averageTempTomorrow = MutableStateFlow("")
    val averageTempTomorrow = _averageTempTomorrow.asStateFlow()

    private val _hourlyTemps = MutableStateFlow("")
    val hourlyTemps = _hourlyTemps.asStateFlow()

    private val _weatherCondition = MutableStateFlow("")
    val weatherCondition = _weatherCondition.asStateFlow()

    private val _rainProbability = MutableStateFlow("")
    val rainProbability = _rainProbability.asStateFlow()

    private val _uvIndex = MutableStateFlow("")
    val uvIndex = _uvIndex.asStateFlow()

    private val _cityName = MutableStateFlow("")
    val cityName: StateFlow<String> = _cityName.asStateFlow()

    private val _weatherState = MutableStateFlow(WeatherUiState())
    val weatherState: StateFlow<WeatherUiState> = _weatherState.asStateFlow()

    // Clothing recommendation properties
    private val _option1Text = MutableStateFlow("")
    val option1Text: StateFlow<String> = _option1Text.asStateFlow()

    private val _option1Image = MutableStateFlow(0)
    val option1Image: StateFlow<Int> = _option1Image.asStateFlow()

    private val _option2Text = MutableStateFlow("")
    val option2Text: StateFlow<String> = _option2Text.asStateFlow()

    private val _option2Image = MutableStateFlow(0)
    val option2Image: StateFlow<Int> = _option2Image.asStateFlow()

    fun loadWeatherForCity(cityName: String) {
        if (cityName.isBlank()) {
            _weatherState.value = WeatherUiState(error = "Invalid city name")
            return
        }

        _weatherState.value = _weatherState.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val result = repository.getWeatherByCity(cityName)
                result.fold(
                    onSuccess = { weatherResponse ->
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

                        _weatherState.value = uiState
                        updateIndividualProperties(uiState, weatherResponse)
                        generateClothingRecommendations(uiState)
                    },
                    onFailure = { exception ->
                        Log.d("WeatherViewModel", "Loaded weather data for $cityName (from API or cache)")
                        val savedCity = repository.getSavedWeather(cityName)
                        if (savedCity != null) {
                            _weatherState.value = savedCity.toUiState()
                        } else {
                            _weatherState.value = WeatherUiState(
                                isLoading = false,
                                error = "Connection error. Could not load data"
                            )
                            clearIndividualProperties()
                        }
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
                clearIndividualProperties()
            }
        }
    }

    private fun WeatherEntity.toUiState(): WeatherUiState {
        return WeatherUiState(
            cityName = this.cityName,
            temperature = this.temperature,
            humidity = this.humidity,
            windSpeed = this.windSpeed,
            weatherCondition = this.weatherCondition,
            rainToday = this.rainToday,
            uvIndex = this.uvIndex,
            isLoading = false,
            error = null
        )
    }

    private fun updateIndividualProperties(uiState: WeatherUiState, weatherResponse: WeatherResponse) {
        _cityName.value = uiState.cityName
        _averageTempToday.value = uiState.temperature

        // Calculate tomorrow's temperature (next 24 hours average)
        val tomorrowTemps = weatherResponse.hourly.temperature_2m.take(24)
        val tomorrowAvg = if (tomorrowTemps.isNotEmpty()) {
            tomorrowTemps.average()
        } else {
            weatherResponse.current.temperature_2m
        }
        _averageTempTomorrow.value = "${String.format("%.1f", tomorrowAvg)}°C"

        // Format hourly temperatures (next 5 hours)
        val nextHours = weatherResponse.hourly.temperature_2m.take(5)
        _hourlyTemps.value = nextHours.joinToString(" ") { "${it.toInt()}°" }

        _weatherCondition.value = uiState.weatherCondition
        _rainProbability.value = uiState.rainToday
        _uvIndex.value = uiState.uvIndex
    }

    private fun generateClothingRecommendations(uiState: WeatherUiState) {
        val recommendation = clothingRecommendationService.getClothingRecommendation(
            temperature = uiState.temperature,
            humidity = uiState.humidity,
            windSpeed = uiState.windSpeed
        )

        val recommendations = recommendation.recommendations
        if (recommendations.isNotEmpty()) {
            _option1Text.value = recommendations.getOrNull(0) ?: "Light clothing"
            _option2Text.value = recommendations.getOrNull(1) ?: "Comfortable shoes"
            getOutfitImage(recommendations.getOrNull(0) ?: "Light clothing")
        } else {
            _option1Text.value = "Light clothing"
            _option2Text.value = "Comfortable shoes"
            _option1Image.value = R.drawable.light_shirt
            _option2Image.value = R.drawable.sandals
        }
    }

    private fun getOutfitImage(recommendation: String) {
        if (recommendation.equals("Heavy winter coat or parka")) {
            _option1Image.value = R.drawable.winter_coat
            _option2Image.value = R.drawable.winter_accessories
        } else if (recommendation.equals("Warm jacket or coat")) {
            _option1Image.value = R.drawable.warm_jacket
            _option2Image.value = R.drawable.light_sweater
        } else if (recommendation.equals("Light jacket or cardigan")) {
            _option1Image.value = R.drawable.light_cardigan
            _option2Image.value = R.drawable.long_jeans
        } else if (recommendation.equals("Light shirt or t-shirt")) {
            _option1Image.value = R.drawable.light_tshirt
            _option2Image.value = R.drawable.shorts
        } else if (recommendation.equals("Light, breathable shirt")) {
            _option1Image.value = R.drawable.light_shirt
            _option2Image.value = R.drawable.shorts
        } else if (recommendation.equals("Shorts and tank top/t-shirt")) {
            _option1Image.value = R.drawable.shorts_tshirt
            _option2Image.value = R.drawable.sandals
        }
    }

    private fun clearIndividualProperties() {
        _cityName.value = ""
        _averageTempToday.value = ""
        _averageTempTomorrow.value = ""
        _hourlyTemps.value = ""
        _weatherCondition.value = ""
        _rainProbability.value = ""
        _uvIndex.value = ""
        _option1Text.value = ""
        _option2Text.value = ""
        _option1Image.value = R.drawable.ic_launcher_foreground
        _option2Image.value = R.drawable.ic_launcher_foreground
    }

    private fun getWeatherCondition(code: Int): String {
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

