package br.com.conding.tv.features.product.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import br.com.conding.tv.components.model.DefinitionsScreen
import br.com.conding.tv.components.ui.ContentScreen
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.components.ui.Price
import br.com.conding.tv.components.ui.TextField
import br.com.conding.tv.features.category.data.dto.CategoryResponseDTO
import br.com.conding.tv.features.category.ui.view.ShowAllCategoriesSelected
import br.com.conding.tv.features.product.data.dto.ProductRequestDTO
import br.com.conding.tv.features.product.ui.viewmodel.ProductViewModel
import br.com.conding.tv.features.product.ui.viewmodel.ResetProduct
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericsStrings
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.Settings.ZERO_DOUBLE
import br.com.conding.tv.resources.checkPriceIsEqualsZero
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ZERO
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun CreateNewProductScreen(
    goToBackScreen: () -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {}
) {
    ContentScreen(
        definitionsScreen = DefinitionsScreen(
            label = GenericsStrings.CREATE_PRODUCT,
            scroll = true
        ),
        goToBackScreen = goToBackScreen,
        content = {
            val viewModel: ProductViewModel = getKoin().get()
            var name: String by remember { mutableStateOf(value = GenericsStrings.EMPTY_TEXT) }
            var selectedCategories: List<CategoryResponseDTO>
                    by remember { mutableStateOf(value = emptyList()) }
            var price: String by remember { mutableStateOf(value = ZERO_DOUBLE) }
            var quantity: Int by remember { mutableIntStateOf(value = NUMBER_ZERO) }
            var observer: Triple<Boolean, Boolean, String?> by remember {
                mutableStateOf(
                    value = Triple(
                        first = false,
                        second = false,
                        third = GenericsStrings.EMPTY_TEXT
                    )
                )
            }
            val createProduct = {
                createProduct(
                    viewModel = viewModel,
                    name = name,
                    selectedCategories = selectedCategories,
                    price = price.toDouble(),
                    quantity = quantity,
                    onError = {
                        observer = it
                    }
                )
            }
            TextField(
                label = GenericsStrings.NAME,
                value = name,
                iconName = IconName.DRAW,
                isError = observer.second,
                onValueChange = { name = it }
            )
            GetCategoriesProductBottomSheet(
                onResult = {
                    selectedCategories = it
                }
            )
            ShowAllCategoriesSelected(selectedCategories = selectedCategories)
            Price(
                label = GenericsStrings.PRICE,
                value = price,
                isError = observer.second,
                onValueChange = {
                    price = it
                }
            )
            TextField(
                label = GenericsStrings.QUANTITY,
                value = quantity.toString(),
                iconName = IconName.BOX,
                keyboardType = KeyboardType.Number,
                isError = observer.second,
                message = observer.third,
                onValueChange = { quantity = it.toIntOrNull() ?: NUMBER_ZERO }
            )
            LoadingButton(
                onClick = {
                    createProduct()
                },
                label = GenericsStrings.CREATE_PRODUCT,
                isEnabled = observer.first
            )
            UiResponseCreateNewProductScreen(
                viewModel = viewModel,
                onError = {
                    observer = it
                },
                goToAlternativeRoutes = goToAlternativeRoutes,
                onSuccessful = {
                    viewModel.resetProduct(reset = ResetProduct.CREATE_PRODUCT)
                    goToBackScreen()
                }
            )
        }
    )
}

private fun createProduct(
    viewModel: ProductViewModel,
    name: String,
    selectedCategories: List<CategoryResponseDTO>,
    price: Double,
    quantity: Int,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {}
) {
    if (
        name.isNotEmpty() &&
        selectedCategories.isNotEmpty() &&
        price > 0.0 &&
        quantity > 0
    ) {
        onError(Triple(first = true, second = false, third = GenericsStrings.EMPTY_TEXT))
        viewModel.createProduct(
            product = ProductRequestDTO(
                name = name,
                categories = selectedCategories,
                price = price,
                quantity = quantity
            )
        )
    } else if (checkPriceIsEqualsZero(price = price)) {
        onError(Triple(first = false, second = true, third = GenericsStrings.MESSAGE_ZERO_DOUBLE))
    } else {
        onError(Triple(first = false, second = true, third = GenericsStrings.NOT_BLANK_OR_EMPTY))
    }
}
