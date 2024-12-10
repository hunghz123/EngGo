package com.example.enggo.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.enggo.navigation.TopLevelDestination
import com.example.enggo.ui.course.navigation.navigateToCourses
import com.example.enggo.ui.dictionary.navigation.navigateToDictionary
import com.example.enggo.ui.flashcard.navigation.navigateToFlashcard
import com.example.enggo.ui.home.navigation.navigateToHome
import com.example.enggo.ui.profile.navigation.navigateToProfile
import java.time.Instant

@Composable
fun rememberAppState(
    context: Context,
    navController: NavHostController = rememberNavController()
): AppState {
    return AppState(context, navController)
}

const val EXPIRED_TIME = 259200000

@Stable
class AppState(
    private val context: Context,
    val navController: NavHostController
) {
    val sharedPref = context.getSharedPreferences("EngGoApp", Context.MODE_PRIVATE)
    val currentUserId = sharedPref.getString("currentUserId", null)
    val lastLoginTimestamp = sharedPref.getString("session", "0")
    val currentTimestamp = Instant.now().toEpochMilli()
    val isTimeoutSession = (currentTimestamp > (lastLoginTimestamp!!.toLong() + EXPIRED_TIME) || currentUserId == null)


    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param topLevelDestination: The destination the app needs to navigate to.
     */
    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }

            when (topLevelDestination) {
                TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
                TopLevelDestination.DICTIONARY -> navController.navigateToDictionary(topLevelNavOptions)
                TopLevelDestination.COURSES -> navController.navigateToCourses(topLevelNavOptions)
                TopLevelDestination.FOLDER -> navController.navigateToFlashcard(topLevelNavOptions)
                TopLevelDestination.PROFILE -> navController.navigateToProfile(topLevelNavOptions)
            }
        }
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}