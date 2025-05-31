package com.example.bakeryapp.catalog.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bakeryapp.catalog.data.CatalogRepository
import com.example.bakeryapp.catalog.domain.Category
import com.example.bakeryapp.catalog.domain.Product
import com.example.bakeryapp.catalog.domain.toCategoryEntity
import com.example.bakeryapp.catalog.domain.toProductEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CatalogViewModel(
    val repository: CatalogRepository
) : ViewModel() {
    val stateFlow = MutableStateFlow(
        CatalogState(
            search = "",
            category = -1,
            categories = listOf(),
            products = listOf(),
            productsLoaded = false,
            categoryLoaded = false,
        )
    )

    fun dispatch(event: CatalogEvent) {
        when (event) {
            is CatalogEvent.onCatalogChanged -> {
                if (stateFlow.value.category == event.value) {
                    stateFlow.value = stateFlow.value.copy(category = -1)

                } else {
                    stateFlow.value = stateFlow.value.copy(category = event.value)
                }

            }

            is CatalogEvent.onSearchChanged -> {
                stateFlow.value = stateFlow.value.copy(search = event.value)
            }

            is CatalogEvent.onOrder -> {
                viewModelScope.launch {
                    try {
                        repository.toCart(event.value)
                    } catch (e: Exception) {

                    }
                }
            }

            CatalogEvent.loadCatalog -> {
                loadCatalog()
            }

            CatalogEvent.loadCategory -> {
                loadCategories()
            }
        }
    }

    fun loadCatalog() {
        viewModelScope.launch {
            try {
                val response: List<Product> = repository.loadCatalog().toProductEntity()
                stateFlow.value = stateFlow.value.copy(products = response)
            } catch (e: Exception) {

            }
            stateFlow.value = stateFlow.value.copy(productsLoaded = true)

        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            try {
                val response: List<Category> = repository.loadCategories().toCategoryEntity()
                stateFlow.value = stateFlow.value.copy(categories = response)
            } catch (e: Exception) {

            }
            stateFlow.value = stateFlow.value.copy(categoryLoaded = true)
        }
    }

}