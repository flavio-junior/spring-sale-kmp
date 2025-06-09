package br.com.conding.tv.features.product.ui.view

import androidx.compose.runtime.Composable
import br.com.conding.tv.features.product.data.vo.ProductResponseVO
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ONE
import br.com.conding.tv.theme.NumbersUtils.NUMBER_TWO
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ZERO

@Composable
internal fun ProductTabMain(
    index: Int,
    productsResponseVO: ProductResponseVO = ProductResponseVO(),
    onItemSelected: (ProductResponseVO) -> Unit = {},
    onToCreateNewProduct: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: (Int) -> Unit = {}
) {
    when (index) {
        NUMBER_ZERO -> ListProductsScreen(
            onItemSelected = onItemSelected,
            onToCreateNewProduct = onToCreateNewProduct,
            goToAlternativeRoutes = goToAlternativeRoutes
        )

        NUMBER_ONE -> DetailsProductScreen(
            product = productsResponseVO,
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = {
                onRefresh(NUMBER_ONE)
            }
        )

        NUMBER_TWO -> CreateNewProductScreen(
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = {
                onRefresh(NUMBER_TWO)
            }
        )
    }
}
