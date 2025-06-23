package com.example.cs4550weather.ui.dashboard.weatherdetails.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather_table")
data class CurrentWeatherEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "temperature") val temperature: Double,
    @ColumnInfo(name = "uv_index") val uvIndex: Double,
    @ColumnInfo(name = "weather_code") val weatherCode: Int
)