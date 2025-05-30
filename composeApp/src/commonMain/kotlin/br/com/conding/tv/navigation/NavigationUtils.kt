package br.com.conding.tv.navigation

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import br.com.conding.tv.networking.resources.AlternativesRoutes

fun NavController.goBack() = this.navigateUp()

fun navigateToAlternativeRoutes(
    navController: NavHostController,
    currentScreen: Any,
    alternativeRoutes: AlternativesRoutes?
) {
    when (alternativeRoutes) {
        AlternativesRoutes.ERROR_403 -> {
            navController.navigate(route = AppDestinations.SignIn) {
                popUpTo(route = currentScreen::class) {
                    inclusive = true
                }
            }
        }

        else -> {

        }
    }
}
