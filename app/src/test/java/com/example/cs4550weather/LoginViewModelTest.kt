package com.example.cs4550weather

import com.example.cs4550weather.ui.home.LoginState
import com.example.cs4550weather.ui.home.LoginViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel()
    }

    @Test
    fun login_withAdminCredentials_setsStateToSuccess() {
        val username = "admin"
        val password = "admin"

        viewModel.login(username, password)

        assertEquals(LoginState.Success, viewModel.loginState.value)
    }
} 