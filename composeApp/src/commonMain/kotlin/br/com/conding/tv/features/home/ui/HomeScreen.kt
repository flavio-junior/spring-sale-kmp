package br.com.conding.tv.features.home.ui

import androidx.compose.runtime.Composable
import br.com.conding.tv.navigation.AppDestinations

@Composable
expect fun HomeScreen(
    goToNextScreen: (AppDestinations) -> Unit = {},
    goToLoginScreen: () -> Unit = {}
)
