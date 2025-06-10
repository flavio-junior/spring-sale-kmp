package br.com.conding.tv.features.product.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import br.com.conding.tv.components.model.DefinitionsScreen
import br.com.conding.tv.components.settings.TypeSystem
import br.com.conding.tv.components.ui.ContentScreen
import br.com.conding.tv.components.ui.HeaderSearch
import br.com.conding.tv.features.product.ui.viewmodel.ProductViewModel
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericsStrings
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal expect fun ProductsScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (AppDestinations) -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {}
)

@Composable
internal fun MobileProductsScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (AppDestinations) -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {}
) {
    val viewModel: ProductViewModel = getKoin().get()
    ContentScreen(
        definitionsScreen = DefinitionsScreen(
            alignment = Alignment.Top,
            enableMiniButton = true
        ),
        goToNextScreen = {

        },
        content = {
            HeaderSearch(
                label = GenericsStrings.PRODUCTS,
                system = TypeSystem.MOBILE,
                onSearch = { name, size, sort, route ->
                    viewModel.findAllProducts(
                        name = name,
                        size = size,
                        sort = sort,
                        route = route
                    )
                },
                onSort = { name, size, sort, route ->
                    viewModel.findAllProducts(
                        name = name,
                        size = size,
                        sort = sort,
                        route = route
                    )
                },
                onFilter = { name, size, sort, route ->
                    viewModel.findAllProducts(
                        name = name,
                        size = size,
                        sort = sort,
                        route = route
                    )
                },
                onRefresh = { name, size, sort, route ->
                    viewModel.findAllProducts(
                        name = name,
                        size = size,
                        sort = sort,
                        route = route
                    )
                }
            )
            MobileListProducts(viewModel = viewModel)
        }
    )
}
