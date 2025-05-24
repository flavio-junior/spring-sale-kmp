package br.com.conding.tv.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.conding.tv.ui.home.HomeScreen
import br.com.conding.tv.ui.account.SignInScreen

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestinations.SignIn.item
) {
    NavHost(navController = navController, startDestination = startDestination) {
        signInNavigation(navController = navController)
        homeNavigation(navController = navController)
        categoryNavigation(navController = navController)
        productNavigation(navController = navController)
        settingsNavigation(navController = navController)
    }
}

fun NavGraphBuilder.signInNavigation(
    navController: NavHostController
) {
    composable(route = AppDestinations.SignIn.item) {
        SignInScreen(
            goToDashboardScreen = {
                navController.navigate(route = AppDestinations.HOME.item) {
                    popUpTo(AppDestinations.SignIn.item) {
                        inclusive = true
                    }
                }
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.SignIn.item,
                    alternativeRoutes = it
                )
            }
        )
    }
}

fun NavGraphBuilder.homeNavigation(
    navController: NavHostController
) {
    composable(route = AppDestinations.HOME.item) {
        HomeScreen(
            goToNextScreen = {
                navController.navigate(route = it)
            }
        )
    }
}

fun NavGraphBuilder.categoryNavigation(
    navController: NavHostController
) {
    composable(route = AppDestinations.Category.item) {
        HomeScreen()
    }
}

fun NavGraphBuilder.productNavigation(
    navController: NavHostController
) {
    composable(route = AppDestinations.Product.item) {
        HomeScreen()
    }
}

fun NavGraphBuilder.settingsNavigation(
    navController: NavHostController
) {
    composable(route = AppDestinations.Settings.item) {
        HomeScreen()
    }
}
