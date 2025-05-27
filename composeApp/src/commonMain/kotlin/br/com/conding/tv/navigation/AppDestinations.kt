package br.com.conding.tv.navigation

sealed class AppDestinations(val item: String) {
    data object SignIn : AppDestinations(item = NavigationItems.SIGN_IN.name)
    data object SendRecoverToken : AppDestinations(item = NavigationItems.SEND_RECOVER_TOKEN.name)
    data object CheckRecoverToken : AppDestinations(item = NavigationItems.CHECK_RECOVER_TOKEN.name)
    data object CreateNewPassword : AppDestinations(item = NavigationItems.CREATE_NEW_PASSWORD.name)
    data object SendCodeToConfirmEmail :
        AppDestinations(item = NavigationItems.SEND_CODE_TO_CONFIRM_EMAIL.name)

    data object CheckCodeToConfirmEmail :
        AppDestinations(item = NavigationItems.CHECK_CODE_TO_CONFIRM_EMAIL.name)

    data object SignUp : AppDestinations(item = NavigationItems.SIGN_UP.name)
    data object Home : AppDestinations(item = NavigationItems.HOME.name)
    data object Product : AppDestinations(item = NavigationItems.PRODUCT.name)
    data object Category : AppDestinations(item = NavigationItems.CATEGORY.name)
    data object Settings : AppDestinations(item = NavigationItems.SETTINGS.name)
}
