package com.example.bakeryapp.catalog.data

interface CatalogRepository {
    suspend fun loadCatalog(): Result<List<ProductsDTO>>
    suspend fun loadCategories(): Result<List<CategoriesDTO>>
    suspend fun toCart(product_id: Int): Result<Int>
}