package br.com.conding.tv.features.product.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import br.com.conding.tv.components.model.DefinitionsScreen
import br.com.conding.tv.components.settings.TypeSystem
import br.com.conding.tv.components.ui.ContentScreen
import br.com.conding.tv.components.ui.HeaderSearch
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.resources.GenericsStrings

@Composable
expect fun ProductsScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (AppDestinations) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
)

@Composable
fun MobileProductsScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (AppDestinations) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    ContentScreen(
        definitionsScreen = DefinitionsScreen(
            alignment = Alignment.Top
        ),
        content = {
            HeaderSearch(
                label = GenericsStrings.PRODUCTS,
                system = TypeSystem.MOBILE,
                onSearch = { name, size, sort, route ->
                },
                onSort = { name, size, sort, route ->
                },
                onFilter = { name, size, sort, route ->
                },
                onRefresh = { name, size, sort, route ->
                }
            )
        }
    )
}
