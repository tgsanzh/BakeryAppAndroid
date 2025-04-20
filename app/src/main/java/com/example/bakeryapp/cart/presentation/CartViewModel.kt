package com.example.bakeryapp.cart.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.bakeryapp.cart.data.CartRepository
import com.example.bakeryapp.catalog.presentation.Category
import com.example.bakeryapp.catalog.presentation.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    val repository: CartRepository
): ViewModel() {
    val stateFlow = MutableStateFlow(
        CartState(
            cartItems = listOf(),
            sum = 0f,
            deliveryAdress = ""
        )
    )

    fun dispatch(event: CartEvent, navController: NavController) {
        when(event) {
            is CartEvent.onOrder -> {
                if(stateFlow.value.deliveryAdress != "")
                    viewModelScope.launch {
                        repository.order(stateFlow.value.deliveryAdress)
                        loadCart()
                    }
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
                        }
                        else {
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
                val response: MutableList<CartObject> = repository.getCarts().map {
                    CartObject(
                        id = it.cart_item_id,
                        userId = it.user_id,
                        quantity = it.quantity,
                        short_product = ShortProduct(
                            id = it.Products.product_id,
                            title = it.Products.name,
                            price = it.Products.price,
                            imageUrl = it.Products.image_url,
                        ),
                    )
                }.toMutableList()
                stateFlow.value = stateFlow.value.copy(cartItems = response)
                get_sum()
            }
            catch (e: Exception) {

            }
        }
    }

}