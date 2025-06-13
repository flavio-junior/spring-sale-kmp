package br.com.conding.tv.features.product.ui.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.ui.PageIndicator
import br.com.conding.tv.features.product.ui.viewmodel.ProductViewModel
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE_2
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun PageIndicatorProducts(
    modifier: Modifier = Modifier,
) {
    val viewModel: ProductViewModel = getKoin().get()
    UiResponseFindAllProductsScreen(
        viewModel = viewModel,
        onSuccess = { response ->
            Row(
                modifier = modifier
            ) {
                PageIndicator(
                    modifier = modifier.fillMaxWidth(),
                    currentPage = response?.pageable?.pageNumber ?: 0,
                    totalPages = response?.totalPages ?: 0,
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
