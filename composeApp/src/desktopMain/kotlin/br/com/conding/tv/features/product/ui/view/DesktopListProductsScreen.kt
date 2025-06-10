package br.com.conding.tv.features.product.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.conding.tv.components.ui.EmptyList
import br.com.conding.tv.components.ui.HeaderSearch
import br.com.conding.tv.components.ui.UiResponse
import br.com.conding.tv.components.ui.UiState
import br.com.conding.tv.features.product.data.vo.ProductResponseVO
import br.com.conding.tv.features.product.data.vo.ProductsResponseVO
import br.com.conding.tv.features.product.ui.viewmodel.ProductViewModel
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericsStrings.CREATE_PRODUCT
import br.com.conding.tv.resources.GenericsStrings.EMPTY_LIST_PRODUCTS
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE_4
import br.com.conding.tv.theme.Themes
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun DesktopListProductsScreen(
    onItemSelected: (ProductResponseVO) -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {},
    onToCreateNewProduct: () -> Unit = {}
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
        LaunchedEffect(key1 = Unit) {
            viewModel.findAllProducts()
        }
        HeaderSearch(
            filter = { name, size, sort ->
                viewModel.findAllProducts(name = name, size = size, sort = sort)
            }
        )
        UiResponseListProductsScreen(
            viewModel = viewModel,
            onItemSelected = onItemSelected,
            onToCreateNewProduct = onToCreateNewProduct,
            goToAlternativeRoutes = goToAlternativeRoutes
        )
    }
}

@Composable
private fun UiResponseListProductsScreen(
    viewModel: ProductViewModel,
    onItemSelected: (ProductResponseVO) -> Unit = {},
    onToCreateNewProduct: () -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {}
) {
    val state: UiState<ProductsResponseVO> by viewModel.findAllProducts.collectAsStateWithLifecycle()
    val showEmptyList: Boolean by remember { viewModel.showEmptyList }
    UiResponse(
        state = state,
        onError = {
            Triple(first = true, second = false, third = it)
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            if (showEmptyList) {
                EmptyList(
                    title = EMPTY_LIST_PRODUCTS,
                    description = "$CREATE_PRODUCT?",
                    onClick = onToCreateNewProduct,
                    refresh = {
                        viewModel.findAllProducts()
                    }
                )
            } else {
                DesktopProductsResult(
                    productsResponseVO = it,
                    onItemSelected = onItemSelected,
                    showEmptyList = {
                        viewModel.showEmptyList(show = true)
                    }
                )
            }
        }
    )
}

@Composable
private fun DesktopProductsResult(
    productsResponseVO: ProductsResponseVO? = null,
    onItemSelected: (ProductResponseVO) -> Unit = {},
    showEmptyList: () -> Unit = {}
) {
    Column {
        DesktopListProducts(
            modifier = Modifier
                .fillMaxSize()
                .weight(weight = WEIGHT_SIZE_4)
                .padding(top = Themes.size.spaceSize12),
            products = productsResponseVO,
            onItemSelected = onItemSelected,
            showEmptyList = showEmptyList
        )
        PageIndicatorProducts(productsResponseVO = productsResponseVO)
    }
}
