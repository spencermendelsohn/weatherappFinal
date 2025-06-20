package com.example.cs4550weather.ui.notifications

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cs4550weather.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ||
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Permission granted, get location weather
                viewModel.getCurrentLocationWeather()
            }
            else -> {
                // Permission denied
                binding.errorText.text = "Location permission is required to get your current weather"
                binding.errorText.visibility = View.VISIBLE
            }
        }
    }

    private lateinit var viewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize repositories
        viewModel.initializeRepositories(requireContext())

        // Set up get location button click listener
        binding.getLocationButton.setOnClickListener {
            checkLocationPermissionAndGetWeather()
        }

        // Set up save location button click listener
        binding.saveLocationButton.setOnClickListener {
            viewModel.saveCurrentLocation()
            updateSaveButtonState()
        }

        // Observe weather state changes
        viewModel.weatherState.observe(viewLifecycleOwner) { weatherState ->
            updateUI(weatherState)
            updateSaveButtonState()
        }

        return root
    }

    private fun checkLocationPermissionAndGetWeather() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted, get location weather
                viewModel.getCurrentLocationWeather()
            }
            else -> {
                // Request permission
                locationPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

    private fun updateUI(weatherState: com.example.cs4550weather.data.model.WeatherUiState) {
        // Show/hide loading state
        binding.getLocationButton.isEnabled = !weatherState.isLoading
        
        if (weatherState.isLoading) {
            binding.getLocationButton.text = "Getting Location..."
        } else {
            binding.getLocationButton.text = "Get My Location"
        }

        // Handle error state
        if (weatherState.error != null) {
            binding.errorText.text = weatherState.error
            binding.errorText.visibility = View.VISIBLE
            
            // Hide weather information and save button
            binding.locationNameText.visibility = View.GONE
            binding.temperatureText.visibility = View.GONE
            binding.weatherDescriptionText.visibility = View.GONE
            binding.humidityText.visibility = View.GONE
            binding.windSpeedText.visibility = View.GONE
            binding.saveLocationButton.visibility = View.GONE
            return
        }

        // Hide error text
        binding.errorText.visibility = View.GONE

        // Show weather information if available
        if (weatherState.cityName.isNotEmpty()) {
            binding.locationNameText.text = weatherState.cityName
            binding.locationNameText.visibility = View.VISIBLE

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
            binding.locationNameText.visibility = View.GONE
            binding.temperatureText.visibility = View.GONE
            binding.weatherDescriptionText.visibility = View.GONE
            binding.humidityText.visibility = View.GONE
            binding.windSpeedText.visibility = View.GONE
            binding.saveLocationButton.visibility = View.GONE
        }
    }

    private fun updateSaveButtonState() {
        val weatherState = viewModel.weatherState.value
        if (weatherState?.cityName?.isNotEmpty() == true && weatherState.error == null) {
            binding.saveLocationButton.visibility = View.VISIBLE
            if (viewModel.isCurrentLocationSaved()) {
                binding.saveLocationButton.text = "Location Saved ✓"
                binding.saveLocationButton.isEnabled = false
            } else {
                binding.saveLocationButton.text = "Save This Location"
                binding.saveLocationButton.isEnabled = true
            }
        } else {
            binding.saveLocationButton.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}