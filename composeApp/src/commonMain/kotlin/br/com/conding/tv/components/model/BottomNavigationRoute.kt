package br.com.conding.tv.components.model

import br.com.conding.tv.resources.GenericStrings
import br.com.conding.tv.resources.IconName

internal enum class BottomNavigationItem {
    Categories,
    Products,
    SETTINGS
}

internal sealed class BottomNavigationRoute(
    val label: String,
    val icon: IconName,
    val route: BottomNavigationItem
) {
    data object Categories : BottomNavigationRoute(
        label = GenericStrings.CATEGORIES,
        icon = IconName.LABEL,
        route = BottomNavigationItem.Categories
    )

    data object Products : BottomNavigationRoute(
        label = GenericStrings.PRODUCTS,
        icon = IconName.BOX,
        route = BottomNavigationItem.Products
    )

    data object Settings : BottomNavigationRoute(
        label = GenericStrings.ADJUSTMENTS,
        icon = IconName.SETTINGS,
        route = BottomNavigationItem.SETTINGS
    )
}
