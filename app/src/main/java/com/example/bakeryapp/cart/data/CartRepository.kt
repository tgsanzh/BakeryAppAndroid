package com.example.bakeryapp.cart.data

import com.example.bakeryapp.cart.domain.CartObject

interface CartRepository {
    suspend fun getCarts(): Result<List<CartDTO>>
    suspend fun plusToCart(cart: CartObject): Result<Int>
    suspend fun minusToCart(cart: CartObject): Result<Int>
    suspend fun order(address: String): Result<Int>
}