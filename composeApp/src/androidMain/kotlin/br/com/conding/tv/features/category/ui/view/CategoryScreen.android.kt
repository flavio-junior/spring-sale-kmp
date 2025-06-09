package br.com.conding.tv.features.category.ui.view

import androidx.compose.runtime.Composable
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.networking.resources.AlternativesRoutes

@Composable
internal actual fun CategoriesScreen(
    goToBackScreen: () -> Unit,
    goToNextScreen: (AppDestinations) -> Unit,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit
) {
    MobileCategoryScreen(goToBackScreen, goToNextScreen, goToAlternativeRoutes)
}
