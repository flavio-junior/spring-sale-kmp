package br.com.conding.tv.features.account.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import br.com.conding.tv.features.account.ui.view.CheckCodeToConfirmEmailScreen
import br.com.conding.tv.features.account.ui.view.CheckRecoverTokenToConfirmEmailScreen
import br.com.conding.tv.features.account.ui.view.ResetPasswordScreen
import br.com.conding.tv.features.account.ui.view.SendCodeToConfirmEmailScreen
import br.com.conding.tv.features.account.ui.view.SendRecoverTokenScreen
import br.com.conding.tv.features.account.ui.view.SignInScreen
import br.com.conding.tv.features.account.ui.view.SignUpScreen
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.navigation.goBack
import br.com.conding.tv.navigation.navigateToAlternativeRoutes

internal fun NavGraphBuilder.signInNavigation(
    navController: NavHostController
) {
    composable<AppDestinations.SignIn> {
        SignInScreen(
            goToHomeScreen = {
                navController.navigate(route = AppDestinations.Home) {
                    popUpTo(route = AppDestinations.SignIn::class) {
                        inclusive = true
                    }
                }
            },
            goToSendRecoverPasswordScreen = {
                navController.navigate(route = AppDestinations.SendRecoverToken)
            },
            goToConfirmEmailAddressScreen = {
                navController.navigate(route = AppDestinations.SendCodeToConfirmEmail)
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.SignIn,
                    alternativeRoutes = it
                )
            }
        )
    }
    recoverPassword(navController = navController)
    signUp(navController = navController)
}

internal fun NavGraphBuilder.recoverPassword(
    navController: NavHostController
) {
    composable<AppDestinations.SendRecoverToken> {
        SendRecoverTokenScreen(
            goToBackScreen = {
                navController.goBack()
            },
            goToCheckRecoverTokenToConfirmEmailScreen = {
                navController.navigate(route = it)
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.SendRecoverToken,
                    alternativeRoutes = it
                )
            }
        )
    }

    composable<AppDestinations.CheckRecoverToken> { backStackEntry ->
        CheckRecoverTokenToConfirmEmailScreen(
            checkRecoverToken = backStackEntry.toRoute(),
            goToSignInScreen = {
                navController.navigate(route = AppDestinations.SignIn) {
                    popUpTo(route = AppDestinations.CheckRecoverToken::class) {
                        inclusive = true
                    }
                }
            },
            goToCreateNewPasswordScreen = {
                navController.navigate(route = it)
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.CheckRecoverToken,
                    alternativeRoutes = it
                )
            }
        )
    }

    composable<AppDestinations.RecoverToken> { backStackEntry ->
        ResetPasswordScreen(
            recoverToken = backStackEntry.toRoute(),
            goToSignInScreen = {
                navController.navigate(route = AppDestinations.SignIn) {
                    popUpTo(route = AppDestinations.RecoverToken::class) {
                        inclusive = true
                    }
                }
            },
            goToHomeScreen = {
                navController.navigate(route = AppDestinations.Home) {
                    popUpTo(route = AppDestinations.RecoverToken::class) {
                        inclusive = true
                    }
                }
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.RecoverToken,
                    alternativeRoutes = it
                )
            }
        )
    }
}

internal fun NavGraphBuilder.signUp(
    navController: NavHostController
) {
    composable<AppDestinations.SendCodeToConfirmEmail> {
        SendCodeToConfirmEmailScreen(
            goToBackScreen = {
                navController.goBack()
            },
            goToCheckCodeToConfirmEmailScreen = { email ->
                navController.navigate(route = AppDestinations.CheckCodeToConfirmEmail(email = email)) {
                    popUpTo(route = AppDestinations.SignIn)
                }
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.SendCodeToConfirmEmail,
                    alternativeRoutes = it
                )
            }
        )
    }

    composable<AppDestinations.CheckCodeToConfirmEmail> { backStackEntry ->
        CheckCodeToConfirmEmailScreen(
            checkCodeToConfirmEmail = backStackEntry.toRoute(),
            goToSignUpScreen = { email ->
                navController.navigate(route = AppDestinations.SignUp(email = email)) {
                    popUpTo(route = AppDestinations.SignIn)
                }
            },
            goToBackScreen = {
                navController.goBack()
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.CheckCodeToConfirmEmail,
                    alternativeRoutes = it
                )
            }
        )
    }

    composable<AppDestinations.SignUp> { backStackEntry ->
        SignUpScreen(
            signUp = backStackEntry.toRoute(),
            goToHomeScreen = {
                navController.navigate(route = AppDestinations.Home) {
                    popUpTo(route = AppDestinations.SignIn) {
                        inclusive = true
                    }
                }
            },
            goToBackScreen = {
                navController.goBack()
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.SignUp,
                    alternativeRoutes = it
                )
            }
        )
    }
}
