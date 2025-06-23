package com.example.cs4550weather.ui.dashboard.weatherdetails.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(products: List<CurrentWeatherEntity>)

    @Query("SELECT * FROM current_weather_table")
    suspend fun getCurrentWeather(): List<CurrentWeatherEntity>

    @Query("DELETE FROM current_weather_table")
    suspend fun clearCurrentWeather()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyWeather(products: List<HourlyWeatherEntity>)

    @Query("SELECT * FROM hourly_weather_table ORDER BY time ASC")
    suspend fun getHourlyWeather(): List<HourlyWeatherEntity>

    @Query("DELETE FROM hourly_weather_table")
    suspend fun clearHourlyWeather()

    // In order to get average temperature for next day
    @Query("SELECT * FROM hourly_weather_table WHERE time LIKE :date || '%' ORDER BY time ASC")
    suspend fun getHourlyForecastForDate(date: String): List<HourlyWeatherEntity>

}