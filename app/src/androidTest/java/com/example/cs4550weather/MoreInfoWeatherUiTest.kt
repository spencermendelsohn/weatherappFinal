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
import com.example.cs4550weather.ui.dashboard.weatherdetails.view.MoreInfoWeatherScreen


@RunWith(AndroidJUnit4::class)
class MoreInfoWeatherUiTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val city = "Boston"
    private val weatherCondition = "Rain"
    private val rainToday = "30%"
    private val uvIndex = "5.5"

    @Test
    fun testAllInfoIsDisplayed() {
        composeTestRule.setContent {
            MoreInfoWeatherScreen(
                cityName = city,
                weatherCondition = weatherCondition,
                rainToday = rainToday,
                uvIndex = uvIndex,
                onWhatToWearClick = {},
                onBackToCurrentWeatherClick = {}
            )
        }
        composeTestRule.onNodeWithText(city).assertIsDisplayed()
        composeTestRule.onNodeWithText("Weather Condition").assertIsDisplayed()
        composeTestRule.onNodeWithText(weatherCondition).assertIsDisplayed()
        composeTestRule.onNodeWithText("Rain Today?").assertIsDisplayed()
        composeTestRule.onNodeWithText(rainToday).assertIsDisplayed()
        composeTestRule.onNodeWithText("UV Index").assertIsDisplayed()
        composeTestRule.onNodeWithText(uvIndex).assertIsDisplayed()
        composeTestRule.onNodeWithText("WHAT TO WEAR").assertIsDisplayed()
        composeTestRule.onNodeWithText("BACK").assertIsDisplayed()
    }

    @Test
    fun testButtonsCanBeClicked() {
        var whatToWearClicked = false
        var backClicked = false

        composeTestRule.setContent {
            MoreInfoWeatherScreen (
                cityName = city,
                weatherCondition = weatherCondition,
                rainToday = rainToday,
                uvIndex = uvIndex,
                onWhatToWearClick = { whatToWearClicked = true },
                onBackToCurrentWeatherClick = { backClicked = true }
            )
        }

        composeTestRule.onNodeWithText("WHAT TO WEAR").performClick()
        assert(whatToWearClicked)
        composeTestRule.onNodeWithText("BACK").performClick()
        assert(backClicked)
    }

}