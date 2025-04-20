package com.example.bakeryapp.cart.presentation

import com.example.bakeryapp.catalog.presentation.Product

data class CartState(
    val cartItems: List<CartObject>,
    val sum: Float,
    val deliveryAdress: String
)

data class CartObject(
    val id: Int,
    val userId: Int,
    var quantity: Int,
    val short_product: ShortProduct,
)

data class ShortProduct(
    val id: Int,
    val title: String,
    val price: Float,
    val imageUrl: String,
)