package br.com.conding.tv.features.product.ui.view

import androidx.compose.runtime.Composable
import br.com.conding.tv.features.product.data.vo.ProductResponseVO
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ONE
import br.com.conding.tv.theme.NumbersUtils.NUMBER_TWO
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ZERO

@Composable
internal fun DesktopProductTabMain(
    index: Int,
    productsResponseVO: ProductResponseVO = ProductResponseVO(),
    onItemSelected: (ProductResponseVO) -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {},
    goToCreateNewProduct: () -> Unit = {},
    onRefresh: (Int) -> Unit = {}
) {
    when (index) {
        NUMBER_ZERO -> DesktopFindAllProductsScreen(
            onItemSelected = onItemSelected,
            goToAlternativeRoutes = goToAlternativeRoutes,
            goToCreateNewProduct = goToCreateNewProduct
        )

        NUMBER_ONE -> DesktopDetailsProductScreen(
            productResponseVO = productsResponseVO,
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = {
                onRefresh(NUMBER_ONE)
            }
        )

        NUMBER_TWO -> DesktopCreateNewProductScreen(
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = {
                onRefresh(NUMBER_TWO)
            }
        )
    }
}
