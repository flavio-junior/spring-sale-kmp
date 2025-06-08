package br.com.conding.tv.features.category.ui.view

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.components.ui.SimpleButton
import br.com.conding.tv.components.ui.SubTitle
import br.com.conding.tv.components.ui.TextField
import br.com.conding.tv.components.ui.Title
import br.com.conding.tv.features.category.data.dto.UpdateCategoryRequestDTO
import br.com.conding.tv.features.category.data.vo.CategoryResponseVO
import br.com.conding.tv.features.category.ui.viewmodel.CategoryViewModel
import br.com.conding.tv.features.category.ui.viewmodel.ResetCategory
import br.com.conding.tv.resources.GenericsStrings
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.ITEM_TO_UPDATE
import br.com.conding.tv.resources.GenericsStrings.NOT_BLANK_OR_EMPTY
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.isNotBlankAndEmpty
import br.com.conding.tv.theme.Themes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UpdateCategoryBottomSheet(
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
                .padding(horizontal = Themes.size.spaceSize36)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Themes.size.spaceSize24)
        ) {
            SaveUpdateCategoryBottomSheet(
                viewModel = viewModel,
                category = category,
                onDismiss = onDismiss
            )
            SimpleButton(
                onClick = onDismiss,
                label = GenericsStrings.CANCEL,
            )
            Spacer(modifier = Modifier.size(Themes.size.spaceSize64))
        }
    }
}

@Composable
private fun SaveUpdateCategoryBottomSheet(
    viewModel: CategoryViewModel,
    category: CategoryResponseVO?,
    onDismiss: () -> Unit = {}
) {
    var newCategoryName: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    var observer: Triple<Boolean, Boolean, String?> by remember {
        mutableStateOf(Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    UiResponseUpdateCategoryScreen(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        onSuccessful = {
            observer = (Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.resetCategory(reset = ResetCategory.UPDATE_CATEGORY)
            onDismiss()
        }
    )
    Title(label = ITEM_TO_UPDATE)
    SubTitle(label = category?.name ?: EMPTY_TEXT)
    TextField(
        label = GenericsStrings.UPDATE_CATEGORY,
        value = newCategoryName,
        imeAction = ImeAction.Done,
        isError = observer.second,
        message = observer.third ?: EMPTY_TEXT,
        iconName = IconName.EDIT,
        onValueChange = { newCategoryName = it }
    )
    LoadingButton(
        onClick = {
            if (newCategoryName.isNotBlankAndEmpty()) {
                observer = (Triple(first = true, second = false, third = EMPTY_TEXT))
                viewModel.updateCategory(
                    category = UpdateCategoryRequestDTO(id = category?.id, name = newCategoryName)
                )
            } else {
                observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
            }
        },
        isEnabled = observer.first,
        label = GenericsStrings.UPDATE_CATEGORY
    )
}
