package com.example.bakeryapp.cart.data

import com.example.bakeryapp.cart.presentation.CartObject
import io.ktor.client.HttpClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface CartRepository{
    suspend fun getCarts(): List<CartDTO>
    suspend fun plusToCart(cart: CartObject): Int
    suspend fun minusToCart(cart: CartObject): Int
    suspend fun order(address: String)
}