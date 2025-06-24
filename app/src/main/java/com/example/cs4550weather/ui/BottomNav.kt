package com.example.cs4550weather.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cs4550weather.ui.dashboard.weatherdetails.view.CityListScreen
import com.example.cs4550weather.ui.dashboard.weatherdetails.view.HomeScreen
import com.example.cs4550weather.ui.dashboard.weatherdetails.view.NotificationsScreen

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "Search", Icons.Default.Add)
    object Dashboard : BottomNavItem("dashboard", "Cities", Icons.Default.List)
    object Notifications : BottomNavItem("notifications", "Location", Icons.Default.LocationOn)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppWithBottomNav() {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Dashboard,
        BottomNavItem.Notifications
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = BottomNavItem.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen()
            }
            composable(BottomNavItem.Dashboard.route) {
                CityListScreen(
                    onCityClick = { cityName ->
                        navController.navigate("weather_details/$cityName")
                    },
                    onAddCityClick = {
                        navController.navigate(BottomNavItem.Home.route)
                    }
                )
            }
            composable(BottomNavItem.Notifications.route) {
                NotificationsScreen()
            }
            composable("weather_details/{cityName}") { backStackEntry ->
                val cityName = backStackEntry.arguments?.getString("cityName") ?: ""
                WeatherDetailNavGraph(
                    selectedCityName = cityName,
                    onBackToCity = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}