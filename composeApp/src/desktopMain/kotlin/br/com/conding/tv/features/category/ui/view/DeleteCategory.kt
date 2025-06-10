package br.com.conding.tv.features.category.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.ui.Alert
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.features.category.ui.viewmodel.CategoryViewModel
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.resources.GenericsStrings.DELETE_CATEGORY
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.theme.Themes

@Composable
internal fun DeleteCategory(
    viewModel: CategoryViewModel,
    id: Long,
    modifier: Modifier = Modifier,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        var observer: Triple<Boolean, Boolean, String?> by remember {
            mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
        }
        var openDialog by remember { mutableStateOf(value = false) }
        LoadingButton(
            label = DELETE_CATEGORY,
            onClick = {
                openDialog = true
            },
            isEnabled = observer.first
        )
        UiResponseDeleteCategoryScreen(
            viewModel = viewModel,
            onError = {
                onError(it)
                observer = it
            },
            goToAlternativeRoutes = goToAlternativeRoutes,
            onSuccessful = {
                observer = Triple(first = false, second = false, third = EMPTY_TEXT)
                onSuccessful()
            }
        )
        if (openDialog) {
            Alert(
                label = "$DELETE_CATEGORY?",
                onDismissRequest = {
                    openDialog = false
                },
                onConfirmation = {
                    observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                    viewModel.deleteCategory(id = id)
                    openDialog = false
                }
            )
        }
    }
}
