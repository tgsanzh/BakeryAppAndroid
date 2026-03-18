package com.example.bakeryapp.register.presentation

import android.widget.Toast
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
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
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

    fun dispatch(
        event: RegisterEvent,
        toLogin: () -> Unit,
        toCatalog: () -> Unit
    )
    {
        when (event) {
            is RegisterEvent.register -> {
                if (
                    validateNumberUseCase(stateFlow.value.number) &&
                    validatePasswordUseCase(stateFlow.value.password) &&
                    stateFlow.value.confirmPassword == stateFlow.value.password
                ) {
                    viewModelScope.launch {
                        stateFlow.value = stateFlow.value.copy(buttonEnabled = false)
                        repository.register(
                            RegisterRequest(
                                phone = "+7${stateFlow.value.number}",
                                password = stateFlow.value.password
                            )
                        ).onSuccess { response ->
                            sharedPrefs.setToken(response.access_token)
                            resetHttpClient(sharedPrefs.context)
                            toCatalog()
                        }.onFailure { error ->
                            val errorText = when(error) {
                                is ClientRequestException -> "Ошибка ${error.response.status.value}"
                                is ServerResponseException -> "Ошибка ${error.response.status.value}"
                                is ResponseException -> "Ошибка ${error.response.status.value}"
                                else -> "Подключение к серверу невозможно"
                            }
                            stateFlow.value = stateFlow.value.copy(showError = true, errorText = errorText)
                        }
                        stateFlow.value = stateFlow.value.copy(buttonEnabled = true)

                    }
                }
                else if (!validateNumberUseCase(stateFlow.value.number)) {
                    stateFlow.value = stateFlow.value.copy(
                        showError = true,
                        errorText = "Заполните номер"
                    )
                }
                else if (!validatePasswordUseCase(stateFlow.value.password)) {
                    stateFlow.value = stateFlow.value.copy(
                        showError = true,
                        errorText = "Пароль должен быть больше 8 символов"
                    )
                }
                else if (stateFlow.value.confirmPassword != stateFlow.value.password) {
                    stateFlow.value = stateFlow.value.copy(
                        showError = true,
                        errorText = "Пароль не совпадает"
                    )
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
                toLogin()
            }
        }
    }

}