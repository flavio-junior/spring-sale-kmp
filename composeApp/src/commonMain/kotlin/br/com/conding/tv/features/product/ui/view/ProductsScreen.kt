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
import br.com.conding.tv.resources.GenericStrings
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
            enableBottomBar = true,
            enableMiniButton = true
        ),
        goToNextScreen = {
            goToNextScreen(AppDestinations.CreateNewProduct)
        },
        content = {
            HeaderSearch(
                label = GenericStrings.PRODUCTS,
                system = TypeSystem.MOBILE,
                filter = { name, size, sort ->
                    viewModel.findAllProducts(name = name, size = size, sort = sort)
                }
            )
            MobileListProducts(
                viewModel = viewModel,
                goToNextScreen = goToNextScreen
            )
        },
        bottomBar = {
            PageIndicatorProducts()
        }
    )
}
