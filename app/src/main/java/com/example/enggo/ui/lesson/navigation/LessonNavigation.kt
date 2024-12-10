package com.example.enggo.ui.lesson.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.enggo.ui.lesson.ExerciseScreen
import com.example.enggo.ui.lesson.LessonRoute
import com.example.enggo.ui.unit.UnitListRoute

const val LESSON_ROUTE = "lesson/{lessonId}"
const val EXERCISE_ROUTE = "exercise/{lessonId}/{exerciseIndex}"

fun NavController.navigateToLesson(
    lessonId: Int,
    navOptions: NavOptions? = null
) {
    Log.d("Navigation", "Navigating to lesson/$lessonId")
    val route = "lesson/$lessonId"
    navigate(route, navOptions)
}

fun NavController.navigateToExerciseScreen(
    lessonId: Int,
    exerciseIndex: Int,
    navOptions: NavOptions? = null
) {
    val route = "exercise/$lessonId/$exerciseIndex"
    navigate(route, navOptions)
}

fun NavGraphBuilder.lessonScreen(
    onBackPressed: () -> Unit,
    onGoToExercise: (Int) -> Unit
) {
    composable(LESSON_ROUTE) { backStackEntry ->
        val lessonId = backStackEntry.arguments?.getString("lessonId")?.toIntOrNull()
        if (lessonId != null) {
            LessonRoute(
                lessonId = lessonId,
                onBackPress = onBackPressed,
                onGoToExercise = onGoToExercise
            )
        } else {
            Log.e("Navigation", "lessonId is null or invalid")
        }
    }
}

fun NavGraphBuilder.exerciseScreens(
    onBackPressed: () -> Unit,
    onNextExercisePressed: (Int, Int) -> Unit,
) {
    composable(EXERCISE_ROUTE) { backStackEntry ->
        val lessonId = backStackEntry.arguments?.getString("lessonId")?.toIntOrNull()
        val exerciseIndex = backStackEntry.arguments?.getString("exerciseIndex")?.toIntOrNull()
        if (lessonId != null && exerciseIndex != null) {
            ExerciseScreen(
                lessonId = lessonId,
                exerciseIndex = exerciseIndex,
                onBackPress = onBackPressed,
                onNextLessonPress = onNextExercisePressed,
                onUnitPress = {}
            )
        } else {
            Log.e("Navigation", "lessonId and exerciseIndex is null or invalid")
        }
    }
}