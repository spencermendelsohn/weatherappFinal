package com.example.cs4550weather.data.model

data class WeatherUiState(
    val cityName: String = "",
    val temperature: String = "",
    val humidity: String = "",
    val windSpeed: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
) 