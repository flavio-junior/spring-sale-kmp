package br.com.conding.tv.features.category.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.ui.HeaderSearch
import br.com.conding.tv.components.ui.LoadingData
import br.com.conding.tv.components.ui.ObserveNetworkStateHandler
import br.com.conding.tv.features.category.data.vo.CategoriesResponseVO
import br.com.conding.tv.features.category.data.vo.CategoryResponseVO
import br.com.conding.tv.features.category.ui.viewmodel.CategoryViewModel
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE_4
import br.com.conding.tv.theme.Themes
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun CardCategories(
    modifier: Modifier = Modifier,
    onItemSelected: (CategoryResponseVO) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(
                top = Themes.size.spaceSize16,
                bottom = Themes.size.spaceSize16,
                end = Themes.size.spaceSize16
            )
    ) {
        val viewModel: CategoryViewModel = getKoin().get()
        HeaderSearch(
            onSearch = { name, size, sort, route ->
                viewModel.findAllCategories(name = name, size = size, sort = sort, route = route)
            },
            onSort = { name, size, sort, route ->
                viewModel.findAllCategories(name = name, size = size, sort = sort, route = route)
            },
            onFilter = { name, size, sort, route ->
                viewModel.findAllCategories(name = name, size = size, sort = sort, route = route)
            },
            onRefresh = { name, size, sort, route ->
                viewModel.findAllCategories(name = name, size = size, sort = sort, route = route)
            }
        )
        LaunchedEffect(key1 = Unit) {
            viewModel.findAllCategories()
        }
        ObserveNetworkStateHandlerCategories(
            viewModel = viewModel,
            onItemSelected = onItemSelected,
            goToAlternativeRoutes = goToAlternativeRoutes
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerCategories(
    viewModel: CategoryViewModel,
    onItemSelected: (CategoryResponseVO) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
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
            it.result?.let { response ->
                CategoriesResult(
                    content = response,
                    onItemSelected = onItemSelected,
                    goToAlternativeRoutes = goToAlternativeRoutes
                )
            }
        }
    )
}

@Composable
private fun CategoriesResult(
    content: CategoriesResponseVO,
    onItemSelected: (CategoryResponseVO) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    Column {
        ListCategories(
            modifier = Modifier
                .fillMaxSize()
                .weight(weight = WEIGHT_SIZE_4)
                .padding(top = Themes.size.spaceSize8),
            content = content,
            onItemSelected = onItemSelected
        )
        PageIndicatorCategories(content = content)
        SaveCategory(goToAlternativeRoutes = goToAlternativeRoutes)
    }
}
