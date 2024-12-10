package com.example.enggo.ui.dictionary.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.enggo.data.AppContainer
import com.example.enggo.ui.dictionary.DictionaryRoute

const val DICTIONARY_ROUTE = "dictionary"

fun NavController.navigateToDictionary(navOptions: NavOptions? = null) {
    navigate(DICTIONARY_ROUTE, navOptions)
}

fun NavGraphBuilder.dictionaryScreen(appContainer: AppContainer) {
    composable(DICTIONARY_ROUTE) {
        DictionaryRoute(appContainer = appContainer)
    }
}