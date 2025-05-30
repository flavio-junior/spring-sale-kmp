package br.com.conding.tv.features.category.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import br.com.conding.tv.components.model.DefinitionsScreen
import br.com.conding.tv.components.ui.ContentScreen
import br.com.conding.tv.components.ui.Title
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.networking.resources.AlternativesRoutes

@Composable
actual fun CategoriesScreen(
    goToBackScreen: () -> Unit,
    goToNextScreen: (AppDestinations) -> Unit,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit
) {
    ContentScreen(
        definitionsScreen = DefinitionsScreen(
            alignment = Alignment.Top
        ),
        content = {
            Title(title = "Categories")
        }
    )
}
