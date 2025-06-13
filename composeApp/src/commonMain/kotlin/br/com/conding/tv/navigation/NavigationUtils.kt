package br.com.conding.tv.navigation

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import br.com.conding.tv.networking.resources.HttpError
import kotlinx.serialization.json.Json

internal inline fun <reified T> T.toJson(): String = Json.encodeToString(value = this)
internal inline fun <reified T> String.fromJson(): T = Json.decodeFromString<T>(string = this)

internal fun NavController.goBack() = this.navigateUp()

internal fun navigateToAlternativeRoutes(
    navController: NavHostController,
    currentScreen: Any,
    alternativeRoutes: HttpError
) {
    when (alternativeRoutes) {
        HttpError.ERROR_401 -> {
            navController.navigate(route = AppDestinations.SplashScreen) {
                popUpTo(route = currentScreen::class) {
                    inclusive = true
                }
            }
        }

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
