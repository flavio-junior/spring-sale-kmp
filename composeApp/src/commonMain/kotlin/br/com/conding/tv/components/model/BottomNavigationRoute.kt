package br.com.conding.tv.components.model

import br.com.conding.tv.resources.GenericsStrings
import br.com.conding.tv.resources.IconName

enum class BottomNavigationItem {
    Categories,
    Products,
    SETTINGS
}

sealed class BottomNavigationRoute(
    val label: String,
    val icon: IconName,
    val route: BottomNavigationItem
) {
    data object HOME : BottomNavigationRoute(
        label = GenericsStrings.CATEGORIES,
        icon = IconName.LABEL,
        route = BottomNavigationItem.Categories
    )

    data object SEARCH : BottomNavigationRoute(
        label = GenericsStrings.PRODUCTS,
        icon = IconName.BOX,
        route = BottomNavigationItem.Products
    )

    data object Settings : BottomNavigationRoute(
        label = GenericsStrings.ADJUSTMENTS,
        icon = IconName.SETTINGS,
        route = BottomNavigationItem.SETTINGS
    )
}
