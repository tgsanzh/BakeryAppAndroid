package com.example.bakeryapp.catalog.data

import kotlinx.serialization.Serializable

@Serializable
data class ProductsDTO(
    val product_id: Int,
    val name: String,
    val description: String,
    val price: Float,
    val image_url: String,
    val category_id: Int,
)

@Serializable
data class CategoriesDTO(
    val category_id: Int,
    val name: String,
)

@Serializable
data class CartRequest(
    val product_id: Int,
)

@Serializable
data class CartQuantityChange(
    val action: String,
)

@Serializable
data class OrderData(
    val delivery_address: String,
)