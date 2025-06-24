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

@Composable
fun CurrentWeatherScreen(
    cityName: String,
    averageTempToday: String,
    averageTempTomorrow: String,
    hourlyTemps: String,
    onMoreInfoClick: () -> Unit,
    onBackToCityListClick: () -> Unit
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
        Spacer(Modifier.height(40.dp))

        Text(
            text = cityName,
            fontSize = 28.sp,
            color = Color(0xFF557270),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(32.dp))

        Text(
            text = "Average Today",
            fontSize = 24.sp,
            color = Color(0xFF557270)
        )
        Spacer(Modifier.height(8.dp))
        TextBox(averageTempToday)

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Average Tomorrow",
            fontSize = 24.sp,
            color = Color(0xFF557270)
        )
        Spacer(Modifier.height(8.dp))
        TextBox(averageTempTomorrow)

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Hourly",
            fontSize = 24.sp,
            color = Color(0xFF557270)
        )
        Spacer(Modifier.height(8.dp))
        TextBox(hourlyTemps)

        Spacer(Modifier.height(40.dp))

        NavigationButton("MORE INFO", onMoreInfoClick)
        Spacer(Modifier.height(8.dp))
        NavigationButton("BACK", onBackToCityListClick)

        Spacer(Modifier.height(32.dp))
    }
}

