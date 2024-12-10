package com.example.enggo.ui.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.enggo.ui.profile.AccountManagementRoute
import com.example.enggo.ui.profile.ChangePasswordRoute

const val PROFILE_ACCOUNT_ROUTE = "account_view"
const val ACCOUNT_PASSWORD_ROUTE = "password_view"

fun NavController.navigateToProfileAccount(navOptions: NavOptions? = null) {
    navigate(PROFILE_ACCOUNT_ROUTE, navOptions)
}

fun NavGraphBuilder.AccountManagementScreen(
    onPasswordChangeClick: () -> Unit,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    composable(PROFILE_ACCOUNT_ROUTE) {
        AccountManagementRoute(onPasswordChangeClick,onBackClick, onLogoutClick)
    }
}

fun NavController.navigateToPasswordChange(navOptions: NavOptions? = null) {
    navigate(ACCOUNT_PASSWORD_ROUTE, navOptions)
}

fun NavGraphBuilder.ChangePasswordScreen(
    onBackClick: () -> Unit,
) {
    composable(ACCOUNT_PASSWORD_ROUTE) {
        ChangePasswordRoute(onBackClick)
    }
}