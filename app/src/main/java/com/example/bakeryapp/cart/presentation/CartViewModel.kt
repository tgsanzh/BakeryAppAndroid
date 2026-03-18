package com.example.bakeryapp.cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.bakeryapp.cart.data.CartRepository
import com.example.bakeryapp.cart.domain.CartObject
import com.example.bakeryapp.cart.domain.toEntity
import com.example.bakeryapp.utils.ToastManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    val repository: CartRepository, val toastManager: ToastManager
) : ViewModel() {
    val stateFlow = MutableStateFlow(
        CartState(
            cartItems = listOf(),
            errorText = "",
            showError = false,
            sum = 0f,
            deliveryAdress = "",
            cartLoaded = false,
            buttonEnabled = true
        )
    )

    fun dispatch(event: CartEvent) {
        when (event) {
            is CartEvent.onOrder -> {
                stateFlow.value = stateFlow.value.copy(buttonEnabled = false)
                if (stateFlow.value.deliveryAdress != "")
                    viewModelScope.launch {
                        repository.order(stateFlow.value.deliveryAdress)
                        loadCart()
                    }
                else {
                    stateFlow.value =
                        stateFlow.value.copy(showError = true, errorText = "Заполните адресс")
                }

                stateFlow.value = stateFlow.value.copy(buttonEnabled = true)
            }

            CartEvent.loadCarts -> {
                loadCart()
            }

            is CartEvent.minus -> {
                viewModelScope.launch {
                    repository.minusToCart(event.value).onSuccess { response ->
                        val updatedItem = event.value.copy(quantity = event.value.quantity - 1)
                        if (updatedItem.quantity <= 0) {
                            loadCart()
                        } else {
                            val updatedCarts = stateFlow.value.cartItems
                                .map {
                                    if (it.id == event.value.id) updatedItem else it
                                }
                            stateFlow.value = stateFlow.value.copy(cartItems = updatedCarts)
                        }
                    }.onFailure { error ->
                        toastManager.show("Не удалось изменить!")
                    }
                    get_sum()
                }
            }

            is CartEvent.plus -> {
                viewModelScope.launch {
                    repository.plusToCart(event.value).onSuccess { response ->
                        val updatedItem = event.value.copy(quantity = event.value.quantity + 1)

                        val updatedCarts = stateFlow.value.cartItems
                            .map {
                                if (it.id == event.value.id) updatedItem else it
                            }
                        stateFlow.value = stateFlow.value.copy(cartItems = updatedCarts)

                    }.onFailure { error ->
                        toastManager.show("Не удалось изменить!")
                    }
                    get_sum()
                }
            }

            is CartEvent.adressChanged -> {
                stateFlow.value = stateFlow.value.copy(deliveryAdress = event.value)
            }
        }
    }

    fun get_sum() {
        var sum: Float = 0.0f
        stateFlow.value.cartItems.map {
            sum += it.quantity * it.short_product.price
        }
        stateFlow.value = stateFlow.value.copy(sum = sum)
    }

    fun loadCart() {
        viewModelScope.launch {
            repository.getCarts().onSuccess { response ->
                stateFlow.value = stateFlow.value.copy(
                    cartItems = response.toEntity().toMutableList(),
                    cartLoaded = true,
                )
            }.onFailure { error ->
                toastManager.show("Не удалось получить данные с сервера.")
            }
            get_sum()
        }
    }

}