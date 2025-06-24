package com.example.cs4550weather.ui.dashboard.weatherdetails.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherEntity(
    @PrimaryKey val cityName: String = "",
    val temperature: String = "",
    val humidity: String = "",
    val windSpeed: String = "",
    val weatherCondition: String = "",
    val rainToday: String = "",
    val uvIndex: String = ""
)
