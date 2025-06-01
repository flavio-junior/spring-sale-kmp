package br.com.conding.tv.features.category.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import br.com.conding.tv.components.settings.TypeSystem
import br.com.conding.tv.components.ui.EmptyList
import br.com.conding.tv.features.category.ui.viewmodel.CategoryViewModel
import br.com.conding.tv.resources.GenericsStrings.EMPTY_LIST_CATEGORIES

@Composable
fun MobileListCategories(
    viewModel: CategoryViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.findAllCategories()
    }
    ObserveNetworkStateHandlerFindAllCategories(
        viewModel = viewModel,
        goToAlternativeRoutes = {},
        onSuccess = { onSuccess ->
            CategoriesResult(
                categoriesResponseVO = onSuccess,
                system = TypeSystem.MOBILE,
                showMobileScreen = { response ->
                    if (response?.content?.isNotEmpty() == true) {
                        response.content.forEach {
                            MobileCategoryItem(viewModel = viewModel, categoryResponseVO = it)
                        }
                        PageIndicatorCategories(categoriesResponseVO = response)
                    } else {
                        EmptyList(
                            type = TypeSystem.MOBILE,
                            title = EMPTY_LIST_CATEGORIES
                        )
                    }
                }
            )
        }
    )
}
