package br.com.conding.tv.navigation

import kotlinx.serialization.Serializable

internal sealed interface AppDestinations {

    @Serializable
    data object SplashScreen : AppDestinations

    @Serializable
    data object SignIn : AppDestinations

    @Serializable
    data class CheckRecoverToken(val email: String) : AppDestinations

    @Serializable
    data object SendRecoverToken : AppDestinations

    @Serializable
    data class RecoverToken(val email: String) : AppDestinations

    @Serializable
    data object SendCodeToConfirmEmail : AppDestinations

    @Serializable
    data class CheckCodeToConfirmEmail(val email: String) : AppDestinations

    @Serializable
    data class SignUp(val email: String) : AppDestinations

    @Serializable
    data object Home : AppDestinations

    @Serializable
    data object Categories : AppDestinations

    @Serializable
    data object Products : AppDestinations

    @Serializable
    data object CreateNewProduct : AppDestinations

    @Serializable
    data class ProductDetails(val data: String) : AppDestinations

    @Serializable
    data object Settings : AppDestinations

    @Serializable
    data object EditProfileUser : AppDestinations
}
