package com.example.cs4550weather.data.repository

import com.example.cs4550weather.data.api.WeatherApiService
import com.example.cs4550weather.data.model.GeocodingResponse
import com.example.cs4550weather.data.model.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class WeatherRepository {
    
    private val weatherApiService: WeatherApiService
    private val geocodingApiService: WeatherApiService
    
    init {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        
        val weatherRetrofit = Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        
        val geocodingRetrofit = Retrofit.Builder()
            .baseUrl("https://geocoding-api.open-meteo.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        
        weatherApiService = weatherRetrofit.create(WeatherApiService::class.java)
        geocodingApiService = geocodingRetrofit.create(WeatherApiService::class.java)
    }
    
    suspend fun getWeatherByCity(cityName: String): Result<WeatherResponse> {
        return withContext(Dispatchers.IO) {
            try {
                // First, get coordinates for the city
                val geocodingResponse = geocodingApiService.searchCity(cityName)
                
                if (geocodingResponse.results.isNullOrEmpty()) {
                    return@withContext Result.failure(Exception("City not found"))
                }
                
                val city = geocodingResponse.results[0]
                
                // Then, get weather data using the coordinates
                val weatherResponse = weatherApiService.getWeather(
                    latitude = city.latitude,
                    longitude = city.longitude
                )
                
                Result.success(weatherResponse)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
} 