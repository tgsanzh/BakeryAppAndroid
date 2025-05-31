package com.example.bakeryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bakeryapp.cart.presentation.CartScreen
import com.example.bakeryapp.cart.presentation.CartViewModel
import com.example.bakeryapp.catalog.presentation.CatalogScreen
import com.example.bakeryapp.catalog.presentation.CatalogViewModel
import com.example.bakeryapp.login.presentation.LoginScreen
import com.example.bakeryapp.login.presentation.LoginViewModel
import com.example.bakeryapp.orders.presentation.OrdersScreen
import com.example.bakeryapp.orders.presentation.OrdersViewModel
import com.example.bakeryapp.register.presentation.RegisterScreen
import com.example.bakeryapp.register.presentation.RegisterViewModel
import com.example.bakeryapp.start.presentation.StartScreen
import com.example.bakeryapp.start.presentation.StartViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            val currentDestination =
                navController.currentBackStackEntryAsState().value?.destination?.route
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
                        startDestination = "start",
                        modifier = Modifier
                            .background(color = appColors.background)
                            .padding(paddingValues)
                    ) {
                        composable("start") {
                            val vm: StartViewModel = koinViewModel()

                            StartScreen() {
                                vm.dispatch(
                                    event = it,
                                    navigate = {
                                        navController.navigate(it) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = false
                                        }
                                    }
                                )
                            }
                        }
                        composable("login") {
                            val vm: LoginViewModel = koinViewModel()
                            val state by vm.stateFlow.collectAsState()
                            LoginScreen(state) {
                                vm.dispatch(it, navController)
                            }
                        }
                        composable("register") {
                            val vm: RegisterViewModel = koinViewModel()
                            val state by vm.stateFlow.collectAsState()
                            RegisterScreen(state) {
                                vm.dispatch(it, navController)
                            }
                        }
                        composable("catalog") {
                            val vm: CatalogViewModel = koinViewModel()
                            val state by vm.stateFlow.collectAsState()
                            CatalogScreen(state, navController) {
                                vm.dispatch(it)
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
