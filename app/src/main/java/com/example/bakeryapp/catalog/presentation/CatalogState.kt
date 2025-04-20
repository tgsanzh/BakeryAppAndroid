package com.example.bakeryapp.catalog.presentation

data class CatalogState(
    val search: String,
    val category: Int,
    val categories: List<Category>,
    val products: List<Product>,
)

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