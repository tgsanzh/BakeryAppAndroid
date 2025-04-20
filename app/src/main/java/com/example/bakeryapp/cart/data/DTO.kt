package com.example.bakeryapp.cart.data

import kotlinx.serialization.Serializable

@Serializable
data class CartDTO(
    val cart_item_id: Int,
    val user_id: Int,
    val quantity: Int,
    val Products: ProductsShort,
)

@Serializable
data class ProductsShort(
    val product_id: Int,
    val name: String,
    val price: Float,
    val image_url: String,
)