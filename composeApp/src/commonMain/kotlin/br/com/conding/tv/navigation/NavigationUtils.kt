package br.com.conding.tv.navigation

import androidx.navigation.NavHostController
import br.com.conding.tv.networking.resources.AlternativesRoutes

fun goToNextScreen(
    navHostController: NavHostController,
    currentScreen: String,
    nextScreen: String
) {
    navHostController.navigate(route = nextScreen) {
        popUpTo(currentScreen) {
            inclusive = true
        }
    }
}

fun navigateToAlternativeRoutes(
    navController: NavHostController,
    currentScreen: String,
    alternativeRoutes: AlternativesRoutes?
) {
    when (alternativeRoutes) {
        AlternativesRoutes.ERROR_403 -> {
            navController.navigate(route = AppDestinations.SignIn.item) {
                popUpTo(currentScreen) {
                    inclusive = true
                }
            }
        }

        else -> {

        }
    }
}
