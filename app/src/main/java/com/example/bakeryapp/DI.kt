package com.example.bakeryapp

import com.example.bakeryapp.cart.data.CartRepository
import com.example.bakeryapp.cart.data.CartRepositoryImpl
import com.example.bakeryapp.cart.presentation.CartViewModel
import com.example.bakeryapp.catalog.data.CatalogRepository
import com.example.bakeryapp.catalog.data.CatalogRepositoryImpl
import com.example.bakeryapp.catalog.presentation.CatalogViewModel
import com.example.bakeryapp.confirm.data.ConfirmRepository
import com.example.bakeryapp.confirm.domain.ConfirmUseCase
import com.example.bakeryapp.confirm.presentation.ConfirmViewModel
import com.example.bakeryapp.login.data.LoginRepository
import com.example.bakeryapp.login.data.LoginRepositoryImpl
import com.example.bakeryapp.login.presentation.LoginViewModel
import com.example.bakeryapp.orders.data.OrdersRepository
import com.example.bakeryapp.orders.domain.OrdersUseCase
import com.example.bakeryapp.orders.presentation.OrdersViewModel
import com.example.bakeryapp.start.data.StartRepository
import com.example.bakeryapp.start.domain.StartUseCase
import com.example.bakeryapp.start.presentation.StartViewModel
import com.example.bakeryapp.utils.SharedPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { privideClient(get()) }
    single { SharedPrefs(androidContext()) }

    viewModel { StartViewModel(get()) }
    single { StartUseCase(get()) }
    single { StartRepository() }

    viewModel { LoginViewModel(get(), get()) }
    single<LoginRepository> { LoginRepositoryImpl(get()) }

    viewModel { ConfirmViewModel(get()) }
    single { ConfirmUseCase(get()) }
    single { ConfirmRepository() }

    viewModel { CatalogViewModel(get()) }
    single<CatalogRepository> { CatalogRepositoryImpl(get(), get()) }

    viewModel { CartViewModel(get()) }

    single<CartRepository> { CartRepositoryImpl(get(), get()) }

    viewModel { OrdersViewModel(get()) }
    single { OrdersUseCase(get()) }
    single { OrdersRepository() }

}