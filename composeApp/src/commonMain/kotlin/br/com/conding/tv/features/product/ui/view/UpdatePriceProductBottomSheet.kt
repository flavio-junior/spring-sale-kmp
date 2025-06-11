package br.com.conding.tv.features.product.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.components.ui.Price
import br.com.conding.tv.components.ui.SimpleButton
import br.com.conding.tv.components.ui.SubTitle
import br.com.conding.tv.components.ui.Title
import br.com.conding.tv.features.product.data.dto.UpdatePriceProductRequestDTO
import br.com.conding.tv.features.product.data.vo.ProductResponseVO
import br.com.conding.tv.features.product.ui.viewmodel.ProductViewModel
import br.com.conding.tv.features.product.ui.viewmodel.ResetProduct
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericsStrings
import br.com.conding.tv.resources.Settings.ZERO_DOUBLE
import br.com.conding.tv.resources.checkPriceIsEqualsZero
import br.com.conding.tv.resources.checkPriceIsNull
import br.com.conding.tv.theme.Themes
import org.koin.mp.KoinPlatform.getKoin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UpdatePriceProductBottomSheet(
    productResponseVO: ProductResponseVO?,
    goToAlternativeRoutes: (HttpError) -> Unit = {},
    onDismiss: () -> Unit = {},
    goToBackScreen: () -> Unit = {}
) {
    val viewModel: ProductViewModel = getKoin().get()
    var price: String by remember { mutableStateOf(value = ZERO_DOUBLE) }
    val modalBottomSheetState = rememberModalBottomSheetState()
    var observer: Triple<Boolean, Boolean, String?> by remember {
        mutableStateOf(
            value = Triple(
                first = false,
                second = false,
                third = GenericsStrings.EMPTY_TEXT
            )
        )
    }
    val checkUpdatePriceProduct = {
        if (checkPriceIsNull(price = price.toDouble())
        ) {
            observer =
                Triple(first = false, second = true, third = GenericsStrings.NOT_BLANK_OR_EMPTY)
        } else if (checkPriceIsEqualsZero(price = price.toDouble())) {
            observer =
                Triple(first = false, second = true, third = GenericsStrings.MESSAGE_ZERO_DOUBLE)
        } else {
            observer = Triple(first = true, second = false, third = GenericsStrings.EMPTY_TEXT)
            viewModel.updatePriceProduct(
                id = productResponseVO?.id ?: 0,
                price = UpdatePriceProductRequestDTO(
                    price = price.toDouble()
                )
            )
        }
    }
    ModalBottomSheet(
        containerColor = Themes.colors.background,
        onDismissRequest = onDismiss,
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier
                .background(color = Themes.colors.background)
                .padding(horizontal = Themes.size.spaceSize36),
            verticalArrangement = Arrangement.spacedBy(Themes.size.spaceSize24)
        ) {
            UiResponseUpdatePriceProductScreen(
                viewModel = viewModel,
                onError = {
                    observer = it
                },
                goToAlternativeRoutes = goToAlternativeRoutes,
                onSuccessful = {
                    observer =
                        Triple(first = false, second = false, third = GenericsStrings.EMPTY_TEXT)
                    viewModel.resetProduct(reset = ResetProduct.UPDATE_PRICE_PRODUCT)
                    onDismiss()
                    goToBackScreen()
                }
            )
            Title(label = GenericsStrings.ITEM_TO_UPDATE_PRICE, backgroundTransparent = true)
            SubTitle(
                label = productResponseVO?.name ?: GenericsStrings.EMPTY_TEXT,
                backgroundTransparent = true
            )
            Price(
                label = GenericsStrings.PRICE,
                value = price,
                isError = observer.second,
                message = observer.third ?: GenericsStrings.EMPTY_TEXT,
                onValueChange = {
                    price = it
                },
                onGo = checkUpdatePriceProduct
            )
            LoadingButton(
                onClick = checkUpdatePriceProduct,
                isEnabled = observer.first,
                label = GenericsStrings.UPDATE_PRICE_PRODUCT_MOBILE
            )
            SimpleButton(
                onClick = onDismiss,
                label = GenericsStrings.CANCEL,
                background = Themes.colors.error
            )
            Spacer(modifier = Modifier.size(Themes.size.spaceSize64))
        }
    }
}
