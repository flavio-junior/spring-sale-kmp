package br.com.conding.tv.features.product.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import br.com.conding.tv.components.ui.Alert
import br.com.conding.tv.components.ui.Description
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.components.ui.TextField
import br.com.conding.tv.features.product.data.dto.RestockProductRequestDTO
import br.com.conding.tv.features.product.ui.viewmodel.ProductViewModel
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericsStrings.CONFIRM_UPDATE
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.NOT_BLANK_OR_EMPTY
import br.com.conding.tv.resources.GenericsStrings.QUANTITY
import br.com.conding.tv.resources.GenericsStrings.RESTOCK_PRODUCT
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE
import br.com.conding.tv.resources.checkPriceIsNull
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ZERO
import br.com.conding.tv.theme.Themes
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun DesktopRestockProduct(
    modifier: Modifier = Modifier,
    id: Long,
    goToAlternativeRoutes: (HttpError) -> Unit = {},
    onRefresh: () -> Unit
) {
    val viewModel: ProductViewModel = getKoin().get()
    var quantity: Int by remember { mutableIntStateOf(value = NUMBER_ZERO) }
    var openDialog by remember { mutableStateOf(value = false) }
    var observer: Triple<Boolean, Boolean, String?> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    val checkRestockProduct = {
        if (checkPriceIsNull(price = quantity.toDouble())
        ) {
            observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
        } else {
            observer = Triple(first = true, second = false, third = EMPTY_TEXT)
            viewModel.restockProduct(
                id = id,
                stock = RestockProductRequestDTO(quantity = quantity)
            )
        }
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        Description(label = "$RESTOCK_PRODUCT:")
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            modifier = modifier,
            verticalAlignment = Alignment.Top
        ) {
            TextField(
                label = QUANTITY,
                value = quantity.toString(),
                isError = observer.second,
                message = observer.third ?: EMPTY_TEXT,
                keyboardType = KeyboardType.Number,
                onValueChange = {
                    quantity = it.toIntOrNull() ?: NUMBER_ZERO
                },
                modifier = modifier.weight(weight = WEIGHT_SIZE),
                onGo = {
                    openDialog = true
                }
            )
            LoadingButton(
                onClick = {
                    openDialog = true
                },
                label = CONFIRM_UPDATE,
                isEnabled = observer.first,
                modifier = modifier.weight(weight = WEIGHT_SIZE)
            )
        }
    }
    if (openDialog) {
        Alert(
            label = "$RESTOCK_PRODUCT?",
            onDismissRequest = {
                openDialog = false
            },
            onConfirmation = {
                observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                checkRestockProduct()
                openDialog = false
            }
        )
    }
    UiResponseRestockProductScreen(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccessful = {
            observer = Triple(first = false, second = false, third = EMPTY_TEXT)
            quantity = NUMBER_ZERO
            onRefresh()
        }
    )
}