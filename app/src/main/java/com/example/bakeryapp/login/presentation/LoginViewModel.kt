package com.example.bakeryapp.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.bakeryapp.login.data.LoginRepository
import com.example.bakeryapp.login.data.LoginRequest
import com.example.bakeryapp.login.data.LoginResponse
import com.example.bakeryapp.login.domain.ValidateNumberUseCase
import com.example.bakeryapp.login.domain.ValidatePasswordUseCase
import com.example.bakeryapp.resetHttpClient
import com.example.bakeryapp.utils.SharedPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    val repository: LoginRepository,
    val sharedPrefs: SharedPrefs,
    val validateNumberUseCase: ValidateNumberUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {
    val stateFlow = MutableStateFlow(
        LoginState(
            number = "",
            password = "",
            errorText = "",
            showError = false,
            buttonEnabled = true,
        )
    )

    fun dispatch(
        event: LoginEvent,
        toRegister: () -> Unit,
        toCatalog: () -> Unit
    ) {
        when (event) {
            is LoginEvent.login -> {
                if (validateNumberUseCase(stateFlow.value.number) && validatePasswordUseCase(
                        stateFlow.value.password
                    )
                ) {
                    viewModelScope.launch {
                        stateFlow.value = stateFlow.value.copy(buttonEnabled = false)
                        repository.login(
                            LoginRequest(
                                phone = "+7${stateFlow.value.number}",
                                password = stateFlow.value.password
                            )
                        ).onSuccess { response ->
                            sharedPrefs.setToken(response.access_token)
                            resetHttpClient(sharedPrefs.context)
                            toCatalog()
                        }.onFailure { error ->
                            stateFlow.value = stateFlow.value.copy(
                                showError = true,
                                errorText = "Неправильный номер или пароль"
                            )
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
            }

            is LoginEvent.onNumberChanged -> {
                stateFlow.value = stateFlow.value.copy(number = event.value)
            }

            is LoginEvent.onPasswordChanged -> {
                stateFlow.value = stateFlow.value.copy(password = event.value)
            }

            LoginEvent.toRegister -> {
                toRegister()
            }
        }
    }

}