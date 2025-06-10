package br.com.conding.tv.features.product.ui.view

import androidx.compose.runtime.Composable
import br.com.conding.tv.components.settings.TypeSystem
import br.com.conding.tv.features.product.data.vo.ProductsResponseVO

@Composable
internal fun ProductsResult(
    productsResponseVO: ProductsResponseVO? = null,
    system: TypeSystem = TypeSystem.DESKTOP,
    showDesktopScreen: @Composable (ProductsResponseVO?) -> Unit = {},
    showMobileScreen: @Composable (ProductsResponseVO?) -> Unit = {}
) {
    when (system) {
        TypeSystem.DESKTOP -> {
            showDesktopScreen(productsResponseVO)
        }

        TypeSystem.MOBILE -> {
            showMobileScreen(productsResponseVO)
        }
    }
}
