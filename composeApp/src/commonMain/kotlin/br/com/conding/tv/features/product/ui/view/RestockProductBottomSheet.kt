package br.com.conding.tv.features.product.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.components.ui.SimpleButton
import br.com.conding.tv.components.ui.SubTitle
import br.com.conding.tv.components.ui.TextField
import br.com.conding.tv.components.ui.Title
import br.com.conding.tv.features.product.data.dto.RestockProductRequestDTO
import br.com.conding.tv.features.product.data.vo.ProductResponseVO
import br.com.conding.tv.features.product.ui.viewmodel.ProductViewModel
import br.com.conding.tv.features.product.ui.viewmodel.ResetProduct
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericStrings
import br.com.conding.tv.resources.GenericStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericStrings.ITEM_TO_RESTOCK
import br.com.conding.tv.resources.GenericStrings.NOT_BLANK_OR_EMPTY
import br.com.conding.tv.resources.GenericStrings.QUANTITY
import br.com.conding.tv.resources.GenericStrings.RESTOCK_PRODUCT_MOBILE
import br.com.conding.tv.resources.checkPriceIsNull
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ZERO
import br.com.conding.tv.theme.Themes
import org.koin.mp.KoinPlatform.getKoin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RestockProductBottomSheet(
    productResponseVO: ProductResponseVO?,
    goToAlternativeRoutes: (HttpError) -> Unit = {},
    onDismiss: () -> Unit = {},
    goToBackScreen: () -> Unit = {}
) {
    var quantity: Int by remember { mutableIntStateOf(value = NUMBER_ZERO) }
    val viewModel: ProductViewModel = getKoin().get()
    val modalBottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        containerColor = Themes.colors.background,
        onDismissRequest = onDismiss,
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier
                .background(color = Themes.colors.background)
                .padding(horizontal = Themes.size.spaceSize36)
                .verticalScroll(state = rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Themes.size.spaceSize24)
        ) {
            var observer: Triple<Boolean, Boolean, String?> by remember {
                mutableStateOf(Triple(first = false, second = false, third = EMPTY_TEXT))
            }
            val checkRestockProduct = {
                if (checkPriceIsNull(price = quantity.toDouble())
                ) {
                    observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
                } else {
                    observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                    viewModel.restockProduct(
                        id = productResponseVO?.id ?: 0,
                        stock = RestockProductRequestDTO(quantity = quantity)
                    )
                }
            }
            UiResponseRestockProductScreen(
                viewModel = viewModel,
                onError = {
                    observer = it
                },
                goToAlternativeRoutes = goToAlternativeRoutes,
                onSuccessful = {
                    observer = Triple(first = false, second = false, third = EMPTY_TEXT)
                    viewModel.resetProduct(reset = ResetProduct.RESTOCK_PRODUCT)
                    onDismiss()
                    goToBackScreen()
                }
            )
            Title(label = ITEM_TO_RESTOCK, backgroundTransparent = true)
            SubTitle(label = productResponseVO?.name ?: EMPTY_TEXT, backgroundTransparent = true)
            TextField(
                label = QUANTITY,
                value = quantity.toString(),
                isError = observer.second,
                message = observer.third,
                keyboardType = KeyboardType.Number,
                onValueChange = {
                    quantity = it.toIntOrNull() ?: NUMBER_ZERO
                },
                onGo = {
                    checkRestockProduct()
                }
            )
            LoadingButton(
                onClick = {
                    checkRestockProduct()
                },
                isEnabled = observer.first,
                label = RESTOCK_PRODUCT_MOBILE
            )
            SimpleButton(
                onClick = onDismiss,
                label = GenericStrings.CANCEL,
                background = Themes.colors.error
            )
            Spacer(modifier = Modifier.size(Themes.size.spaceSize64))
        }
    }
}
