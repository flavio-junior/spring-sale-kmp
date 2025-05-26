package br.com.conding.tv.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.conding.tv.features.home.HomeScreen
import br.com.conding.tv.features.account.ui.SignInScreen
import br.com.conding.tv.features.category.CategoryScreen
import br.com.conding.tv.features.product.ProductScreen
import br.com.conding.tv.features.settings.SettingsScreen

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
                navController.navigate(route = AppDestinations.Home.item) {
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
    composable(route = AppDestinations.Home.item) {
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
        CategoryScreen(
            goToBackScreen = {
                navController.popBackStack()
            },
            goToNextScreen = {
                goToNextScreen(
                    navHostController = navController,
                    currentScreen = AppDestinations.Category.item,
                    nextScreen = it
                )
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.Category.item,
                    alternativeRoutes = it
                )
            }
        )
    }
}

fun NavGraphBuilder.productNavigation(
    navController: NavHostController
) {
    composable(route = AppDestinations.Product.item) {
        ProductScreen(
            goToBackScreen = {
                navController.popBackStack()
            },
            goToNextScreen = {
                goToNextScreen(
                    navHostController = navController,
                    currentScreen = AppDestinations.Product.item,
                    nextScreen = it
                )
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.Product.item,
                    alternativeRoutes = it
                )
            }
        )
    }
}

fun NavGraphBuilder.settingsNavigation(
    navController: NavHostController
) {
    composable(route = AppDestinations.Settings.item) {
        SettingsScreen(
            goToBackScreen = {
                navController.popBackStack()
            },
            goToNextScreen = {
                goToNextScreen(
                    navHostController = navController,
                    currentScreen = AppDestinations.Settings.item,
                    nextScreen = it
                )
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.Settings.item,
                    alternativeRoutes = it
                )
            }
        )
    }
}
