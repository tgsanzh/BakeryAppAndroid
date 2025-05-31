package com.example.bakeryapp.catalog.presentation

import com.example.bakeryapp.catalog.domain.Category
import com.example.bakeryapp.catalog.domain.Product

data class CatalogState(
    val search: String,
    val category: Int,
    val categories: List<Category>,
    val products: List<Product>,
    val productsLoaded: Boolean,
    val categoryLoaded: Boolean,
)

