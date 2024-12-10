package com.example.enggo.ui.register.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.enggo.ui.register.RegisterRoute

const val REGISTER_ROUTE = "register"

fun NavController.navigateToRegister(navOptions: NavOptions? = null) {
    navigate(REGISTER_ROUTE, navOptions)
}

fun NavGraphBuilder.registerScreen(
    onRegisterClick: () -> Unit,
    redirectToLogin: () -> Unit
) {
    composable(REGISTER_ROUTE) {
        RegisterRoute(onRegisterClick, redirectToLogin)
    }
}