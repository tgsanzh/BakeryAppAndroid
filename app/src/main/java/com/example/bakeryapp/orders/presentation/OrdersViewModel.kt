package com.example.bakeryapp.orders.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.bakeryapp.orders.data.OrdersRepository
import com.example.bakeryapp.orders.domain.Orders
import com.example.bakeryapp.orders.domain.toEntity
import com.example.bakeryapp.resetHttpClient
import com.example.bakeryapp.utils.SharedPrefs
import com.example.bakeryapp.utils.ToastManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class OrdersViewModel(
    val repository: OrdersRepository,
    val sharedPrefs: SharedPrefs,
    val toastManager: ToastManager,
) : ViewModel() {
    val stateFlow = MutableStateFlow(
        OrdersState(
            orders = emptyList(),
            ordersLoaded = false
        )
    )

    fun dispatch(event: OrdersEvent, navigateToLogin: () -> Unit) {
        when (event) {
            is OrdersEvent.leaveAccount -> {
                sharedPrefs.setToken("")
                resetHttpClient(sharedPrefs.context)
                navigateToLogin()
            }

            OrdersEvent.loadData -> {
                viewModelScope.launch {
                    repository.loadOrders().onSuccess { response ->
                        stateFlow.value = stateFlow.value.copy(
                            orders = response.toEntity(),
                            ordersLoaded = true
                        )
                    }.onFailure {
                        toastManager.show("Не удалость загрузить заказы.")
                    }

                }
            }
        }
    }
}