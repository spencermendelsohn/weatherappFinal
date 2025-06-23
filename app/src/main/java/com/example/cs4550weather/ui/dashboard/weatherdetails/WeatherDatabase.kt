package com.example.cs4550weather.ui.dashboard.weatherdetails

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrentWeatherEntity::class, HourlyWeatherEntity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}