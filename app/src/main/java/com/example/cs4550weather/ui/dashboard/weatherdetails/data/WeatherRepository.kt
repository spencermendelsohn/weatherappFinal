package com.example.cs4550weather.ui.dashboard.weatherdetails.data

import com.example.cs4550weather.data.api.WeatherApiService

class WeatherRepository(private val api: WeatherApiService, private val weatherDao: WeatherDao) {
}