package com.example.bakeryapp.catalog.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bakeryapp.catalog.data.CatalogRepository
import com.example.bakeryapp.catalog.domain.Category
import com.example.bakeryapp.catalog.domain.Product
import com.example.bakeryapp.catalog.domain.toCategoryEntity
import com.example.bakeryapp.catalog.domain.toProductEntity
import com.example.bakeryapp.utils.ToastManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CatalogViewModel(
    val repository: CatalogRepository,
    val toastManager: ToastManager
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
            repository.loadCatalog().onSuccess { response ->
                stateFlow.value = stateFlow.value.copy(
                    products = response.toProductEntity(),
                    productsLoaded = true
                )
            }.onFailure {
                toastManager.show("Не удалость загрузить каталог!")
            }
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            repository.loadCategories().onSuccess { response ->
                stateFlow.value = stateFlow.value.copy(
                    categories = response.toCategoryEntity(),
                    categoryLoaded = true
                )
            }.onFailure { error ->
                toastManager.show("Не удалость загрузить категорию!")
            }
        }
    }

}