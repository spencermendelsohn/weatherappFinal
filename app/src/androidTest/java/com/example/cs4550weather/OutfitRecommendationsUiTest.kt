package com.example.cs4550weather

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.Test
import com.example.cs4550weather.ui.dashboard.weatherdetails.view.OutfitRecommendationsScreen


@RunWith(AndroidJUnit4::class)
class OutfitRecommendationsUiTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val city = "Boston"
    private val option1 = "Shorts"
    private val option1Image = R.drawable.ic_launcher_foreground
    private val option2 = "Skirt"
    private val option2Image = R.drawable.ic_launcher_foreground

    @Test
    fun testAllInfoIsDisplayed() {
        composeTestRule.setContent {
            OutfitRecommendationsScreen(
                cityName = city,
                option1Text = option1,
                option1Image = option1Image,
                option2Text = option2,
                option2Image = option2Image,
                onBackToMoreInfoClick = {}
            )
        }
        composeTestRule.onNodeWithText(city).assertIsDisplayed()
        composeTestRule.onNodeWithText("Recommendations").assertIsDisplayed()
        composeTestRule.onNodeWithText(option1).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Option 1 Image").assertIsDisplayed()
        composeTestRule.onNodeWithText(option2).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Option 2 Image").assertIsDisplayed()
        composeTestRule.onNodeWithText("BACK").assertIsDisplayed()
    }

    @Test
    fun testButtonsCanBeClicked() {
        var backClicked = false

        composeTestRule.setContent {
            OutfitRecommendationsScreen (
                cityName = city,
                option1Text = option1,
                option1Image = option1Image,
                option2Text = option2,
                option2Image = option2Image,
                onBackToMoreInfoClick = { backClicked = true}
            )
        }

        composeTestRule.onNodeWithText("BACK").performClick()
        assert(backClicked)
    }

}