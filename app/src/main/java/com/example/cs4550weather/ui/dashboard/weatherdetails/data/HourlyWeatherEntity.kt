package com.example.cs4550weather.ui.dashboard.weatherdetails.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hourly_weather_table")
data class HourlyWeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "temperature") val temperature: Double,
    @ColumnInfo(name = "precipitation_probability") val precipitationProbability: Int
)

