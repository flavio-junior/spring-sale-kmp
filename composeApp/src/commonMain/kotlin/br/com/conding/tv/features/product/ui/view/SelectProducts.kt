package br.com.conding.tv.features.product.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import br.com.conding.tv.components.ui.Description
import br.com.conding.tv.components.ui.ObserveNetworkStateHandler
import br.com.conding.tv.components.ui.Search
import br.com.conding.tv.components.ui.SimpleButton
import br.com.conding.tv.components.ui.Tag
import br.com.conding.tv.components.ui.Title
import br.com.conding.tv.features.product.data.dto.ProductResponseDTO
import br.com.conding.tv.features.product.ui.viewmodel.ProductViewModel
import br.com.conding.tv.features.product.ui.viewmodel.ResetProduct
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.resources.GenericsStrings.ADD_PRODUCTS
import br.com.conding.tv.resources.GenericsStrings.CANCEL
import br.com.conding.tv.resources.GenericsStrings.CONFIRM
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.NO_PRODUCTS_SELECTED
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE
import br.com.conding.tv.resources.onBorder
import br.com.conding.tv.theme.Themes
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun SelectProducts(
    onDismissRequest: () -> Unit = {},
    onConfirmation: (List<ProductResponseDTO>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    val viewModel: ProductViewModel = getKoin().get()
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .onBorder(
                    onClick = {},
                    spaceSize = Themes.size.spaceSize16,
                    width = Themes.size.spaceSize2,
                    color = Themes.colors.primary
                )
                .background(color = Themes.colors.background)
                .size(size = Themes.size.spaceSize500)
                .padding(all = Themes.size.spaceSize16),
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            var name: String by remember { mutableStateOf(value = EMPTY_TEXT) }
            val products = remember { mutableStateListOf<ProductResponseDTO>() }
            var productsSelected = remember { mutableStateListOf<ProductResponseDTO>() }
            Title(title = ADD_PRODUCTS)
            Search(
                value = name,
                onValueChange = { name = it },
                isError = observer.second,
                message = observer.third,
                onGo = {
                    viewModel.findProductByName(name = name)
                }
            )
            SelectProducts(
                products = products,
                productsSelected = productsSelected,
                onResult = {
                    productsSelected = it.toMutableStateList()
                }
            )
            ListProductsAvailable(products = productsSelected)
            FooterSelectProducts(
                onDismissRequest = {
                    viewModel.resetProduct(reset = ResetProduct.FIND_BY_NAME)
                    onDismissRequest()
                },
                onConfirmation = {
                    if (productsSelected.isNotEmpty()) {
                        viewModel.resetProduct(reset = ResetProduct.FIND_BY_NAME)
                        onConfirmation(productsSelected)
                    } else {
                        observer =
                            Triple(first = false, second = true, third = NO_PRODUCTS_SELECTED)
                    }
                }
            )
            ObserveNetworkStateHandlerFindProductByName(
                viewModel = viewModel,
                onError = {
                    observer = it
                },
                goToAlternativeRoutes = goToAlternativeRoutes,
                onSuccessful = {
                    observer = Triple(first = false, second = false, third = EMPTY_TEXT)
                    products.clear()
                    products.addAll(it)
                }
            )
        }
    }
}

@Composable
private fun SelectProducts(
    products: List<ProductResponseDTO>,
    productsSelected: MutableList<ProductResponseDTO>,
    onResult: (List<ProductResponseDTO>) -> Unit = {}
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        state = scrollState,
        modifier = Modifier
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(-delta)
                    }
                }
            )
    ) {
        items(products) { products ->
            Tag(
                text = products.name,
                value = products,
                enabled = productsSelected.contains(products),
                onCheck = { isChecked ->
                    if (isChecked) {
                        productsSelected.add(products)
                    } else {
                        productsSelected.remove(products)
                    }
                    onResult(productsSelected)
                }
            )
        }
    }
}

@Composable
private fun ListProductsAvailable(
    products: List<ProductResponseDTO>
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        state = scrollState,
        modifier = Modifier
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(-delta)
                    }
                }
            )
    ) {
        items(products) { products ->
            Description(description = products.name)
        }
    }
}

@Composable
private fun FooterSelectProducts(
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
    ) {
        SimpleButton(
            onClick = onDismissRequest,
            label = CANCEL,
            background = Themes.colors.error,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        SimpleButton(
            onClick = onConfirmation,
            label = CONFIRM,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerFindProductByName(
    viewModel: ProductViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: (List<ProductResponseDTO>) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<List<ProductResponseDTO>> by remember { viewModel.findProductByName }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            it?.let {
                onError(Triple(first = false, second = true, third = it))
            }
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.findAllProducts()
            it.result?.let { result -> onSuccessful(result) }
        }
    )
}
