package br.com.conding.tv.features.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.conding.tv.features.settings.ui.view.EditProfileUserScreen
import br.com.conding.tv.features.settings.ui.view.SettingsScreen
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.navigation.goBack
import br.com.conding.tv.navigation.navigateToAlternativeRoutes

internal fun NavGraphBuilder.settingsNavigation(
    navController: NavHostController
) {
    composable<AppDestinations.Settings> {
        SettingsScreen(
            goToBackScreen = {
                navController.goBack()
            },
            goToNextScreen = {
                navController.navigate(route = it) {
                    popUpTo(route = AppDestinations.Settings) {
                        inclusive = true
                    }
                }
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.Settings,
                    alternativeRoutes = it
                )
            }
        )
    }

    composable<AppDestinations.EditProfileUser> {
        EditProfileUserScreen(
            goToBackScreen = {
                navController.goBack()
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.EditProfileUser,
                    alternativeRoutes = it
                )
            },
            onSuccess = {
                navController.navigate(route = AppDestinations.SplashScreen)
            }
        )
    }
}
