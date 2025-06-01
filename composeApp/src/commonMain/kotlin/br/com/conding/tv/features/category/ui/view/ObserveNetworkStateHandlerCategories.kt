package br.com.conding.tv.features.category.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import br.com.conding.tv.components.ui.LoadingData
import br.com.conding.tv.components.ui.ObserveNetworkStateHandler
import br.com.conding.tv.features.category.data.vo.CategoriesResponseVO
import br.com.conding.tv.features.category.ui.viewmodel.CategoryViewModel
import br.com.conding.tv.features.category.ui.viewmodel.ResetCategory
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.networking.resources.reloadViewModels
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT

@Composable
fun ObserveNetworkStateHandlerFindAllCategories(
    viewModel: CategoryViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccess: @Composable (CategoriesResponseVO?) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<CategoriesResponseVO> by remember { viewModel.findAllCategories }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {
            LoadingData()
        },
        onError = {
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onSuccess(it.result)
        }
    )
}

@Composable
fun ObserveNetworkStateHandlerCreateNewCategory(
    viewModel: CategoryViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.createNewCategory }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            onError(Triple(first = false, second = true, third = it))
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
            reloadViewModels()
        },
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.resetCategory(reset = ResetCategory.CREATE_CATEGORY)
            onSuccessful()
        }
    )
}

@Composable
fun ObserveNetworkStateHandlerUpdateCategory(
    viewModel: CategoryViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.updateCategory }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            onError(Triple(first = false, second = true, third = it))
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onSuccessful()
            viewModel.findAllCategories()
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
        }
    )
}

@Composable
fun ObserveNetworkStateHandlerDeleteCategory(
    viewModel: CategoryViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.deleteCategory }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            onError(Triple(first = false, second = true, third = it))
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onSuccessful()
            viewModel.findAllCategories()
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
        }
    )
}
