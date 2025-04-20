package com.example.bakeryapp.catalog.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.bakeryapp.catalog.data.CatalogRepository
import com.example.bakeryapp.catalog.data.ProductsDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CatalogViewModel(
    val repository: CatalogRepository
): ViewModel() {
    val stateFlow = MutableStateFlow(
        CatalogState(
            search = "",
            category = -1,
            categories = listOf(),
            products = listOf()
        )
    )

    fun dispatch(event: CatalogEvent, navController: NavController) {
        when (event) {
            is CatalogEvent.onCatalogChanged -> {
                if (stateFlow.value.category == event.value)
                {
                    stateFlow.value = stateFlow.value.copy(category = -1)

                }
                else
                {
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
                    }
                    catch (e: Exception) {

                    }
                }
            }

            CatalogEvent.loadCatalog -> { loadCatalog() }
            CatalogEvent.loadCategory -> { loadCategories() }
        }
    }

    fun loadCatalog() {
        viewModelScope.launch {
            try {
                val response: List<Product> = repository.loadCatalog().map {
                    Product(
                        id = it.product_id,
                        title = it.name + " - " + it.description,
                        category_id = it.category_id,
                        imageUrl = it.image_url,
                        price = it.price,
                    )
                }
                stateFlow.value = stateFlow.value.copy(products = response)
            }
            catch (e: Exception) {

            }
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            try {
                val response: List<Category> = repository.loadCategories().map {
                    Category(
                        id = it.category_id,
                        name = it.name,
                    )
                }
                stateFlow.value = stateFlow.value.copy(categories = response)
            }
            catch (e: Exception) {

            }
        }
    }

}