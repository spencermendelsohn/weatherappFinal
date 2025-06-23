package com.example.cs4550weather.ui.dashboard.weatherdetails.view

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
fun MoreInfoWeatherScreenPreview() {
    MoreInfoWeatherScreen(
        cityName = "Boston",
        weatherCondition = "Sunny",
        rainToday = "30%",
        uvIndex = "5",
        onWhatToWearClick = {},
        onBackToCurrentWeatherClick = {}
    )
}

@Composable
fun MoreInfoWeatherScreen(
    cityName: String,
    weatherCondition: String,
    rainToday: String,
    uvIndex: String,
    onWhatToWearClick: () -> Unit,
    onBackToCurrentWeatherClick: () -> Unit
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

            Text("Weather Condition", fontSize = 35.sp, color = Color(0xFF557270))
            Spacer(Modifier.height(10.dp))
            TextBox(weatherCondition)

            Spacer(Modifier.height(20.dp))

            Text("Rain Today?", fontSize = 35.sp, color = Color(0xFF557270))
            Spacer(Modifier.height(10.dp))
            TextBox(rainToday)

            Spacer(Modifier.height(20.dp))

            Text("UV Index", fontSize = 35.sp, color = Color(0xFF557270))
            Spacer(Modifier.height(10.dp))
            TextBox(uvIndex)

            Spacer(Modifier.height(60.dp))

            NavigationButton("WHAT TO WEAR", onWhatToWearClick)
            Spacer(Modifier.height(10.dp))
            NavigationButton("BACK", onBackToCurrentWeatherClick)
        }
    }
}


