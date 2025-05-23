package br.com.conding.tv.navigation

sealed class AppDestinations(val item: String) {
    data object SignIn : AppDestinations(item = NavigationItems.SIGN_IN.name)
    data object HOME : AppDestinations(item = NavigationItems.HOME.name)
    data object Product : AppDestinations(item = NavigationItems.PRODUCT.name)
    data object Category : AppDestinations(item = NavigationItems.CATEGORY.name)
    data object Settings : AppDestinations(item = NavigationItems.SETTINGS.name)
}
