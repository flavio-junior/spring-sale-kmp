package br.com.conding.tv.features.product.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.ui.EmptyList
import br.com.conding.tv.components.ui.HeaderSearch
import br.com.conding.tv.features.product.data.vo.ProductResponseVO
import br.com.conding.tv.features.product.ui.viewmodel.ProductViewModel
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericsStrings.CREATE_PRODUCT
import br.com.conding.tv.resources.GenericsStrings.EMPTY_LIST_PRODUCTS
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE_4
import br.com.conding.tv.theme.Themes
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun DesktopFindAllProductsScreen(
    onItemSelected: (ProductResponseVO) -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {},
    goToCreateNewProduct: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(
                top = Themes.size.spaceSize16,
                bottom = Themes.size.spaceSize16,
                end = Themes.size.spaceSize16
            )
            .fillMaxSize()
    ) {
        val viewModel: ProductViewModel = getKoin().get()
        val showEmptyList: Boolean by remember { viewModel.showEmptyList }
        LaunchedEffect(key1 = Unit) {
            viewModel.findAllProducts()
        }
        HeaderSearch(
            filter = { name, size, sort ->
                viewModel.findAllProducts(name = name, size = size, sort = sort)
            }
        )
        UiResponseFindAllProductsScreen(
            viewModel = viewModel,
            goToAlternativeRoutes = goToAlternativeRoutes,
            onSuccess = { response ->
                if (showEmptyList) {
                    EmptyList(
                        title = EMPTY_LIST_PRODUCTS,
                        description = "$CREATE_PRODUCT?",
                        onClick = goToCreateNewProduct,
                        refresh = {
                            viewModel.findAllProducts()
                        }
                    )
                } else {
                    ProductsResult(
                        productsResponseVO = response,
                        showDesktopScreen = { result ->
                            Column {
                                DesktopListProducts(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(weight = WEIGHT_SIZE_4)
                                        .padding(top = Themes.size.spaceSize12),
                                    productsResponseVO = result,
                                    onItemSelected = onItemSelected,
                                    showEmptyList = {
                                        viewModel.showEmptyList(show = true)
                                    }
                                )
                                PageIndicatorProducts(productsResponseVO = result)
                            }
                        }
                    )
                }
            }
        )
    }
}
