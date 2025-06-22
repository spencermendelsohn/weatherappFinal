package com.example.cs4550weather.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(username: String, password: String) {
        _loginState.value = LoginState.Loading

        if (username == "admin" && password == "admin") {
            _loginState.value = LoginState.Success
        } else {
            _loginState.value = LoginState.Error("Invalid credentials")
        }
    }
}