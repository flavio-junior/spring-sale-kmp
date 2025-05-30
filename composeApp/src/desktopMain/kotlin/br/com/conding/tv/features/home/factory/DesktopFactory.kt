package br.com.conding.tv.features.home.factory

import br.com.conding.tv.components.model.Menu
import br.com.conding.tv.navigation.AppDestinations
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
        route = AppDestinations.Categories
    ),
    Menu(
        icon = IconName.BOX,
        label = PRODUCTS,
        route = AppDestinations.Products
    ),
    Menu(
        icon = IconName.SETTINGS,
        label = SETTINGS,
        route = AppDestinations.Settings
    ),
    Menu(
        icon = IconName.LOGOUT,
        label = EXIT,
        route = AppDestinations.SignIn
    )
)

val availableServices = listOf(
    Menu(
        icon = IconName.HOME_PIN,
        label = HOME,
        route = AppDestinations.Home
    ),
    Menu(
        icon = IconName.LABEL,
        label = CATEGORIES,
        route = AppDestinations.Categories
    ),
    Menu(
        icon = IconName.BOX,
        label = PRODUCTS,
        route = AppDestinations.Products
    ),
    Menu(
        icon = IconName.SETTINGS,
        label = ADJUSTMENTS,
        route = AppDestinations.Settings
    )
)
