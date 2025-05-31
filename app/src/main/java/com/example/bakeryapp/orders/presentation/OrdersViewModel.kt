package com.example.bakeryapp.orders.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.bakeryapp.orders.data.OrdersRepository
import com.example.bakeryapp.orders.domain.Orders
import com.example.bakeryapp.orders.domain.toEntity
import com.example.bakeryapp.resetHttpClient
import com.example.bakeryapp.utils.SharedPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class OrdersViewModel(
    val repository: OrdersRepository,
    val sharedPrefs: SharedPrefs,
) : ViewModel() {
    val stateFlow = MutableStateFlow(
        OrdersState(
            orders = emptyList(),
            ordersLoaded = false
        )
    )

    fun dispatch(event: OrdersEvent, navController: NavController) {
        when (event) {
            is OrdersEvent.leaveAccount -> {
                sharedPrefs.setToken("")
                resetHttpClient(sharedPrefs.context)
                navController.navigate("login") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = false
                }
            }

            OrdersEvent.loadData -> {
                viewModelScope.launch {
                    val response: List<Orders> = repository.loadOrders().toEntity()
                    stateFlow.value = stateFlow.value.copy(orders = response)
                    stateFlow.value = stateFlow.value.copy(ordersLoaded = true)

                }
            }
        }
    }
}