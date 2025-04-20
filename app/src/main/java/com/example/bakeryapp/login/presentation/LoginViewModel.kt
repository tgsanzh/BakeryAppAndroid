package com.example.bakeryapp.login.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.bakeryapp.login.data.LoginRepository
import com.example.bakeryapp.login.data.LoginRequest
import com.example.bakeryapp.login.data.LoginResponse
import com.example.bakeryapp.utils.SharedPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    val repository: LoginRepository,
    val sharedPrefs: SharedPrefs
): ViewModel() {
    val stateFlow = MutableStateFlow(
        LoginState(
            number = "",
            password = "",
            errorText = "",
            showError = false,
        )
    )

    fun dispatch(event: LoginEvent, navController: NavController) {
        when (event) {
            is LoginEvent.login -> {
                if (stateFlow.value.number.length == 10 && stateFlow.value.password.length >= 8) {
                    viewModelScope.launch {
                        try {
                            val response: LoginResponse = repository.login(
                                LoginRequest(
                                    phone = "+7${stateFlow.value.number}",
                                    password = stateFlow.value.password
                                )
                            )
                            sharedPrefs.setToken(response.access_token)
                            navController.navigate("catalog") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                        catch(e: Exception) {
                            stateFlow.value = stateFlow.value.copy(showError = true)
                            stateFlow.value = stateFlow.value.copy(errorText = "Неправильный номер или пароль")
                        }
                    }
                }
                else if (stateFlow.value.number.length != 10) {
                    stateFlow.value = stateFlow.value.copy(showError = true)
                    stateFlow.value = stateFlow.value.copy(errorText = "Заполните номер")
                }
                else if (stateFlow.value.password.length < 8) {
                    stateFlow.value = stateFlow.value.copy(showError = true)
                    stateFlow.value = stateFlow.value.copy(errorText = "Пароль должен быть больше 8 символов")
                }
            }

            is LoginEvent.onNumberChanged -> {
                stateFlow.value = stateFlow.value.copy(number = event.value)
            }

            is LoginEvent.onPasswordChanged -> {
                stateFlow.value = stateFlow.value.copy(password = event.value)
            }
        }
    }

}