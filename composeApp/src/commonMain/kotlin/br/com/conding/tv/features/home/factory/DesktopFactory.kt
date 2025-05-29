package br.com.conding.tv.features.home.factory

import br.com.conding.tv.components.model.Menu
import br.com.conding.tv.navigation.NavigationItems
import br.com.conding.tv.resources.GenericsStrings.ADJUSTMENTS
import br.com.conding.tv.resources.GenericsStrings.CATEGORIES
import br.com.conding.tv.resources.GenericsStrings.EXIT
import br.com.conding.tv.resources.GenericsStrings.HOME
import br.com.conding.tv.resources.GenericsStrings.PRODUCTS
import br.com.conding.tv.resources.GenericsStrings.SETTINGS
import br.com.conding.tv.resources.IconName

val home = listOf(
    Menu(
        icon = IconName.LABEL,
        label = CATEGORIES,
        route = NavigationItems.CATEGORY.name
    ),
    Menu(
        icon = IconName.BOX,
        label = PRODUCTS,
        route = NavigationItems.PRODUCT.name
    ),
    Menu(
        icon = IconName.SETTINGS,
        label = SETTINGS,
        route = NavigationItems.SETTINGS.name
    ),
    Menu(
        icon = IconName.LOGOUT,
        label = EXIT,
        route = NavigationItems.EXIT.name
    )
)

val availableServices = listOf(
    Menu(
        icon = IconName.HOME_PIN,
        label = HOME,
        route = NavigationItems.HOME.name
    ),
    Menu(
        icon = IconName.LABEL,
        label = CATEGORIES,
        route = NavigationItems.CATEGORY.name
    ),
    Menu(
        icon = IconName.BOX,
        label = PRODUCTS,
        route = NavigationItems.PRODUCT.name
    ),
    Menu(
        icon = IconName.SETTINGS,
        label = ADJUSTMENTS,
        route = NavigationItems.SETTINGS.name
    )
)
