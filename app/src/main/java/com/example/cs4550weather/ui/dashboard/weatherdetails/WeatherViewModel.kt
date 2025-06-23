package com.example.cs4550weather.ui.dashboard.weatherdetails

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WeatherViewModel (private val repository: WeatherRepository) : ViewModel() {
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