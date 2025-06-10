package br.com.conding.tv.features.product.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.settings.TypeSystem
import br.com.conding.tv.components.ui.EmptyList
import br.com.conding.tv.features.product.ui.viewmodel.ProductViewModel
import br.com.conding.tv.resources.GenericsStrings.EMPTY_LIST_PRODUCTS
import br.com.conding.tv.theme.Themes

@Composable
internal fun MobileListProducts(
    viewModel: ProductViewModel
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
                            MobileProductItem(viewModel = viewModel, productResponseVO = it)
                        }
                        PageIndicatorProducts(
                            modifier = Modifier.padding(bottom = Themes.size.spaceSize2),
                            productsResponseVO = response
                        )
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
