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
import br.com.conding.tv.features.category.ui.view.CategoriesScreen
import br.com.conding.tv.features.home.ui.HomeScreen
import br.com.conding.tv.features.product.ui.view.ProductsScreen
import br.com.conding.tv.features.settings.ui.view.EditProfileUserScreen
import br.com.conding.tv.features.settings.ui.view.SettingsScreen
import br.com.conding.tv.features.splash.SplashScreen

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    startDestination: AppDestinations
) {
    NavHost(navController = navController, startDestination = startDestination) {
        splashScreenNavigation(navController = navController)
        signInNavigation(navController = navController)
        homeNavigation(navController = navController)
        categoryNavigation(navController = navController)
        productNavigation(navController = navController)
        settingsNavigation(navController = navController)
    }
}

fun NavGraphBuilder.splashScreenNavigation(
    navController: NavHostController
) {
    composable<AppDestinations.SplashScreen> {
        SplashScreen(
            goToSignInScreen = {
                navController.navigate(route = AppDestinations.SignIn) {
                    popUpTo(route = AppDestinations.SplashScreen::class) {
                        inclusive = true
                    }
                }
            },
            goToHomeScreen = {
                navController.navigate(route = AppDestinations.Home) {
                    popUpTo(route = AppDestinations.SplashScreen::class) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

fun NavGraphBuilder.signInNavigation(
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

private fun NavGraphBuilder.recoverPassword(
    navController: NavHostController
) {
    composable<AppDestinations.SendRecoverToken> {
        SendRecoverTokenScreen(
            goToBackScreen = {
                navController.goBack()
            },
            goToCheckRecoverTokenToConfirmEmailScreen = {
                navController.navigate(route = it)
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
            }
        )
    }
}

private fun NavGraphBuilder.signUp(
    navController: NavHostController
) {
    composable<AppDestinations.SendCodeToConfirmEmail> {
        SendCodeToConfirmEmailScreen(
            goToBackScreen = {
                navController.goBack()
            },
            goToCheckCodeToConfirmEmailScreen = {
                navController.navigate(route = it) {
                    popUpTo(route = AppDestinations.SendCodeToConfirmEmail) {
                        inclusive = true
                    }
                }
            }
        )
    }

    composable<AppDestinations.CheckCodeToConfirmEmail> { backStackEntry ->
        CheckCodeToConfirmEmailScreen(
            checkCodeToConfirmEmail = backStackEntry.toRoute(),
            goToSignUpScreen = {
                navController.navigate(route = it)
            },
            goToSignInScreen = {
                navController.navigate(route = AppDestinations.SignIn) {
                    popUpTo(route = AppDestinations.CheckRecoverToken::class) {
                        inclusive = true
                    }
                }
            }
        )
    }

    composable<AppDestinations.SignUp> { backStackEntry ->
        SignUpScreen(
            signUp = backStackEntry.toRoute(),
            goToHomeScreen = {
                navController.navigate(route = AppDestinations.Home) {
                    popUpTo(route = AppDestinations.SignUp::class) {
                        inclusive = true
                    }
                }
            },
            goToSignInScreen = {
                navController.navigate(route = AppDestinations.SignIn) {
                    popUpTo(route = AppDestinations.SignUp::class) {
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
    composable<AppDestinations.Home> {
        HomeScreen(
            navGraph = navController,
            goToNextScreen = {
                navController.navigate(route = it)
            },
            goToLoginScreen = {
                navController.navigate(route = AppDestinations.SplashScreen) {
                    popUpTo(route = AppDestinations.Home) {
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
    composable<AppDestinations.Categories> {
        CategoriesScreen(
            goToBackScreen = {
                navController.goBack()
            },
            goToNextScreen = {
                navController.navigate(route = it) {
                    popUpTo(route = AppDestinations.Categories) {
                        inclusive = true
                    }
                }
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.Categories,
                    alternativeRoutes = it
                )
            }
        )
    }
}

fun NavGraphBuilder.productNavigation(
    navController: NavHostController
) {
    composable<AppDestinations.Products> {
        ProductsScreen(
            goToBackScreen = {
                navController.goBack()
            },
            goToNextScreen = {
                navController.navigate(route = it) {
                    popUpTo(route = AppDestinations.Products) {
                        inclusive = true
                    }
                }
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.Products,
                    alternativeRoutes = it
                )
            }
        )
    }
}

fun NavGraphBuilder.settingsNavigation(
    navController: NavHostController
) {
    composable<AppDestinations.Settings> {
        SettingsScreen(
            goToBackScreen = {
                navController.goBack()
            },
            goToNextScreen = {
                navController.navigate(route = it) {
                    popUpTo(route = AppDestinations.Settings) {
                        inclusive = true
                    }
                }
            },
            goToAlternativeRoutes = {
                navigateToAlternativeRoutes(
                    navController = navController,
                    currentScreen = AppDestinations.Settings,
                    alternativeRoutes = it
                )
            }
        )
    }

    composable<AppDestinations.EditProfileUser> {
        EditProfileUserScreen(
            goToBackScreen = {
                navController.goBack()
            }
        )
    }
}
