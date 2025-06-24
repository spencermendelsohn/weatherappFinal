package com.example.cs4550weather.ui.dashboard.weatherdetails.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cs4550weather.data.repository.WeatherAppRepository
import com.example.cs4550weather.ui.dashboard.weatherdetails.data.WeatherDatabase
import com.example.cs4550weather.ui.dashboard.weatherdetails.data.WeatherRepository

class NotificationsViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationsViewModel::class.java)) {
            val dao = WeatherDatabase.getDatabase(context).weatherDao()
            val apiRepository = WeatherAppRepository()
            val repository = WeatherRepository(apiRepository, dao)

            @Suppress("UNCHECKED_CAST")
            return NotificationsViewModel(repository) as T
        }
        throw IllegalArgumentException("Not a NotificationsViewModel")
    }
}