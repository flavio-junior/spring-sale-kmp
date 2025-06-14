package br.com.conding.tv.features.product.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import br.com.conding.tv.features.product.ui.view.CreateNewProductScreen
import br.com.conding.tv.features.product.ui.view.ProductDetailsScreen
import br.com.conding.tv.features.product.ui.view.ProductsScreen
import br.com.conding.tv.features.product.ui.view.UpdateProductScreen
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.navigation.fromJson
import br.com.conding.tv.navigation.goBack
import br.com.conding.tv.navigation.navigateToAlternativeRoutes

internal fun NavGraphBuilder.productNavigation(
    navController: NavHostController
) {
    composable<AppDestinations.Products> {
        ProductsScreen(
            goToBackScreen = {
                navController.goBack()
            },
            goToNextScreen = {
                navController.navigate(route = it) {
                    popUpTo(route = AppDestinations.Products) {
                        inclusive = true
                    }
                }
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.Products,
                    alternativeRoutes = it
                )
            }
        )
    }

    composable<AppDestinations.CreateNewProduct> {
        CreateNewProductScreen(
            goToBackScreen = {
                navController.goBack()
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.CreateNewProduct,
                    alternativeRoutes = it
                )
            }
        )
    }

    composable<AppDestinations.ProductDetails> { backStackEntry ->
        val route = backStackEntry.toRoute<AppDestinations.ProductDetails>()
        ProductDetailsScreen(
            goToBackScreen = {
                navController.goBack()
            },
            productResponseVO = route.data.fromJson(),
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.ProductDetails,
                    alternativeRoutes = it
                )
            },
            goToNextScreen = {
                navController.navigate(route = it)
            }
        )
    }

    composable<AppDestinations.UpdateProduct> { backStackEntry ->
        UpdateProductScreen(
            id = backStackEntry.toRoute<AppDestinations.UpdateProduct>().id,
            goToBackScreen = {
                navController.goBack()
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.UpdateProduct,
                    alternativeRoutes = it
                )
            },
            onSuccessful = {
                navController.navigate(route = AppDestinations.Home)
            }
        )
    }
}
