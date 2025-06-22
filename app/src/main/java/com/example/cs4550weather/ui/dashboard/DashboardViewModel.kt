package com.example.cs4550weather.ui.dashboard

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cs4550weather.data.model.SavedCity
import com.example.cs4550weather.data.repository.SavedCitiesRepository

class DashboardViewModel : ViewModel() {

    private var savedCitiesRepository: SavedCitiesRepository? = null
    
    private val _savedCities = MutableLiveData<List<SavedCity>>(emptyList())
    val savedCities: LiveData<List<SavedCity>> = _savedCities

    fun initializeRepository(context: Context) {
        savedCitiesRepository = SavedCitiesRepository(context)
        loadSavedCities()
    }

    fun loadSavedCities() {
        savedCitiesRepository?.let { repository ->
            _savedCities.value = repository.getSavedCities()
        }
    }

    fun removeCity(city: SavedCity) {
        savedCitiesRepository?.removeCity(city.name)
        loadSavedCities()
    }
}