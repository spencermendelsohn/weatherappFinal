package com.example.cs4550weather.ui

sealed class Screen(val route: String) {
    object CurrentWeather : Screen("current_weather")
    object MoreInfo : Screen("more_info")
    object OutfitRecs : Screen("outfit_recs")
}