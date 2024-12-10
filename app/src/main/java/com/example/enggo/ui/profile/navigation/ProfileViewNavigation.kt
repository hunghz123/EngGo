package com.example.enggo.ui.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.enggo.ui.profile.ProfileViewRoute

const val PROFILE_VIEW_ROUTE = "profile_view"

fun NavController.navigateToProfileView(navOptions: NavOptions? = null) {
    navigate(PROFILE_VIEW_ROUTE, navOptions)
}

fun NavGraphBuilder.profileViewScreen(
    onBackClick: () -> Unit
) {
    composable(PROFILE_VIEW_ROUTE) {
        ProfileViewRoute(onBackClick)
    }
}