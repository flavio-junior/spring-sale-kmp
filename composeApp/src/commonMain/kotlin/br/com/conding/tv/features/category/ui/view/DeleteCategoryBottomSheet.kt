package br.com.conding.tv.features.category.ui.view

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
import br.com.conding.tv.components.ui.IsErrorMessage
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.components.ui.SimpleButton
import br.com.conding.tv.components.ui.SubTitle
import br.com.conding.tv.components.ui.Title
import br.com.conding.tv.features.category.data.vo.CategoryResponseVO
import br.com.conding.tv.features.category.ui.viewmodel.CategoryViewModel
import br.com.conding.tv.features.category.ui.viewmodel.ResetCategory
import br.com.conding.tv.resources.GenericsStrings
import br.com.conding.tv.resources.GenericsStrings.DELETE_CATEGORY
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.ITEM_TO_DELETE
import br.com.conding.tv.theme.Themes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DeleteCategoryBottomSheet(
    viewModel: CategoryViewModel,
    category: CategoryResponseVO?,
    onDismiss: () -> Unit = {}
) {
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
                .padding(horizontal = Themes.size.spaceSize36),
            verticalArrangement = Arrangement.spacedBy(Themes.size.spaceSize24)
        ) {
            var observer: Triple<Boolean, Boolean, String?> by remember {
                mutableStateOf(Triple(first = false, second = false, third = EMPTY_TEXT))
            }
            UiResponseDeleteCategoryScreen(
                viewModel = viewModel,
                onError = {
                    observer = it
                },
                onSuccessful = {
                    observer = Triple(first = false, second = false, third = EMPTY_TEXT)
                    viewModel.resetCategory(reset = ResetCategory.DELETE_CATEGORY)
                    onDismiss()
                }
            )
            Title(label = ITEM_TO_DELETE)
            SubTitle(label = category?.name ?: EMPTY_TEXT)
            IsErrorMessage(isError = observer.second, message = observer.third ?: EMPTY_TEXT)
            LoadingButton(
                onClick = {
                    observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                    viewModel.deleteCategory(id = category?.id ?: 0)
                },
                isEnabled = observer.first,
                label = DELETE_CATEGORY
            )
            SimpleButton(
                onClick = onDismiss,
                label = GenericsStrings.CANCEL
            )
            Spacer(modifier = Modifier.size(Themes.size.spaceSize64))
        }
    }
}
