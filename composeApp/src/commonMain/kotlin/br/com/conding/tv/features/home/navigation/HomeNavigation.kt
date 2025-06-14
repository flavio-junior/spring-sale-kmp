package br.com.conding.tv.features.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.conding.tv.features.home.ui.HomeScreen
import br.com.conding.tv.navigation.AppDestinations

internal fun NavGraphBuilder.homeNavigation(
    navController: NavHostController
) {
    composable<AppDestinations.Home> {
        HomeScreen(
            navGraph = navController,
            goToNextScreen = {
                navController.navigate(route = it)
            },
            goToLoginScreen = {
                navController.navigate(route = AppDestinations.SplashScreen) {
                    popUpTo(route = AppDestinations.Home) {
                        inclusive = true
                    }
                }
            }
        )
    }
}
