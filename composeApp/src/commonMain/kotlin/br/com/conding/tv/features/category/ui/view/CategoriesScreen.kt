package br.com.conding.tv.features.category.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import br.com.conding.tv.components.model.DefinitionsScreen
import br.com.conding.tv.components.settings.TypeSystem
import br.com.conding.tv.components.ui.ContentScreen
import br.com.conding.tv.components.ui.HeaderSearch
import br.com.conding.tv.features.category.ui.viewmodel.CategoryViewModel
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.resources.GenericsStrings
import org.koin.mp.KoinPlatform.getKoin

@Composable
expect fun CategoriesScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (AppDestinations) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
)

@Composable
fun MobileCategoryScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (AppDestinations) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val viewModel: CategoryViewModel = getKoin().get()
    ContentScreen(
        definitionsScreen = DefinitionsScreen(
            alignment = Alignment.Top
        ),
        content = {
            HeaderSearch(
                label = GenericsStrings.CATEGORIES,
                system = TypeSystem.MOBILE,
                onSearch = { name, size, sort, route ->
                    viewModel.findAllCategories(
                        name = name,
                        size = size,
                        sort = sort,
                        route = route
                    )
                },
                onSort = { name, size, sort, route ->
                    viewModel.findAllCategories(
                        name = name,
                        size = size,
                        sort = sort,
                        route = route
                    )
                },
                onFilter = { name, size, sort, route ->
                    viewModel.findAllCategories(
                        name = name,
                        size = size,
                        sort = sort,
                        route = route
                    )
                },
                onRefresh = { name, size, sort, route ->
                    viewModel.findAllCategories(
                        name = name,
                        size = size,
                        sort = sort,
                        route = route
                    )
                }
            )
            LaunchedEffect(key1 = Unit) {
                viewModel.findAllCategories()
            }
            ObserveNetworkStateHandlerCategories(
                viewModel = viewModel,
                goToAlternativeRoutes = goToAlternativeRoutes,
                onSuccess = { onSuccess ->
                    CategoriesResult(
                        categoriesResponseVO = onSuccess,
                        system = TypeSystem.MOBILE,
                        showMobileScreen = { response ->
                            MobileListCategories(
                                categoriesResponseVO = response,
                                onItemSelected = {}
                            )
                        }
                    )
                }
            )
        }
    )
}
