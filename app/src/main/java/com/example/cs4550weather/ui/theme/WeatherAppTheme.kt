package com.example.cs4550weather.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable

private val colorTheme = lightColorScheme(
    primary = Color(0xFF557270),
    onPrimary = Color.White,
    background = Color(0xFFDCE7E4),
    surface = Color.White,
    onBackground = Color(0xFF557270),
    onSurface = Color(0xFF557270)
)

@Composable
fun WeatherAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = colorTheme,
        content = content
    )
}