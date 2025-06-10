package br.com.conding.tv.features.product.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.ui.Alert
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.features.product.ui.viewmodel.ProductViewModel
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericsStrings.DELETE_PRODUCT
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun DesktopDeleteProduct(
    modifier: Modifier = Modifier,
    id: Long,
    goToAlternativeRoutes: (HttpError) -> Unit = {},
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
