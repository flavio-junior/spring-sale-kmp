package br.com.conding.tv.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import br.com.conding.tv.features.account.ui.view.CheckCodeToConfirmEmailScreen
import br.com.conding.tv.features.account.ui.view.CheckRecoverTokenToConfirmEmailScreen
import br.com.conding.tv.features.account.ui.view.ResetPasswordScreen
import br.com.conding.tv.features.account.ui.view.SendCodeToConfirmEmailScreen
import br.com.conding.tv.features.account.ui.view.SendRecoverTokenScreen
import br.com.conding.tv.features.account.ui.view.SignInScreen
import br.com.conding.tv.features.account.ui.view.SignUpScreen
import br.com.conding.tv.features.category.ui.view.CategoryScreen
import br.com.conding.tv.features.home.ui.HomeScreen
import br.com.conding.tv.features.product.ui.view.ProductScreen
import br.com.conding.tv.features.settings.SettingsScreen

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    startDestination: Any = SignIn
) {
    NavHost(navController = navController, startDestination = startDestination) {
        signInNavigation(navController = navController)
        homeNavigation(navController = navController)
        categoryNavigation(navController = navController)
        productNavigation(navController = navController)
        settingsNavigation(navController = navController)
    }
}

fun NavGraphBuilder.signInNavigation(
    navController: NavHostController
) {
    composable<SignIn> {
        SignInScreen(
            goToHomeScreen = {
                navController.navigate(route = Home) {
                    popUpTo(route = SignIn) {
                        inclusive = true
                    }
                }
            },
            goToSendRecoverPasswordScreen = {
                navController.navigate(route = SendRecoverToken)
            },
            goToConfirmEmailAddressScreen = {
                navController.navigate(route = AppDestinations.SendCodeToConfirmEmail.item)
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.SignIn.item,
                    alternativeRoutes = it
                )
            }
        )
    }
    recoverPassword(navController = navController)
    signUp(navController = navController)
}

private fun NavGraphBuilder.recoverPassword(
    navController: NavHostController
) {
    composable<SendRecoverToken> {
        SendRecoverTokenScreen(
            goToBackScreen = {
                navController.popBackStack()
            },
            goToCheckRecoverTokenToConfirmEmailScreen = {
                navController.navigate(route = it)
            }
        )
    }

    composable<CheckRecoverToken> { backStackEntry ->
        CheckRecoverTokenToConfirmEmailScreen(
            checkRecoverToken = backStackEntry.toRoute(),
            goToSignInScreen = {
                navController.navigate(route = SignIn) {
                    popUpTo(route = CheckRecoverToken::class) {
                        inclusive = true
                    }
                }
            },
            goToCreateNewPasswordScreen = {
                navController.navigate(route = it)
            }
        )
    }

    composable<RecoverToken> { backStackEntry ->
        ResetPasswordScreen(
            recoverToken = backStackEntry.toRoute(),
            goToSignInScreen = {
                navController.navigate(route = SignIn) {
                    popUpTo(route = RecoverToken::class) {
                        inclusive = true
                    }
                }
            },
            goToHomeScreen = {
                navController.navigate(route = Home) {
                    popUpTo(route = RecoverToken::class) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

private fun NavGraphBuilder.signUp(
    navController: NavHostController
) {
    composable(route = AppDestinations.SendCodeToConfirmEmail.item) {
        SendCodeToConfirmEmailScreen(
            goToBackScreen = {
                navController.popBackStack()
            },
            goToCheckCodeToConfirmEmailScreen = {
                navController.navigate(route = it) {
                    popUpTo(route = AppDestinations.SendCodeToConfirmEmail.item) {
                        inclusive = true
                    }
                }
            }
        )
    }

    composable<CheckCodeToConfirmEmail> { backStackEntry ->
        CheckCodeToConfirmEmailScreen(
            checkCodeToConfirmEmail = backStackEntry.toRoute(),
            goToSignUpScreen = {
                navController.navigate(route = it)
            },
            goToSignInScreen = {
                navController.navigate(route = SignIn) {
                    popUpTo(route = CheckRecoverToken::class) {
                        inclusive = true
                    }
                }
            }
        )
    }

    composable<SignUp> { backStackEntry ->
        SignUpScreen(
            signUp = backStackEntry.toRoute(),
            goToHomeScreen = {
                navController.navigate(route = Home) {
                    popUpTo(route = SignUp::class) {
                        inclusive = true
                    }
                }
            },
            goToSignInScreen = {
                navController.navigate(route = SignIn) {
                    popUpTo(route = SignUp::class) {
                        inclusive = true
                    }
                }
            },
            goToAlternativeRoutes = {

            }
        )
    }
}


fun NavGraphBuilder.homeNavigation(
    navController: NavHostController
) {
    composable<Home> {
        HomeScreen(
            goToNextScreen = {
                navController.navigate(route = it)
            },
            goToLoginScreen = {
                navController.navigate(route = SignIn) {
                    popUpTo(route = Home) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

fun NavGraphBuilder.categoryNavigation(
    navController: NavHostController
) {
    composable(route = AppDestinations.Category.item) {
        CategoryScreen(
            goToBackScreen = {
                navController.popBackStack()
            },
            goToNextScreen = {
                goToNextScreen(
                    navHostController = navController,
                    currentScreen = AppDestinations.Category.item,
                    nextScreen = it
                )
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.Category.item,
                    alternativeRoutes = it
                )
            }
        )
    }
}

fun NavGraphBuilder.productNavigation(
    navController: NavHostController
) {
    composable(route = AppDestinations.Product.item) {
        ProductScreen(
            goToBackScreen = {
                navController.popBackStack()
            },
            goToNextScreen = {
                goToNextScreen(
                    navHostController = navController,
                    currentScreen = AppDestinations.Product.item,
                    nextScreen = it
                )
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.Product.item,
                    alternativeRoutes = it
                )
            }
        )
    }
}

fun NavGraphBuilder.settingsNavigation(
    navController: NavHostController
) {
    composable(route = AppDestinations.Settings.item) {
        SettingsScreen(
            goToBackScreen = {
                navController.popBackStack()
            },
            goToNextScreen = {
                goToNextScreen(
                    navHostController = navController,
                    currentScreen = AppDestinations.Settings.item,
                    nextScreen = it
                )
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.Settings.item,
                    alternativeRoutes = it
                )
            }
        )
    }
}
