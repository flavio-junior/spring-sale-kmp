package br.com.conding.tv.features.category.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.conding.tv.components.ui.UiResponse
import br.com.conding.tv.components.ui.UiState
import br.com.conding.tv.features.category.data.vo.CategoriesResponseVO
import br.com.conding.tv.features.category.ui.viewmodel.CategoryViewModel
import br.com.conding.tv.features.category.ui.viewmodel.ResetCategory
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.networking.resources.reloadViewModels

@Composable
internal fun UiResponseFindAllCategoriesScreen(
    viewModel: CategoryViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccess: @Composable (CategoriesResponseVO?) -> Unit = {}
) {
    val state: UiState<CategoriesResponseVO> by viewModel.findAllCategories.collectAsStateWithLifecycle()
    UiResponse(
        state = state,
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onSuccess(it)
        }
    )
}

@Composable
internal fun UiResponseCreateNewCategoryScreen(
    viewModel: CategoryViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: UiState<Unit> by viewModel.createNewCategory.collectAsStateWithLifecycle()
    UiResponse(
        state = state,
        onLoading = {},
        onError = onError,
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
            reloadViewModels()
        },
        onSuccess = {
            viewModel.resetCategory(reset = ResetCategory.CREATE_CATEGORY)
            onSuccessful()
        }
    )
}

@Composable
internal fun UiResponseUpdateCategoryScreen(
    viewModel: CategoryViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: UiState<Unit> by viewModel.updateCategory.collectAsStateWithLifecycle()
    UiResponse(
        state = state,
        onLoading = {},
        onError = onError,
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onSuccessful()
            viewModel.findAllCategories()
        }
    )
}

@Composable
internal fun UiResponseDeleteCategoryScreen(
    viewModel: CategoryViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: UiState<Unit> by viewModel.deleteCategory.collectAsStateWithLifecycle()
    UiResponse(
        state = state,
        onLoading = {},
        onError = onError,
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onSuccessful()
            viewModel.findAllCategories()
        }
    )
}
