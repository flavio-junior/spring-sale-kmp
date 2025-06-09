package br.com.conding.tv.features.category.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.settings.TypeSystem
import br.com.conding.tv.components.ui.EmptyList
import br.com.conding.tv.features.category.ui.viewmodel.CategoryViewModel
import br.com.conding.tv.resources.GenericsStrings.EMPTY_LIST_CATEGORIES
import br.com.conding.tv.theme.Themes

@Composable
internal fun MobileListCategories(
    viewModel: CategoryViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.findAllCategories()
    }
    UiResponseFindAllCategoriesScreen(
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
                        PageIndicatorCategories(
                            modifier = Modifier.padding(bottom = Themes.size.spaceSize2),
                            categoriesResponseVO = response
                        )
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
