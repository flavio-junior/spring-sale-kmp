package br.com.conding.tv.features.category.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.conding.tv.components.ui.Description
import br.com.conding.tv.components.ui.UiResponse
import br.com.conding.tv.components.ui.Search
import br.com.conding.tv.components.ui.SimpleButton
import br.com.conding.tv.components.ui.Tag
import br.com.conding.tv.components.ui.Title
import br.com.conding.tv.components.ui.UiState
import br.com.conding.tv.features.category.data.dto.CategoryResponseDTO
import br.com.conding.tv.features.category.ui.viewmodel.CategoryViewModel
import br.com.conding.tv.features.category.ui.viewmodel.ResetCategory
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.resources.GenericsStrings.ADD_CATEGORIES
import br.com.conding.tv.resources.GenericsStrings.CANCEL
import br.com.conding.tv.resources.GenericsStrings.CONFIRM
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.NO_CATEGORIES_SELECTED
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE
import br.com.conding.tv.resources.onBorder
import br.com.conding.tv.theme.Themes
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun SelectCategories(
    onDismissRequest: () -> Unit = {},
    onConfirmation: (List<CategoryResponseDTO>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    var observer: Triple<Boolean, Boolean, String?> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    val viewModel: CategoryViewModel = getKoin().get()
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .onBorder(
                    onClick = {},
                    spaceSize = Themes.size.spaceSize16,
                    width = Themes.size.spaceSize2,
                    color = Themes.colors.primary
                )
                .background(color = Themes.colors.background)
                .size(size = Themes.size.spaceSize500)
                .padding(all = Themes.size.spaceSize16),
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            var name: String by remember { mutableStateOf(value = EMPTY_TEXT) }
            val categories = remember { mutableStateListOf<CategoryResponseDTO>() }
            var selectedCategories = remember { mutableStateListOf<CategoryResponseDTO>() }
            Title(label = ADD_CATEGORIES)
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
            ListCategoriesAvailable(categories = selectedCategories)
            FooterSelectCategories(
                onDismissRequest = {
                    viewModel.resetCategory(reset = ResetCategory.FIND_CATEGORY_BY_NAME)
                    onDismissRequest()
                },
                onConfirmation = {
                    if (selectedCategories.isNotEmpty()) {
                        viewModel.resetCategory(reset = ResetCategory.FIND_CATEGORY_BY_NAME)
                        onConfirmation(selectedCategories)
                    } else {
                        observer =
                            Triple(first = false, second = true, third = NO_CATEGORIES_SELECTED)
                    }
                }
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
        }
    }
}

@Composable
private fun SelectCategories(
    categories: List<CategoryResponseDTO>,
    selectedCategories: MutableList<CategoryResponseDTO>,
    onResult: (List<CategoryResponseDTO>) -> Unit = {}
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        state = scrollState,
        modifier = Modifier
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(-delta)
                    }
                }
            )
    ) {
        items(categories) { category ->
            Tag(
                text = category.name,
                value = category,
                enabled = selectedCategories.contains(category),
                onCheck = { isChecked ->
                    if (isChecked) {
                        selectedCategories.add(category)
                    } else {
                        selectedCategories.remove(category)
                    }
                    onResult(selectedCategories)
                }
            )
        }
    }
}

@Composable
private fun ListCategoriesAvailable(
    categories: List<CategoryResponseDTO>
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        state = scrollState,
        modifier = Modifier
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(-delta)
                    }
                }
            )
    ) {
        items(categories) { category ->
            Description(label = category.name)
        }
    }
}

@Composable
private fun FooterSelectCategories(
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
    ) {
        SimpleButton(
            onClick = onDismissRequest,
            label = CANCEL,
            background = Themes.colors.error,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        SimpleButton(
            onClick = onConfirmation,
            label = CONFIRM,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
    }
}

@Composable
private fun UiResponseFindCategoryByNameScreen(
    viewModel: CategoryViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: (List<CategoryResponseDTO>) -> Unit = {}
) {
    val uiState: UiState<List<CategoryResponseDTO>?> by viewModel.findCategoryByName.collectAsStateWithLifecycle()
    UiResponse(
        state = uiState,
        onLoading = {},
        onError = onError,
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            viewModel.findAllCategories()
            if (it != null) {
                onSuccessful(it)
            }
        }
    )
}
