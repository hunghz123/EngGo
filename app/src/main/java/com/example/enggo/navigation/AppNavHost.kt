package com.example.enggo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import com.example.enggo.data.DefaultAppContainer
import com.example.enggo.ui.course.navigation.coursesScreen
import com.example.enggo.ui.course.navigation.navigateToCourses
import com.example.enggo.ui.dictionary.navigation.dictionaryScreen
import com.example.enggo.ui.unit.navigation.navigateToUnitList
import com.example.enggo.ui.unit.navigation.unitListScreen
import com.example.enggo.ui.home.navigation.HOME_ROUTE
import com.example.enggo.ui.home.navigation.homeScreen
import com.example.enggo.ui.home.navigation.navigateToHome
import com.example.enggo.ui.lesson.navigation.exerciseScreens
import com.example.enggo.ui.lesson.navigation.lessonScreen
import com.example.enggo.ui.lesson.navigation.navigateToExerciseScreen
import com.example.enggo.ui.lesson.navigation.navigateToLesson
import com.example.enggo.ui.login.navigation.LOGIN_ROUTE
import com.example.enggo.ui.login.navigation.loginScreen
import com.example.enggo.ui.login.navigation.navigateToLogin
import com.example.enggo.ui.navigation.AppState
import com.example.enggo.ui.profile.navigation.AccountManagementScreen
import com.example.enggo.ui.profile.navigation.ChangePasswordScreen
import com.example.enggo.ui.profile.navigation.navigateToPasswordChange
import com.example.enggo.ui.profile.navigation.navigateToProfile
import com.example.enggo.ui.profile.navigation.navigateToProfileAccount
import com.example.enggo.ui.profile.navigation.navigateToProfileView
import com.example.enggo.ui.profile.navigation.profileScreen
import com.example.enggo.ui.profile.navigation.profileViewScreen
import com.example.enggo.ui.register.navigation.navigateToRegister
import com.example.enggo.ui.register.navigation.registerScreen

@Composable
fun AppNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
    startDestination: String = if (appState.isTimeoutSession) LOGIN_ROUTE else HOME_ROUTE,
) {
    val navController = appState.navController
    val context = LocalContext.current
    val appContainer = DefaultAppContainer(context)
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        homeScreen()
        coursesScreen(onCourseClick = navController::navigateToUnitList)
        unitListScreen(onBackPressed = navController::popBackStack, onLessonPressed = navController::navigateToLesson)
        lessonScreen(
            onBackPressed = navController::popBackStack,
            onGoToExercise = { lessonId -> navController.navigateToExerciseScreen(lessonId, 0) } // first exercise
        )
        exerciseScreens(onBackPressed = navController::navigateToCourses, onNextExercisePressed = navController::navigateToExerciseScreen) // TODO: backPressed
        registerScreen (onRegisterClick = navController::navigateToLogin, redirectToLogin = navController::navigateToLogin)
        loginScreen (onLoginClick = navController::navigateToHome , redirectToRegister = navController::navigateToRegister)
        dictionaryScreen(appContainer)
        AccountManagementScreen (onPasswordChangeClick = navController::navigateToPasswordChange, onBackClick = navController::navigateToProfile, onLogoutClick = navController::navigateToLogin)
        ChangePasswordScreen (onBackClick = navController::navigateToProfileAccount)
        profileScreen (onLogoutClick = navController::navigateToLogin, onClickProfile = navController::navigateToProfileView, onClickAccount = navController::navigateToProfileAccount)
        profileViewScreen (onBackClick = navController::navigateToProfile)
    }
}