package com.example.bakeryapp.confirm.presentation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.bakeryapp.confirm.domain.ConfirmUseCase
import kotlinx.coroutines.flow.MutableStateFlow

class ConfirmViewModel(
    val useCase: ConfirmUseCase
): ViewModel() {
    val stateFlow = MutableStateFlow(
        ConfirmState(
            number = ""
        )
    )

    fun dispatch(event: ConfirmEvent, navController: NavController) {
        when (event) {
            is ConfirmEvent.confirmCode -> {

            }

            is ConfirmEvent.onValueChanged -> {
                stateFlow.value = stateFlow.value.copy(number = event.value)
            }

            ConfirmEvent.back -> {
                navController.popBackStack()
            }
        }
    }

}