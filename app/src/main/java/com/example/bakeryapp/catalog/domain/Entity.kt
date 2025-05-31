package com.example.bakeryapp.catalog.domain

data class Product(
    val id: Int,
    val title: String,
    val category_id: Int,
    val imageUrl: String,
    val price: Float,
)

data class Category(
    val id: Int,
    val name: String,
)