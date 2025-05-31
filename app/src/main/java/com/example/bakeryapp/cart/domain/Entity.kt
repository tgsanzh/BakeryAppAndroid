package com.example.bakeryapp.cart.domain

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