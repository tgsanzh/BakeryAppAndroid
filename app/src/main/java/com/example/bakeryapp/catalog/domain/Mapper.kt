package com.example.bakeryapp.catalog.domain

import com.example.bakeryapp.catalog.data.CategoriesDTO
import com.example.bakeryapp.catalog.data.ProductsDTO

fun List<ProductsDTO>.toProductEntity(): List<Product> {
    return this.map {
        Product(
            id = it.product_id,
            title = it.name + " - " + it.description,
            category_id = it.category_id,
            imageUrl = it.image_url,
            price = it.price,
        )
    }
}

fun List<CategoriesDTO>.toCategoryEntity(): List<Category> {
    return this.map {
        Category(
            id = it.category_id,
            name = it.name,
        )
    }
}