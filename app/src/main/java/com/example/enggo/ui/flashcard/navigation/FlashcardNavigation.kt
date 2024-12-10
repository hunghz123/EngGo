package com.example.enggo.ui.flashcard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.enggo.ui.flashcard.FlashcardFolderView
import com.example.enggo.ui.flashcard.FlashcardHomeScreen
import com.example.enggo.ui.flashcard.createFCFolderScreen

const val Flashcard_ROUTE = "Flashcard"

fun NavController.navigateToFlashcard(navOptions: NavOptions? = null) {
    navigate(Flashcard_ROUTE, navOptions)
}

fun NavGraphBuilder.flashcardHome(
    navController : NavController
) {
    composable(Flashcard_ROUTE) {
        FlashcardHomeScreen(navController, 1)
    }
    composable("Flashcard_create") {
        createFCFolderScreen(navController)
    }
    composable("FlashcardReview" + "/{id}") {
        val id = it.arguments?.getString("id")
        FlashcardFolderView(id?:"0")
    }
}
