package com.example.cs4550weather.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.cs4550weather.data.model.SavedCity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SavedCitiesRepository(context: Context) {
    
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("saved_cities", Context.MODE_PRIVATE)
    private val gson = Gson()
    
    fun getSavedCities(): List<SavedCity> {
        val json = sharedPreferences.getString("cities", "[]")
        val type = object : TypeToken<List<SavedCity>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }
    
    fun addCity(city: SavedCity) {
        val cities = getSavedCities().toMutableList()
        
        // Remove existing city with same name if it exists
        cities.removeAll { it.name == city.name }
        
        // Add the new city
        cities.add(city)
        
        // Save back to SharedPreferences
        val json = gson.toJson(cities)
        sharedPreferences.edit().putString("cities", json).apply()
    }
    
    fun removeCity(cityName: String) {
        val cities = getSavedCities().toMutableList()
        cities.removeAll { it.name == cityName }
        
        val json = gson.toJson(cities)
        sharedPreferences.edit().putString("cities", json).apply()
    }
    
    fun isCitySaved(cityName: String): Boolean {
        return getSavedCities().any { it.name == cityName }
    }
    
    fun clearAllCities() {
        sharedPreferences.edit().putString("cities", "[]").apply()
    }
} 