package com.example.enggo.ui.course.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.enggo.ui.course.CourseRoute

const val COURSES_ROUTE = "course"

fun NavController.navigateToCourses(navOptions: NavOptions? = null) {
    navigate(COURSES_ROUTE, navOptions)
}

fun NavGraphBuilder.coursesScreen(
    onCourseClick: (Int) -> Unit,
) {
    composable(COURSES_ROUTE) {
        CourseRoute(onCourseClick)
    }
}