package com.example.cs4550weather.data.api

import com.example.cs4550weather.data.model.GeocodingResponse
import com.example.cs4550weather.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    
    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String =
            "temperature_2m,relative_humidity_2m,wind_speed_10m,weather_code,uv_index",
        @Query("hourly") hourly: String =
            "temperature_2m,relative_humidity_2m,wind_speed_10m,precipitation_probability"
    ): WeatherResponse
    
    @GET("v1/search")
    suspend fun searchCity(
        @Query("name") cityName: String,
        @Query("count") count: Int = 1,
        @Query("language") language: String = "en",
        @Query("format") format: String = "json"
    ): GeocodingResponse
} 