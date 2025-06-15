package br.com.conding.tv.features.product.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import br.com.conding.tv.components.ui.SimpleButton
import br.com.conding.tv.features.category.data.dto.CategoryResponseDTO
import br.com.conding.tv.features.category.ui.view.SelectCategoriesBottomSheet
import br.com.conding.tv.resources.GenericStrings

@Composable
internal fun GetCategoriesProductBottomSheet(
    onResult: (List<CategoryResponseDTO>) -> Unit = {}
) {
    var openDialog: Boolean by remember { mutableStateOf(value = false) }
    SimpleButton(
        onClick = {
            openDialog = true
        },
        label = GenericStrings.ADD_CATEGORIES
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
