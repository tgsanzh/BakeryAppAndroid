package com.example.bakeryapp.cart.presentation

import com.example.bakeryapp.cart.domain.CartObject

sealed interface CartEvent {
    object onOrder : CartEvent
    object loadCarts : CartEvent
    data class plus(
        val value: CartObject
    ) : CartEvent

    data class minus(
        val value: CartObject
    ) : CartEvent

    data class adressChanged(
        val value: String
    ) : CartEvent
}