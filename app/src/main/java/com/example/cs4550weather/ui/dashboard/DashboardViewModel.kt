package com.example.cs4550weather.ui.dashboard

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.cs4550weather.data.model.SavedCity
import com.example.cs4550weather.data.repository.SavedCitiesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel : ViewModel() {

    private var savedCitiesRepository: SavedCitiesRepository? = null

    private val _savedCities = MutableStateFlow<List<SavedCity>>(emptyList())
    val savedCities: StateFlow<List<SavedCity>> = _savedCities.asStateFlow()

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