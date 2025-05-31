package com.example.bakeryapp.cart.presentation

import com.example.bakeryapp.cart.domain.CartObject

data class CartState(
    val cartItems: List<CartObject>,
    val errorText: String,
    val showError: Boolean,
    val sum: Float,
    val deliveryAdress: String,
    val cartLoaded: Boolean,
    val buttonEnabled: Boolean,
)

