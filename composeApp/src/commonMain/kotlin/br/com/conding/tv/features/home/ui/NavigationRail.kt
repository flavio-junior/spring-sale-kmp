package br.com.conding.tv.features.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.conding.tv.components.model.BottomNavigationRoute
import br.com.conding.tv.features.category.ui.view.CategoriesScreen
import br.com.conding.tv.features.product.ui.view.ProductsScreen
import br.com.conding.tv.features.settings.ui.view.SettingsScreen

@Composable
fun NavigationRail(
    modifier: Modifier,
    navController: NavHostController,
    navGraph: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationRoute.Categories.route.name,
        modifier = modifier
    ) {
        composable(route = BottomNavigationRoute.Categories.route.name) {
            CategoriesScreen()
        }
        composable(route = BottomNavigationRoute.Products.route.name) {
            ProductsScreen()
        }
        composable(route = BottomNavigationRoute.Settings.route.name) {
            SettingsScreen(
                goToNextScreen = {
                    navGraph.navigate(route = it) {
                        popUpTo(route = BottomNavigationRoute.Settings.route.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
