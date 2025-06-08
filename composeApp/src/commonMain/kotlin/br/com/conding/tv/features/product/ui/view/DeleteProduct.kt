package br.com.conding.tv.features.product.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.conding.tv.components.ui.Alert
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.components.ui.UiResponse
import br.com.conding.tv.components.ui.UiState
import br.com.conding.tv.features.product.ui.viewmodel.ProductViewModel
import br.com.conding.tv.features.product.ui.viewmodel.ResetProduct
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.networking.resources.reloadViewModels
import br.com.conding.tv.resources.GenericsStrings.DELETE_PRODUCT
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun DeleteProduct(
    modifier: Modifier = Modifier,
    id: Long,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit
) {
    val viewModel: ProductViewModel = getKoin().get()
    var openDialog by remember { mutableStateOf(value = false) }
    var observer: Triple<Boolean, Boolean, String?> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    LoadingButton(
        onClick = {
            openDialog = true
        },
        label = DELETE_PRODUCT,
        isEnabled = observer.first,
        modifier = modifier
    )
    if (openDialog) {
        Alert(
            label = "$DELETE_PRODUCT?",
            onDismissRequest = {
                openDialog = false
            },
            onConfirmation = {
                observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                viewModel.deleteProduct(id = id)
                openDialog = false
            }
        )
    }
    UiResponseDeleteProductScreen(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccessful = {
            observer = Triple(first = false, second = false, third = EMPTY_TEXT)
            onRefresh()
        }
    )
}

@Composable
private fun UiResponseDeleteProductScreen(
    viewModel: ProductViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: UiState<Unit> by viewModel.deleteProduct.collectAsStateWithLifecycle()
    UiResponse(
        state = state,
        onLoading = {},
        onError = onError,
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
            reloadViewModels()
        },
        onSuccess = {
            viewModel.resetProduct(reset = ResetProduct.DELETE_PRODUCT)
            viewModel.findAllProducts()
            onSuccessful()
        }
    )
}
