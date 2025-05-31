package com.example.bakeryapp.catalog.data

interface CatalogRepository {
    suspend fun loadCatalog(): List<ProductsDTO>
    suspend fun loadCategories(): List<CategoriesDTO>
    suspend fun toCart(product_id: Int)
}