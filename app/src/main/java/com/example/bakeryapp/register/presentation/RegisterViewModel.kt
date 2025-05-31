package com.example.bakeryapp.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.bakeryapp.login.domain.ValidateNumberUseCase
import com.example.bakeryapp.login.domain.ValidatePasswordUseCase
import com.example.bakeryapp.register.data.RegisterRepository
import com.example.bakeryapp.register.data.RegisterRequest
import com.example.bakeryapp.register.data.RegisterResponse
import com.example.bakeryapp.resetHttpClient
import com.example.bakeryapp.utils.SharedPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    val repository: RegisterRepository,
    val sharedPrefs: SharedPrefs,
    val validatePasswordUseCase: ValidatePasswordUseCase,
    val validateNumberUseCase: ValidateNumberUseCase
) : ViewModel() {
    val stateFlow = MutableStateFlow(
        RegisterState(
            number = "",
            password = "",
            confirmPassword = "",
            errorText = "",
            showError = false,
            buttonEnabled = true,
        )
    )

    fun dispatch(event: RegisterEvent, navController: NavController) {
        when (event) {
            is RegisterEvent.register -> {
                if (
                    validateNumberUseCase(stateFlow.value.number) &&
                    validatePasswordUseCase(stateFlow.value.password) &&
                    stateFlow.value.confirmPassword == stateFlow.value.password
                ) {
                    viewModelScope.launch {
                        stateFlow.value = stateFlow.value.copy(buttonEnabled = false)
                        try {
                            val response: RegisterResponse = repository.register(
                                RegisterRequest(
                                    phone = "+7${stateFlow.value.number}",
                                    password = stateFlow.value.password
                                )
                            )
                            sharedPrefs.setToken(response.access_token)
                            resetHttpClient(sharedPrefs.context)
                            navController.navigate("catalog") {
                                popUpTo(0) { inclusive = true }
                            }
                        } catch (e: Exception) {
                            stateFlow.value = stateFlow.value.copy(showError = true)
                            stateFlow.value =
                                stateFlow.value.copy(errorText = "Неправильный номер или пароль")
                        }
                        stateFlow.value = stateFlow.value.copy(buttonEnabled = true)
                    }
                } else if (!validateNumberUseCase(stateFlow.value.number)) {
                    stateFlow.value = stateFlow.value.copy(showError = true)
                    stateFlow.value = stateFlow.value.copy(errorText = "Заполните номер")
                } else if (!validatePasswordUseCase(stateFlow.value.password)) {
                    stateFlow.value = stateFlow.value.copy(showError = true)
                    stateFlow.value =
                        stateFlow.value.copy(errorText = "Пароль должен быть больше 8 символов")
                } else if (stateFlow.value.confirmPassword != stateFlow.value.password) {
                    stateFlow.value = stateFlow.value.copy(showError = true)
                    stateFlow.value = stateFlow.value.copy(errorText = "Пароль не совпадает")
                }
            }

            is RegisterEvent.onNumberChanged -> {
                stateFlow.value = stateFlow.value.copy(number = event.value)
            }

            is RegisterEvent.onPasswordChanged -> {
                stateFlow.value = stateFlow.value.copy(password = event.value)
            }

            is RegisterEvent.onConfirmPasswordChanged -> {
                stateFlow.value = stateFlow.value.copy(confirmPassword = event.value)
            }

            RegisterEvent.toLogin -> {
                navController.navigate("login") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = false
                }
            }
        }
    }

}