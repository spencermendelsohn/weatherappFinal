package com.example.cs4550weather

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cs4550weather.ui.dashboard.DashboardViewModel
import com.example.cs4550weather.ui.dashboard.weatherdetails.view.CityListScreen
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.Test
import com.example.cs4550weather.ui.dashboard.weatherdetails.view.CurrentWeatherScreen


@RunWith(AndroidJUnit4::class)
class CityListUiTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    class FakeDashboardViewModel() : DashboardViewModel() {
    }

    val viewModel = FakeDashboardViewModel()

    @Test
    fun testAllInfoIsDisplayed() {
        composeTestRule.setContent {
            CityListScreen(
                onCityClick = {},
                viewModel = viewModel
            )
        }
        composeTestRule.onNodeWithText("Cities").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add City").assertIsDisplayed()
    }


}