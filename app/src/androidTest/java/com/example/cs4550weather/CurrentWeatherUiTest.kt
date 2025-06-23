package com.example.cs4550weather

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.Test
import com.example.cs4550weather.ui.dashboard.weatherdetails.view.CurrentWeatherScreen


@RunWith(AndroidJUnit4::class)
class CurrentWeatherUiTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val city = "Boston"
    private val today = "22°C"
    private val tomorrow = "30°C"
    private val hourly = "5°C 10°C 10°C 20°C 20°C"

    @Test
    fun testAllInfoIsDisplayed() {
        composeTestRule.setContent {
            CurrentWeatherScreen(
                cityName = city,
                averageTempToday = today,
                averageTempTomorrow = tomorrow,
                hourlyTemps = hourly,
                onMoreInfoClick = {},
                onBackToCityListClick = {}
            )
        }
        composeTestRule.onNodeWithText(city).assertIsDisplayed()
        composeTestRule.onNodeWithText("Average Today").assertIsDisplayed()
        composeTestRule.onNodeWithText(today).assertIsDisplayed()
        composeTestRule.onNodeWithText("Average Tomorrow").assertIsDisplayed()
        composeTestRule.onNodeWithText(tomorrow).assertIsDisplayed()
        composeTestRule.onNodeWithText("Hourly").assertIsDisplayed()
        composeTestRule.onNodeWithText(hourly).assertIsDisplayed()
        composeTestRule.onNodeWithText("MORE INFO").assertIsDisplayed()
        composeTestRule.onNodeWithText("BACK").assertIsDisplayed()
    }

    @Test
    fun testButtonsCanBeClicked() {
        var moreInfoClicked = false
        var backClicked = false

        composeTestRule.setContent {
            CurrentWeatherScreen(
                cityName = city,
                averageTempToday = today,
                averageTempTomorrow = tomorrow,
                hourlyTemps = hourly,
                onMoreInfoClick = { moreInfoClicked = true },
                onBackToCityListClick = { backClicked = true }
            )
        }

        composeTestRule.onNodeWithText("MORE INFO").performClick()
        assert(moreInfoClicked)
        composeTestRule.onNodeWithText("BACK").performClick()
        assert(backClicked)
    }

}