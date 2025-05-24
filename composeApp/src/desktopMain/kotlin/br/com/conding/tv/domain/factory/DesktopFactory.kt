package br.com.conding.tv.domain.factory

import br.com.conding.tv.components.model.Menu
import br.com.conding.tv.navigation.NavigationItems
import br.com.conding.tv.resources.GenericsStrings.CATEGORIES
import br.com.conding.tv.resources.GenericsStrings.EXIT
import br.com.conding.tv.resources.GenericsStrings.PRODUCTS
import br.com.conding.tv.resources.GenericsStrings.SETTINGS
import springsale.composeapp.generated.resources.Res
import springsale.composeapp.generated.resources.box
import springsale.composeapp.generated.resources.logout
import springsale.composeapp.generated.resources.settings

val menus = listOf(
    Menu(
        icon = Res.drawable.box,
        label = PRODUCTS,
        route = NavigationItems.PRODUCT.name
    ),
    Menu(
        icon = Res.drawable.box,
        label = CATEGORIES,
        route = NavigationItems.PRODUCT.name
    ),
    Menu(
        icon = Res.drawable.settings,
        label = SETTINGS,
        route = NavigationItems.SETTINGS.name
    ),
    Menu(
        icon = Res.drawable.logout,
        label = EXIT,
        route = NavigationItems.EXIT.name
    )
)
