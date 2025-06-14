package br.com.conding.tv.features.category.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.conding.tv.features.category.ui.view.CategoriesScreen
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.navigation.goBack
import br.com.conding.tv.navigation.navigateToAlternativeRoutes

internal fun NavGraphBuilder.categoryNavigation(
    navController: NavHostController
) {
    composable<AppDestinations.Categories> {
        CategoriesScreen(
            goToBackScreen = {
                navController.goBack()
            },
            goToNextScreen = {
                navController.navigate(route = it) {
                    popUpTo(route = AppDestinations.Categories) {
                        inclusive = true
                    }
                }
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.Categories,
                    alternativeRoutes = it
                )
            }
        )
    }
}
