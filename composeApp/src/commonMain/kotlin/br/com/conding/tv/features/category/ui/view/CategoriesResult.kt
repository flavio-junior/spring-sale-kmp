package br.com.conding.tv.features.category.ui.view

import androidx.compose.runtime.Composable
import br.com.conding.tv.components.settings.TypeSystem
import br.com.conding.tv.features.category.data.vo.CategoriesResponseVO

@Composable
internal fun CategoriesResult(
    categoriesResponseVO: CategoriesResponseVO? = null,
    system: TypeSystem = TypeSystem.DESKTOP,
    showDesktopScreen: @Composable (CategoriesResponseVO?) -> Unit = {},
    showMobileScreen: @Composable (CategoriesResponseVO?) -> Unit = {}
) {
    when (system) {
        TypeSystem.DESKTOP -> {
            showDesktopScreen(categoriesResponseVO)
        }

        TypeSystem.MOBILE -> {
            showMobileScreen(categoriesResponseVO)
        }
    }
}
