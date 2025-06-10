package br.com.conding.tv.navigation

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import br.com.conding.tv.networking.resources.HttpError

internal fun NavController.goBack() = this.navigateUp()

internal fun navigateToAlternativeRoutes(
    navController: NavHostController,
    currentScreen: Any,
    alternativeRoutes: HttpError
) {
    when (alternativeRoutes) {
        HttpError.ERROR_403 -> {
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
