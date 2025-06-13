package br.com.conding.tv.features.category.ui.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.ui.PageIndicator
import br.com.conding.tv.features.category.ui.viewmodel.CategoryViewModel
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE_2
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ZERO
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun PageIndicatorCategories(
    modifier: Modifier = Modifier
) {
    val viewModel: CategoryViewModel = getKoin().get()
    UiResponseFindAllCategoriesScreen(
        viewModel = viewModel,
        onSuccess = { result ->
            Row(
                modifier = modifier
            ) {
                PageIndicator(
                    modifier = modifier.fillMaxWidth(),
                    currentPage = result?.pageable?.pageNumber ?: NUMBER_ZERO,
                    totalPages = result?.totalPages ?: NUMBER_ZERO,
                    loadNextPage = {
                        viewModel.loadNextPage()
                    },
                    reloadPreviousPage = {
                        viewModel.reloadPreviousPage()
                    },
                    reloadPage = {
                        viewModel.reloadPage()
                    }
                )
                Spacer(modifier = Modifier.weight(weight = WEIGHT_SIZE_2))
            }
        }
    )
}
