package br.com.conding.tv.features.product.ui.view

import androidx.compose.runtime.Composable
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.networking.resources.HttpError

@Composable
internal actual fun ProductsScreen(
    goToBackScreen: () -> Unit,
    goToNextScreen: (AppDestinations) -> Unit,
    goToAlternativeRoutes: (HttpError) -> Unit
) {
    MobileProductsScreen(goToBackScreen, goToNextScreen, goToAlternativeRoutes)
}
