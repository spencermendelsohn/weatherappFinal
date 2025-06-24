package com.example.cs4550weather.ui.dashboard.weatherdetails.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cs4550weather.ui.dashboard.weatherdetails.data.WeatherRepository
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

}