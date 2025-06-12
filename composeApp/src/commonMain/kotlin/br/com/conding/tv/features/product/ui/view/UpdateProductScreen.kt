package br.com.conding.tv.features.product.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import br.com.conding.tv.components.model.DefinitionsScreen
import br.com.conding.tv.components.ui.ContentScreen
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.components.ui.Price
import br.com.conding.tv.components.ui.SimpleButton
import br.com.conding.tv.components.ui.TextField
import br.com.conding.tv.features.category.data.dto.CategoryResponseDTO
import br.com.conding.tv.features.category.ui.view.SelectCategoriesBottomSheet
import br.com.conding.tv.features.category.ui.view.ShowAllCategoriesSelected
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericsStrings
import br.com.conding.tv.resources.IconName

@Composable
internal fun UpdateProductScreen(
    id: Long,
    goToBackScreen: () -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {}
) {
    ContentScreen(
        definitionsScreen = DefinitionsScreen(
            label = GenericsStrings.UPDATE_PRODUCT_MOBILE,
            scroll = true
        ),
        goToBackScreen = goToBackScreen,
        content = {
            var name: String by remember { mutableStateOf(value = GenericsStrings.EMPTY_TEXT) }
            var selectedCategories: List<CategoryResponseDTO> by remember { mutableStateOf(value = emptyList()) }
            var price: String by remember { mutableStateOf(value = GenericsStrings.EMPTY_TEXT) }
            var quantity: String by remember { mutableStateOf(value = GenericsStrings.EMPTY_TEXT) }
            TextField(
                label = GenericsStrings.NAME,
                value = name,
                iconName = IconName.DRAW,
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
                onValueChange = {
                    price = it
                }
            )
            TextField(
                label = GenericsStrings.QUANTITY,
                value = quantity,
                iconName = IconName.BOX,
                keyboardType = KeyboardType.Number,
                onValueChange = { quantity = it }
            )
            LoadingButton(
                onClick = {

                },
                label = GenericsStrings.UPDATE_PRODUCT_MOBILE
            )
        }
    )
}

@Composable
private fun GetCategoriesProductBottomSheet(
    onResult: (List<CategoryResponseDTO>) -> Unit = {}
) {
    var openDialog: Boolean by remember { mutableStateOf(value = false) }
    SimpleButton(
        onClick = {
            openDialog = true
        },
        label = GenericsStrings.ADD_CATEGORIES
    )
    if (openDialog) {
        SelectCategoriesBottomSheet(
            onDismissRequest = {
                openDialog = false
            },
            onConfirmation = {
                onResult(it)
                openDialog = false
            }
        )
    }
}
