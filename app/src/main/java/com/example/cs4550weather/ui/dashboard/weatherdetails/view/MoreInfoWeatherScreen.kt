package com.example.cs4550weather.ui.dashboard.weatherdetails.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cs4550weather.ui.home.HomeViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun MoreInfoWeatherScreen(
    cityName: String,
    weatherCondition: String,
    rainToday: String,
    uvIndex: String,
    onWhatToWearClick: () -> Unit,
    onBackToCurrentWeatherClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFDCE7E4))
            .verticalScroll(scrollState)
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Add some top spacing
        Spacer(Modifier.height(40.dp))

        // City Name
        Text(
            text = cityName,
            fontSize = 28.sp,
            color = Color(0xFF557270),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(32.dp))

        // Weather Condition
        Text(
            text = "Weather Condition",
            fontSize = 24.sp,
            color = Color(0xFF557270)
        )
        Spacer(Modifier.height(8.dp))
        TextBox(weatherCondition)

        Spacer(Modifier.height(16.dp))

        // Rain Today
        Text(
            text = "Rain Today?",
            fontSize = 24.sp,
            color = Color(0xFF557270)
        )
        Spacer(Modifier.height(8.dp))
        TextBox(rainToday)

        Spacer(Modifier.height(16.dp))

        // UV Index
        Text(
            text = "UV Index",
            fontSize = 24.sp,
            color = Color(0xFF557270)
        )
        Spacer(Modifier.height(8.dp))
        TextBox(uvIndex)

        Spacer(Modifier.height(40.dp))

        // Navigation Buttons
        NavigationButton("WHAT TO WEAR", onWhatToWearClick)
        Spacer(Modifier.height(8.dp))
        NavigationButton("BACK", onBackToCurrentWeatherClick)

        // Add bottom padding for better scrolling
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
fun MoreInfoWeatherScreenWrapper(
    viewModel: HomeViewModel,
    onWhatToWearClick: () -> Unit,
    onBackToCurrentWeatherClick: () -> Unit
) {
    val weatherState = viewModel.weatherState.collectAsState().value

    MoreInfoWeatherScreen(
        cityName = weatherState.cityName,
        weatherCondition = weatherState.temperature,
        rainToday = weatherState.rainToday,
        uvIndex = weatherState.uvIndex,
        onWhatToWearClick = onWhatToWearClick,
        onBackToCurrentWeatherClick = onBackToCurrentWeatherClick
    )
}