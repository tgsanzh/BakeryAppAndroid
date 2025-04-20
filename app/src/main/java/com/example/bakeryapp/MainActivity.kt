package com.example.bakeryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bakeryapp.login.presentation.LoginScreen
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bakeryapp.cart.presentation.CartScreen
import com.example.bakeryapp.cart.presentation.CartViewModel
import com.example.bakeryapp.catalog.presentation.CatalogEvent
import com.example.bakeryapp.catalog.presentation.CatalogScreen
import com.example.bakeryapp.catalog.presentation.CatalogViewModel
import com.example.bakeryapp.confirm.presentation.ConfirmScreen
import com.example.bakeryapp.confirm.presentation.ConfirmViewModel
import com.example.bakeryapp.login.presentation.LoginViewModel
import com.example.bakeryapp.orders.presentation.OrdersEvent
import com.example.bakeryapp.orders.presentation.OrdersScreen
import com.example.bakeryapp.orders.presentation.OrdersViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
            val isBottomBarVisible =
                currentDestination == "catalog" ||
                currentDestination == "cart" ||
                currentDestination == "orders"

            AppTheme {
                Scaffold(
                    bottomBar = {
                        if (isBottomBarVisible)
                            BottomNavBar(navController)
                    }
                ) { paddingValues ->
                    NavHost(
                        navController,
                        startDestination = "login",
                        modifier = Modifier
                            .background(color = appColors.background)
                            .padding(paddingValues)
                    ) {
                        composable("start") {

                        }
                        composable("login") {
                            val vm: LoginViewModel = koinViewModel()
                            val state by vm.stateFlow.collectAsState()
                            LoginScreen(state) {
                                vm.dispatch(it, navController)
                            }
                        }
                        composable("confirm/{number}") {
                            val number = it.arguments?.getString("number") ?: ""
                            val vm: ConfirmViewModel = koinViewModel()
                            val state by vm.stateFlow.collectAsState()

                            ConfirmScreen(number, state) {
                                vm.dispatch(it, navController)
                            }
                        }
                        composable("catalog") {
                            val vm: CatalogViewModel = koinViewModel()
                            val state by vm.stateFlow.collectAsState()
                            CatalogScreen(state, navController) {
                                vm.dispatch(it, navController)
                            }
                        }
                        composable("cart") {
                            val vm: CartViewModel = koinViewModel()
                            val state by vm.stateFlow.collectAsState()

                            CartScreen(state, navController) {
                                vm.dispatch(it, navController)
                            }
                        }
                        composable("orders") {
                            val vm: OrdersViewModel = koinViewModel()
                            val state by vm.stateFlow.collectAsState()

                            OrdersScreen(state, navController) {
                                vm.dispatch(it, navController)
                            }
                        }
                    }
                }
            }
        }
    }
}
