package com.example.bakeryapp.catalog.presentation

sealed interface CatalogEvent {
    data class onSearchChanged(
        val value: String
    ) : CatalogEvent

    data class onCatalogChanged(
        val value: Int
    ) : CatalogEvent

    data class onOrder(
        val value: Int
    ) : CatalogEvent

    object loadCatalog : CatalogEvent
    object loadCategory : CatalogEvent
}