package com.example.cs4550weather.data.model

data class SavedCity(
    val name: String,
    val temperature: String,
    val humidity: String,
    val windSpeed: String,
    val timestamp: Long = System.currentTimeMillis()
) 