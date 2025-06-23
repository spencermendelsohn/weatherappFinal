package com.example.cs4550weather.ui.dashboard.weatherdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview() {
    CurrentWeatherScreen(
        cityName = "Boston",
        averageTempToday = "22",
        averageTempTomorrow = "30",
        hourlyTemps = "5 10 15 20 25",
        onMoreInfoClick = {},
        onBackToCityListClick = {}
    )
}

@Composable
fun CurrentWeatherScreen(
    cityName: String,
    averageTempToday: String,
    averageTempTomorrow: String,
    hourlyTemps: String,
    onMoreInfoClick: () -> Unit,
    onBackToCityListClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFDCE7E4))
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = cityName,
                fontSize = 35.sp,
                color = Color(0xFF557270),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(40.dp))

            Text("Average Today", fontSize = 35.sp, color = Color(0xFF557270))
            Spacer(Modifier.height(10.dp))
            TextBox(averageTempToday)

            Spacer(Modifier.height(20.dp))

            Text("Average Tomorrow", fontSize = 35.sp, color = Color(0xFF557270))
            Spacer(Modifier.height(10.dp))
            TextBox(averageTempTomorrow)

            Spacer(Modifier.height(20.dp))

            Text("Hourly", fontSize = 35.sp, color = Color(0xFF557270))
            Spacer(Modifier.height(10.dp))
            TextBox(hourlyTemps)

            Spacer(Modifier.height(60.dp))

            NavigationButton("MORE INFO", onMoreInfoClick)
            Spacer(Modifier.height(10.dp))
            NavigationButton("BACK", onBackToCityListClick)
        }
    }
}


