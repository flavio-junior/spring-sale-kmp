package br.com.conding.tv.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.conding.tv.features.account.navigation.signInNavigation
import br.com.conding.tv.features.category.navigation.categoryNavigation
import br.com.conding.tv.features.home.navigation.homeNavigation
import br.com.conding.tv.features.product.navigation.productNavigation
import br.com.conding.tv.features.settings.navigation.settingsNavigation
import br.com.conding.tv.features.splash.splashScreenNavigation

@Composable
internal fun Navigation(
    navController: NavHostController = rememberNavController(),
    startDestination: AppDestinations
) {
    NavHost(navController = navController, startDestination = startDestination) {
        splashScreenNavigation(navController = navController)
        signInNavigation(navController = navController)
        homeNavigation(navController = navController)
        categoryNavigation(navController = navController)
        productNavigation(navController = navController)
        settingsNavigation(navController = navController)
    }
}
