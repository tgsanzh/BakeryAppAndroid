package com.example.bakeryapp

import com.example.bakeryapp.cart.data.CartRepository
import com.example.bakeryapp.cart.data.CartRepositoryImpl
import com.example.bakeryapp.cart.presentation.CartViewModel
import com.example.bakeryapp.catalog.data.CatalogRepository
import com.example.bakeryapp.catalog.data.CatalogRepositoryImpl
import com.example.bakeryapp.catalog.presentation.CatalogViewModel
import com.example.bakeryapp.login.data.LoginRepository
import com.example.bakeryapp.login.data.LoginRepositoryImpl
import com.example.bakeryapp.login.domain.ValidateNumberUseCase
import com.example.bakeryapp.login.domain.ValidatePasswordUseCase
import com.example.bakeryapp.login.presentation.LoginViewModel
import com.example.bakeryapp.orders.data.OrdersRepository
import com.example.bakeryapp.orders.data.OrdersRepositoryImpl
import com.example.bakeryapp.orders.presentation.OrdersViewModel
import com.example.bakeryapp.register.data.RegisterRepository
import com.example.bakeryapp.register.data.RegisterRepositoryImpl
import com.example.bakeryapp.register.presentation.RegisterViewModel
import com.example.bakeryapp.start.data.StartRepository
import com.example.bakeryapp.start.data.StartRepositoryImpl
import com.example.bakeryapp.start.presentation.StartViewModel
import com.example.bakeryapp.utils.SharedPrefs
import com.example.bakeryapp.utils.ToastManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { provideClient(get()) }
    single { SharedPrefs(androidContext()) }
    single { ValidateNumberUseCase() }
    single { ValidatePasswordUseCase() }
    single { ToastManager(androidContext()) }

    // SPLASH
    viewModel { StartViewModel(get(), get()) }
    single<StartRepository> { StartRepositoryImpl(get()) }

    //LOGIN
    viewModel { LoginViewModel(get(), get(), get(), get()) }
    single<LoginRepository> { LoginRepositoryImpl(get()) }

    //REGISTER
    viewModel { RegisterViewModel(get(), get(), get(), get()) }
    single<RegisterRepository> { RegisterRepositoryImpl(get()) }

    //CATALOG
    viewModel { CatalogViewModel(get(), get()) }
    single<CatalogRepository> { CatalogRepositoryImpl(get()) }

    //CART
    viewModel { CartViewModel(get(), get()) }
    single<CartRepository> { CartRepositoryImpl(get()) }

    //ORDERS
    viewModel { OrdersViewModel(get(), get(), get()) }
    single<OrdersRepository> { OrdersRepositoryImpl(get()) }

}
