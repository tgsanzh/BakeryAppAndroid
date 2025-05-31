package com.example.bakeryapp.cart.data

import com.example.bakeryapp.cart.domain.CartObject

interface CartRepository {
    suspend fun getCarts(): List<CartDTO>
    suspend fun plusToCart(cart: CartObject): Int
    suspend fun minusToCart(cart: CartObject): Int
    suspend fun order(address: String)
}