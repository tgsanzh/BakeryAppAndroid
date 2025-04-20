package com.example.bakeryapp.orders.presentation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.bakeryapp.cart.presentation.CartEvent
import com.example.bakeryapp.cart.presentation.CartState
import com.example.bakeryapp.orders.domain.OrdersUseCase
import kotlinx.coroutines.flow.MutableStateFlow

class OrdersViewModel(
    val useCase: OrdersUseCase
): ViewModel() {
    val stateFlow = MutableStateFlow(
        OrdersState(
            orders = emptyList()
        )
    )

    fun dispatch(event: OrdersEvent, navController: NavController) {
        when(event) {
            is OrdersEvent.onCancel -> {

            }
        }
    }
}