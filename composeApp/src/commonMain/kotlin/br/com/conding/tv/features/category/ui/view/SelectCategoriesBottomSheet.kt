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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.ui.Search
import br.com.conding.tv.components.ui.SimpleButton
import br.com.conding.tv.components.ui.Title
import br.com.conding.tv.features.category.data.dto.CategoryResponseDTO
import br.com.conding.tv.features.category.ui.viewmodel.CategoryViewModel
import br.com.conding.tv.features.category.ui.viewmodel.ResetCategory
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericsStrings.ADD_CATEGORIES
import br.com.conding.tv.resources.GenericsStrings.CANCEL
import br.com.conding.tv.resources.GenericsStrings.CONFIRM
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.NO_CATEGORIES_SELECTED
import br.com.conding.tv.theme.Themes
import org.koin.mp.KoinPlatform.getKoin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SelectCategoriesBottomSheet(
    onDismissRequest: () -> Unit = {},
    onConfirmation: (List<CategoryResponseDTO>) -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {}
) {
    var observer: Triple<Boolean, Boolean, String?> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    val viewModel: CategoryViewModel = getKoin().get()
    var name: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    val categories = remember { mutableStateListOf<CategoryResponseDTO>() }
    var selectedCategories = remember { mutableStateListOf<CategoryResponseDTO>() }
    val modalBottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        containerColor = Themes.colors.background,
        onDismissRequest = onDismissRequest,
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier
                .background(color = Themes.colors.background)
                .padding(horizontal = Themes.size.spaceSize16)
                .verticalScroll(state = rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Themes.size.spaceSize16)
        ) {
            Title(label = ADD_CATEGORIES, backgroundTransparent = true)
            Search(
                value = name,
                onValueChange = { name = it },
                isError = observer.second,
                message = observer.third ?: EMPTY_TEXT,
                onGo = {
                    viewModel.findCategoryByName(name = name)
                }
            )
            SelectCategories(
                categories = categories,
                selectedCategories = selectedCategories,
                onResult = {
                    selectedCategories = it.toMutableStateList()
                }
            )
            ShowAllCategoriesSelected(selectedCategories = selectedCategories)
            SimpleButton(
                onClick = {
                    if (selectedCategories.isNotEmpty()) {
                        viewModel.resetCategory(reset = ResetCategory.FIND_CATEGORY_BY_NAME)
                        onConfirmation(selectedCategories)
                    } else {
                        observer =
                            Triple(first = false, second = true, third = NO_CATEGORIES_SELECTED)
                    }
                },
                label = CONFIRM
            )
            SimpleButton(
                onClick = {
                    viewModel.resetCategory(reset = ResetCategory.FIND_CATEGORY_BY_NAME)
                    onDismissRequest()
                },
                label = CANCEL,
                background = Themes.colors.error
            )
            UiResponseFindCategoryByNameScreen(
                viewModel = viewModel,
                onError = {
                    observer = it
                },
                goToAlternativeRoutes = goToAlternativeRoutes,
                onSuccessful = {
                    observer = Triple(first = false, second = false, third = EMPTY_TEXT)
                    categories.clear()
                    categories.addAll(it)
                }
            )
            Spacer(modifier = Modifier.size(Themes.size.spaceSize64))
        }
    }
}
