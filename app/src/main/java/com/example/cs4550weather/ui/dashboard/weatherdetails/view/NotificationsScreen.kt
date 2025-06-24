package com.example.cs4550weather.ui.dashboard.weatherdetails.view

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cs4550weather.ui.dashboard.weatherdetails.viewmodel.NotificationsViewModel

@Composable
fun NotificationsScreen(
    viewModel: NotificationsViewModel
) {
    val context = LocalContext.current
    val weatherState by viewModel.weatherState.collectAsState()
    val scrollState = rememberScrollState()

    // Location permission launcher
    val locationPermissionRequest = rememberLauncherForActivityResult(
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
                Toast.makeText(context, "Location permission is required to get your current weather", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Initialize repository
    LaunchedEffect(Unit) {
        viewModel.initializeRepositories(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDCE7E4))
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Add some top spacing
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Current Location",
            fontSize = 28.sp,
            color = Color(0xFF557270),
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Get Location Button
        Button(
            onClick = {
                locationPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            },
            modifier = Modifier
                .width(180.dp)
                .height(42.dp)
                .testTag("get_location_button"),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF546980)
            ),
            shape = RoundedCornerShape(0.dp),
            enabled = !weatherState.isLoading
        ) {
            if (weatherState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Get My Location",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Weather Information (when available)
        if (weatherState.cityName.isNotEmpty() && weatherState.error == null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = weatherState.cityName,
                        fontSize = 20.sp,
                        color = Color(0xFF557270),
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Current Weather",
                        fontSize = 16.sp,
                        color = Color(0xFF557270)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = weatherState.temperature,
                        modifier = Modifier.testTag("temperatureText"),
                        fontSize = 18.sp,
                        color = Color(0xFF557270),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Humidity: ${weatherState.humidity}",
                        fontSize = 14.sp,
                        color = Color(0xFF557270)
                    )

                    Text(
                        text = "Wind Speed: ${weatherState.windSpeed}",
                        fontSize = 14.sp,
                        color = Color(0xFF557270)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            viewModel.saveCurrentLocation()
                            Toast.makeText(context, "Location saved!", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .width(180.dp)
                            .height(42.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF546980)
                        ),
                        shape = RoundedCornerShape(0.dp),
                        enabled = !viewModel.isCurrentLocationSaved()
                    ) {
                        Text(
                            text = if (viewModel.isCurrentLocationSaved()) "Location Saved ✓" else "Save This Location",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

        // Error Message (when there's an error)
        if (weatherState.error != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE5E5)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = weatherState.error!!,
                    modifier = Modifier.padding(16.dp),
                    color = Color(0xFF8B0000),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Info Text (when no location data)
        if (weatherState.cityName.isEmpty() && weatherState.error == null && !weatherState.isLoading) {
            Text(
                text = "Tap 'Get My Location' to see weather for your current location.",
                fontSize = 16.sp,
                color = Color(0xFF557270),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        // Add bottom padding for better scrolling
        Spacer(modifier = Modifier.height(40.dp))
    }
}