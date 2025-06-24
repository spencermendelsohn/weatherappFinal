package com.example.cs4550weather.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cs4550weather.ui.dashboard.weatherdetails.view.CurrentWeatherScreen
import com.example.cs4550weather.ui.dashboard.weatherdetails.view.MoreInfoWeatherScreen
import com.example.cs4550weather.ui.dashboard.weatherdetails.view.OutfitRecommendationsScreen
import com.example.cs4550weather.ui.dashboard.weatherdetails.viewmodel.WeatherViewModel

@Composable
fun NavGraph(
    weatherViewModel: WeatherViewModel = WeatherViewModel()
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "current_weather") {
        composable("current_weather") {
            CurrentWeatherScreen(
                cityName = weatherViewModel.cityName.collectAsState().value,
                averageTempToday = weatherViewModel.averageTempToday.collectAsState().value,
                averageTempTomorrow = weatherViewModel.averageTempTomorrow.collectAsState().value,
                hourlyTemps = weatherViewModel.hourlyTemps.collectAsState().value,
                onMoreInfoClick = { navController.navigate("more_info") },
                onBackToCityListClick = { /* optional */ }
            )
        }
        composable("more_info") {
            MoreInfoWeatherScreen(
                cityName = weatherViewModel.cityName.collectAsState().value,
                weatherCondition = weatherViewModel.weatherCondition.collectAsState().value,
                rainToday = weatherViewModel.rainProbability.collectAsState().value,
                uvIndex = weatherViewModel.uvIndex.collectAsState().value,
                onWhatToWearClick = { navController.navigate("outfit_recs") },
                onBackToCurrentWeatherClick = { navController.navigateUp() }
            )
        }
        composable("outfit_recs") {
            OutfitRecommendationsScreen(
                cityName = weatherViewModel.cityName.collectAsState().value,
                option1Text = weatherViewModel.option1Text.collectAsState().value,
                option1Image = weatherViewModel.option1Image.collectAsState().value,
                option2Text = weatherViewModel.option2Text.collectAsState().value,
                option2Image = weatherViewModel.option2Image.collectAsState().value,
                onBackToMoreInfoClick = { navController.navigateUp() }
            )
        }
    }
}