package br.com.conding.tv.features.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.conding.tv.navigation.AppDestinations

internal fun NavGraphBuilder.splashScreenNavigation(
    navController: NavHostController
) {
    composable<AppDestinations.SplashScreen> {
        SplashScreen(
            goToSignInScreen = {
                navController.navigate(route = AppDestinations.SignIn) {
                    popUpTo(route = AppDestinations.SplashScreen::class) {
                        inclusive = true
                    }
                }
            },
            goToHomeScreen = {
                navController.navigate(route = AppDestinations.Home) {
                    popUpTo(route = AppDestinations.SplashScreen::class) {
                        inclusive = true
                    }
                }
            }
        )
    }
}
