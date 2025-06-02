package br.com.conding.tv.features.splash

import androidx.compose.runtime.Composable

@Composable
actual fun SplashScreen(
    goToSignInScreen: () -> Unit,
    goToHomeScreen: () -> Unit
) {
    MobileSplashScreen(
        goToSignInScreen = goToSignInScreen,
        goToHomeScreen = goToHomeScreen
    )
}
