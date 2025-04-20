package com.example.bakeryapp.catalog.data

import io.ktor.client.HttpClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface CatalogRepository {
    suspend fun loadCatalog(): List<ProductsDTO>
    suspend fun loadCategories(): List<CategoriesDTO>
    suspend fun toCart(product_id: Int)
}