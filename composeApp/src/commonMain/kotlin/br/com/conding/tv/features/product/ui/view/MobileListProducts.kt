package br.com.conding.tv.features.product.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import br.com.conding.tv.components.settings.TypeSystem
import br.com.conding.tv.components.ui.EmptyList
import br.com.conding.tv.features.product.ui.viewmodel.ProductViewModel
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.resources.GenericsStrings.EMPTY_LIST_PRODUCTS

@Composable
internal fun MobileListProducts(
    viewModel: ProductViewModel,
    goToNextScreen: (AppDestinations) -> Unit = {}
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.findAllProducts()
    }
    UiResponseFindAllProductsScreen(
        viewModel = viewModel,
        goToAlternativeRoutes = {},
        onSuccess = { onSuccess ->
            ProductsResult(
                productsResponseVO = onSuccess,
                system = TypeSystem.MOBILE,
                showMobileScreen = { response ->
                    if (response?.content?.isNotEmpty() == true) {
                        response.content.forEach {
                            MobileProductItem(
                                productResponseVO = it,
                                goToNextScreen = goToNextScreen
                            )
                        }
                    } else {
                        EmptyList(
                            type = TypeSystem.MOBILE,
                            title = EMPTY_LIST_PRODUCTS
                        )
                    }
                }
            )
        }
    )
}
