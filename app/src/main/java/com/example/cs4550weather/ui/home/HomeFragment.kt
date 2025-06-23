package com.example.cs4550weather.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cs4550weather.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize the repository
        homeViewModel.initializeRepository(requireContext())

        // Set up search button click listener
        binding.searchButton.setOnClickListener {
            val cityName = binding.cityInput.text.toString().trim()
            homeViewModel.searchWeather(cityName)
        }

        // Set up save city button click listener
        binding.saveCityButton.setOnClickListener {
            homeViewModel.saveCurrentCity()
            updateSaveButtonState(homeViewModel)
        }

        // Observe weather state changes
        homeViewModel.weatherState.observe(viewLifecycleOwner) { weatherState ->
            updateUI(weatherState)
            updateSaveButtonState(homeViewModel)
        }

        return root
    }

    private fun updateUI(weatherState: com.example.cs4550weather.data.model.WeatherUiState) {
        // Show/hide loading state
        binding.searchButton.isEnabled = !weatherState.isLoading

        if (weatherState.isLoading) {
            binding.searchButton.text = "Searching..."
        } else {
            binding.searchButton.text = "Search"
        }

        // Handle error state
        if (weatherState.error != null) {
            binding.errorText.text = weatherState.error
            binding.errorText.visibility = View.VISIBLE

            // Hide weather information and save button
            binding.cityNameText.visibility = View.GONE
            binding.temperatureText.visibility = View.GONE
            binding.weatherDescriptionText.visibility = View.GONE
            binding.humidityText.visibility = View.GONE
            binding.windSpeedText.visibility = View.GONE
            binding.saveCityButton.visibility = View.GONE
            return
        }

        // Hide error text
        binding.errorText.visibility = View.GONE

        // Show weather information if available
        if (weatherState.cityName.isNotEmpty()) {
            binding.cityNameText.text = weatherState.cityName
            binding.cityNameText.visibility = View.VISIBLE

            binding.temperatureText.text = weatherState.temperature
            binding.temperatureText.visibility = View.VISIBLE

            binding.weatherDescriptionText.text = "Current Weather"
            binding.weatherDescriptionText.visibility = View.VISIBLE

            binding.humidityText.text = "Humidity: ${weatherState.humidity}"
            binding.humidityText.visibility = View.VISIBLE

            binding.windSpeedText.text = "Wind Speed: ${weatherState.windSpeed}"
            binding.windSpeedText.visibility = View.VISIBLE
        } else {
            // Hide all weather information and save button
            binding.cityNameText.visibility = View.GONE
            binding.temperatureText.visibility = View.GONE
            binding.weatherDescriptionText.visibility = View.GONE
            binding.humidityText.visibility = View.GONE
            binding.windSpeedText.visibility = View.GONE
            binding.saveCityButton.visibility = View.GONE
        }
    }

    private fun updateSaveButtonState(homeViewModel: HomeViewModel) {
        val weatherState = homeViewModel.weatherState.value
        if (weatherState?.cityName?.isNotEmpty() == true && weatherState.error == null) {
            binding.saveCityButton.visibility = View.VISIBLE
            if (homeViewModel.isCurrentCitySaved()) {
                binding.saveCityButton.text = "City Saved ✓"
                binding.saveCityButton.isEnabled = false
            } else {
                binding.saveCityButton.text = "Save City"
                binding.saveCityButton.isEnabled = true
            }
        } else {
            binding.saveCityButton.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}