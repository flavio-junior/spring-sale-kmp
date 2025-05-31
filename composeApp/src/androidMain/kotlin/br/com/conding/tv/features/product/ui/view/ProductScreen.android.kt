package br.com.conding.tv.features.product.ui.view

import androidx.compose.runtime.Composable
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.networking.resources.AlternativesRoutes

@Composable
actual fun ProductsScreen(
    goToBackScreen: () -> Unit,
    goToNextScreen: (AppDestinations) -> Unit,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit
) {
    MobileProductsScreen(goToBackScreen, goToNextScreen, goToAlternativeRoutes)
}
