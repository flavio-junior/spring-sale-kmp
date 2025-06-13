package br.com.conding.tv.features.category.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import br.com.conding.tv.components.model.DefinitionsScreen
import br.com.conding.tv.components.settings.TypeSystem
import br.com.conding.tv.components.ui.ContentScreen
import br.com.conding.tv.components.ui.HeaderSearch
import br.com.conding.tv.features.category.ui.viewmodel.CategoryViewModel
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericsStrings
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal expect fun CategoriesScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (AppDestinations) -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {}
)

@Composable
internal fun MobileCategoryScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (AppDestinations) -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {}
) {
    val viewModel: CategoryViewModel = getKoin().get()
    var createCategory: Boolean by remember { mutableStateOf(value = false) }
    ContentScreen(
        definitionsScreen = DefinitionsScreen(
            alignment = Alignment.Top,
            enableBottomBar = true,
            enableMiniButton = true
        ),
        goToNextScreen = {
            createCategory = true
        },
        content = {
            HeaderSearch(
                label = GenericsStrings.CATEGORIES,
                system = TypeSystem.MOBILE,
                filter = { name, size, sort ->
                    viewModel.findAllCategories(name = name, size = size, sort = sort)
                }
            )
            MobileListCategories(viewModel = viewModel)
        },
        bottomBar = {
            PageIndicatorCategories()
        }
    )
    if (createCategory) {
        CreateCategoryBottomSheet(
            viewModel = viewModel,
            onDismiss = {
                createCategory = false
            }
        )
    }
}
