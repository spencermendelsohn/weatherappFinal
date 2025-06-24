package com.example.cs4550weather.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cs4550weather.data.repository.WeatherAppRepository
import com.example.cs4550weather.ui.dashboard.weatherdetails.data.WeatherDatabase
import com.example.cs4550weather.ui.dashboard.weatherdetails.data.WeatherRepository
import com.example.cs4550weather.ui.dashboard.weatherdetails.viewmodel.NotificationsViewModel

class HomeViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            val dao = WeatherDatabase.getDatabase(context).weatherDao()
            val apiRepository = WeatherAppRepository()
            val repository = WeatherRepository(apiRepository, dao)

            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Not a HomeViewModel")
    }
}