package com.example.bakeryapp.cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.bakeryapp.cart.data.CartRepository
import com.example.bakeryapp.cart.domain.CartObject
import com.example.bakeryapp.cart.domain.toEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    val repository: CartRepository
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

    fun dispatch(event: CartEvent, navController: NavController) {
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
                    if (repository.minusToCart(event.value) == 200) {
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
                    }
                    get_sum()
                }
            }

            is CartEvent.plus -> {
                viewModelScope.launch {
                    if (repository.plusToCart(event.value) == 200) {
                        val updatedItem = event.value.copy(quantity = event.value.quantity + 1)

                        val updatedCarts = stateFlow.value.cartItems
                            .map {
                                if (it.id == event.value.id) updatedItem else it
                            }

                        stateFlow.value = stateFlow.value.copy(cartItems = updatedCarts)
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
            try {
                val response: MutableList<CartObject> =
                    repository.getCarts().toEntity().toMutableList()
                stateFlow.value = stateFlow.value.copy(cartItems = response)
                get_sum()
            } catch (e: Exception) {

            }
            stateFlow.value = stateFlow.value.copy(cartLoaded = true)

        }
    }

}