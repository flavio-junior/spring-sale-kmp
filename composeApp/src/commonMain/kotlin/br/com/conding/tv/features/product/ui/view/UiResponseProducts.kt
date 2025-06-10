package br.com.conding.tv.features.product.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.conding.tv.components.ui.UiResponse
import br.com.conding.tv.components.ui.UiState
import br.com.conding.tv.features.product.data.vo.ProductsResponseVO
import br.com.conding.tv.features.product.ui.viewmodel.ProductViewModel
import br.com.conding.tv.features.product.ui.viewmodel.ResetProduct
import br.com.conding.tv.networking.resources.AlternativesRoutes

@Composable
internal fun UiResponseFindAllProductsScreen(
    viewModel: ProductViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccess: @Composable (ProductsResponseVO?) -> Unit = {}
) {
    val state: UiState<ProductsResponseVO> by viewModel.findAllProducts.collectAsStateWithLifecycle()
    UiResponse(
        state = state,
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onSuccess(it)
        }
    )
}

@Composable
internal fun UiResponseCreateNewProductScreen(
    viewModel: ProductViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: UiState<Unit> by viewModel.createProduct.collectAsStateWithLifecycle()
    UiResponse(
        state = state,
        onLoading = {},
        onError = onError,
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            viewModel.resetProduct(reset = ResetProduct.CREATE_PRODUCT)
            onSuccessful()
        }
    )
}

@Composable
internal fun UiResponseUpdateProductScreen(
    viewModel: ProductViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: UiState<Unit> by viewModel.updateProduct.collectAsStateWithLifecycle()
    UiResponse(
        state = state,
        onLoading = {},
        onError = onError,
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onSuccessful()
            viewModel.findAllProducts()
        }
    )
}

@Composable
internal fun UiResponseUpdatePriceProductScreen(
    viewModel: ProductViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val uiState: UiState<Unit> by viewModel.updatePriceProduct.collectAsStateWithLifecycle()
    UiResponse(
        state = uiState,
        onLoading = {},
        onError = onError,
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            viewModel.resetProduct(reset = ResetProduct.UPDATE_PRICE_PRODUCT)
            onSuccessful()
        }
    )
}

@Composable
internal fun UiResponseRestockProductScreen(
    viewModel: ProductViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: UiState<Unit> by viewModel.restockProduct.collectAsStateWithLifecycle()
    UiResponse(
        state = state,
        onLoading = {},
        onError = onError,
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            viewModel.resetProduct(reset = ResetProduct.RESTOCK_PRODUCT)
            onSuccessful()
        }
    )
}

@Composable
internal fun UiResponseDeleteProductScreen(
    viewModel: ProductViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: UiState<Unit> by viewModel.deleteProduct.collectAsStateWithLifecycle()
    UiResponse(
        state = state,
        onLoading = {},
        onError = onError,
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onSuccessful()
            viewModel.findAllProducts()
        }
    )
}
