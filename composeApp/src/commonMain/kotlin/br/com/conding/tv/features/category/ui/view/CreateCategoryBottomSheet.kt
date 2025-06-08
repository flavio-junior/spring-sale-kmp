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
import androidx.compose.ui.text.input.ImeAction
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.components.ui.SimpleButton
import br.com.conding.tv.components.ui.TextField
import br.com.conding.tv.components.ui.Title
import br.com.conding.tv.features.category.ui.viewmodel.CategoryViewModel
import br.com.conding.tv.features.category.ui.viewmodel.ResetCategory
import br.com.conding.tv.resources.GenericsStrings
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.NOT_BLANK_OR_EMPTY
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.isNotBlankAndEmpty
import br.com.conding.tv.theme.Themes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CreateCategoryBottomSheet(
    viewModel: CategoryViewModel,
    onDismiss: () -> Unit = {}
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        containerColor = Themes.colors.background,
        onDismissRequest = {
            viewModel.resetCategory(reset = ResetCategory.CREATE_CATEGORY)
            onDismiss()
        },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier
                .background(color = Themes.colors.background)
                .padding(horizontal = Themes.size.spaceSize36),
            verticalArrangement = Arrangement.spacedBy(Themes.size.spaceSize24)
        ) {
            SaveCategoryBottomSheet(viewModel = viewModel, onDismiss = onDismiss)
            SimpleButton(
                onClick = {
                    onDismiss()
                },
                label = GenericsStrings.CANCEL
            )
            Spacer(modifier = Modifier.size(Themes.size.spaceSize64))
        }
    }
}

@Composable
private fun SaveCategoryBottomSheet(
    viewModel: CategoryViewModel,
    onDismiss: () -> Unit = {}
) {
    var category: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    var observer: Triple<Boolean, Boolean, String?> by remember {
        mutableStateOf(Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    UiResponseCreateNewCategoryScreen(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        onSuccessful = {
            observer = (Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.resetCategory(reset = ResetCategory.CREATE_CATEGORY)
            viewModel.findAllCategories()
            onDismiss()
        }
    )
    Title(label = GenericsStrings.ITEM_TO_ADD)
    TextField(
        label = GenericsStrings.CATEGORY_NAME,
        value = category,
        imeAction = ImeAction.Done,
        isError = observer.second,
        message = observer.third ?: EMPTY_TEXT,
        iconName = IconName.EDIT,
        onValueChange = { category = it }
    )
    LoadingButton(
        onClick = {
            if (category.isNotBlankAndEmpty()) {
                observer = (Triple(first = true, second = false, third = EMPTY_TEXT))
                viewModel.createCategory(category = category)
            } else {
                observer = (Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY))
            }
        },
        isEnabled = observer.first,
        label = GenericsStrings.SAVE_CATEGORY
    )
}
