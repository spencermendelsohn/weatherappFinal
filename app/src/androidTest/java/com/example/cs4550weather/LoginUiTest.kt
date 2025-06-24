package com.example.cs4550weather

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cs4550weather.ui.dashboard.weatherdetails.view.LoginScreen
import com.example.cs4550weather.ui.home.LoginViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    class FakeLoginViewModel() : LoginViewModel() {
    }

    val viewModel = FakeLoginViewModel()

    @Test
    fun testLoginInfoIsDisplayed() {
        composeTestRule.setContent {
            LoginScreen(
                onLoginSuccess = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithTag("usernameTextField").performTextInput("user")
        composeTestRule.onNodeWithTag("passwordTextField").performTextInput("password")

        composeTestRule.onNodeWithTag("loginButton").performClick()

    }
}
